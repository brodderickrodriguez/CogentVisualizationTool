package cvt.context
import cvt.{Cell, Coordinate, AgentUI, AgentUINotification, MockCogentType}
import java.awt.{Dimension, Graphics2D}
import scala.collection.mutable.ArrayBuffer


/**
  *
  * @param dimension the dimension of the grid in cells
  */
class Grid(val dimension: Dimension, cellSize : Integer = 50, cellGapSize : Integer = 2, circular : Boolean = true) extends Context(dimension: Dimension) {
    private val grid : Array[Array[Cell]] = Array.ofDim[Cell](dimension.width, dimension.height)
    createGrid()
    sizeWindowToGrid()
    private var populatedGrid = false

    
    /** sizeWindowToGrid
      * shape the window to fit all cells
      */
    private def sizeWindowToGrid() : Unit = {
        val borderPixels = new Dimension(2 * window.borderSize, 2 * window.borderSize + 25)
        val gapPixels = new Dimension((dimension.width - 1) * cellGapSize, (dimension.height - 1) * cellGapSize)
        val cellPixels = new Dimension(dimension.width * cellSize, dimension.height * cellSize)
        window.size = new Dimension(borderPixels.width + gapPixels.width + cellPixels.width, borderPixels.height + gapPixels.height + cellPixels.height)
    } // sizeWindowToGrid()
    
    
    /** createGrid
      * adds cells to the grid 2D array
      */
    def createGrid() : Unit = {
        val cellDimension = new Dimension(cellSize, cellSize)
        for (x <- 0 until dimension.width; y <- 0 until dimension.height) {
            val cell = new Cell(this, cellDimension)
            val absoluteCoordinate = new Coordinate(x * (cellSize + cellGapSize), y * (cellSize + cellGapSize))
            cell.absoluteLocation = absoluteCoordinate
            cell.cellCoordinate = new Coordinate(x, y)
            grid(x)(y) = cell
        } // for x, y
        populatedGrid = true
    } // initializeGrid
    
    
    /**
      *
      * @param graphics the graphics object
      */
    override def paintComponent(graphics: Graphics2D): Unit = {
        if (!populatedGrid) createGrid()
        // if the grid is empty, populate it first
        // for each cell, draw it
        for (x <- grid.indices; y <- grid(0).indices) {
            val cell = grid(x)(y)
            graphics.setColor(getCellColor(cell))
            graphics.fillRect(cell.absoluteLocation.x, cell.absoluteLocation.y, cellSize, cellSize)
            for (a <- cell.agents) {
                graphics.setColor(a.color)
                graphics.fillOval(cell.absoluteLocation.x + 5, cell.absoluteLocation.y + 5, 10, 10)
            } // for a
        } // for x, y
    } // paintComponent()
    
    
    def cellAt(c : Coordinate) : Cell = {
        if (c == null) return null
        if (circular) {
            if (c.x < 0) return cellAt(new Coordinate(c.x + dimension.width, c.y))
            if (c.y < 0) return cellAt(new Coordinate(c.x, c.y + + dimension.height))
            return grid(c.x % (dimension.width - 0))(c.y % (dimension.height - 0))
        } // if circular
        if (c.x < 0 || c.y < 0 || c.y > dimension.width || c.y > dimension.height) return null
        grid(c.x)(c.y)
    } // cellAt()
    
    
    def getNeighborsOfTypes(agent : AgentUI, radius : Integer, types : Array[MockCogentType.Value]): ArrayBuffer[AgentUI] = getNeighbors(agent, radius).intersect(getAgentsWithTypes(types))
    
    
    /************/
    def getNeighbors(agent : AgentUI, radius : Integer) : ArrayBuffer[AgentUI] = {
        val start = new Coordinate(agent.cell.cellCoordinate).subtract(radius)
        val end = new Coordinate(agent.cell.cellCoordinate).add(radius)
        val agents = ArrayBuffer[AgentUI]()
        for (x <- start.x + 1 until end.x; y <- start.y + 1 until end.y) agents ++= cellAt(new Coordinate(x, y)).agents
        agents
    } // getNeighbors()
    
    
    /******************/
    def addAgent(agent : AgentUI) : Unit = addAgent(agent, new Coordinate(0, 0))
    
    
    def addAgent(agent : AgentUI, c : Coordinate) : Unit = {
        val cell = cellAt(c)
        agent.cell = cell
        cell.sendNotificationToAgents(AgentUINotification.addedAgentToCell)
        cell.agents.append(agent)
        allAgents.append(agent)
        repaint()
    } // addAgent()
    
    
    def addAgents(agents : Array[AgentUI]) : Unit = {
        val cell = cellAt(new Coordinate(0, 0))
        for (agent <- agents) {
            agent.cell = cell
            cell.agents.append(agent)
        }
        cell.sendNotificationToAgents(AgentUINotification.addedAgentToCell)
        allAgents ++ agents
        repaint()
    } // addAgents()
    
    
    /************************************/
    def removeAgent(agent : AgentUI) : Boolean = {
        if (agent.cell == null) return false
        val cell = agent.cell
        //cell.agents.remove(cell.agents.indexOf(agent))
        // allAgents.remove(allAgents.indexOf(agent))
        cell.sendNotificationToAgents(AgentUINotification.removedAgentFromCell)
        agent.cell = null
        repaint()
        true
    } // removeAgent
    
    
    def removeAllAgents() : Unit = {
        for (x <- grid.indices; y <- grid(0).indices) {
            val cell = cellAt(new Coordinate(x, y))
            cell.sendNotificationToAgents(AgentUINotification.removingAllAgentsFromCell)
            cell.agents.clear()
        } // for x, y
        allAgents.clear()
        repaint()
    } // removeAllAgents()
    

    def move(agent : AgentUI, c : Coordinate) : Unit = {
        removeAgent(agent)
        addAgent(agent, c)
    } // move()
    
    
    /**
      *
      * @param agent the agent to move
      * @param direction which we want to move
      * @param magnitude is the amount of cells we wish to move
      */
    def move(agent : AgentUI, direction : Direction.Value, magnitude : Integer) : Unit = {
        val newCoordinate = new Coordinate(agent.cell.cellCoordinate)
        direction match {
            case Direction.up => newCoordinate.y -= magnitude
            case Direction.right => newCoordinate.x += magnitude
            case Direction.down => newCoordinate.y += magnitude
            case Direction.left => newCoordinate.x -= magnitude
        } // match direction
        move(agent, newCoordinate)
    } // move()
    
    
} // Grid
