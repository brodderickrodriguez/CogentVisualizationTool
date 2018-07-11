package cvt.context
import cvt.{Cell, Coordinate, AgentUI, AgentUINotification, MockCogentType}
import java.awt.{Dimension, Graphics2D}
import scala.collection.mutable.ArrayBuffer


/**
  *
  * @param _dimension the dimension of the grid in cells
  */
class Grid(val _dimension: Dimension, cellSize : Integer = 50, cellGapSize : Integer = 2, circular : Boolean = true) extends Context(_dimension: Dimension) {
    private val grid : Array[Array[Cell]] = Array.ofDim[Cell](_dimension.width, _dimension.height)
    createGrid()
    sizeWindowToGrid()
    
    
    /** sizeWindowToGrid
      * shape the window to fit all cells
      */
    private def sizeWindowToGrid() : Unit = {
        val borderPixels = new Dimension(2 * window.borderSize, 2 * window.borderSize + 25)
        val gapPixels = new Dimension((_dimension.width - 1) * cellGapSize, (_dimension.height - 1) * cellGapSize)
        val cellPixels = new Dimension(_dimension.width * cellSize, _dimension.height * cellSize)
        window.size = new Dimension(borderPixels.width + gapPixels.width + cellPixels.width, borderPixels.height + gapPixels.height + cellPixels.height)
    } // sizeWindowToGrid()
    
    
    /** createGrid
      * adds cells to the grid 2D array
      */
    private def createGrid() : Unit = {
        val cellDimension = new Dimension(cellSize, cellSize)
        for (x <- 0 until _dimension.width; y <- 0 until _dimension.height) {
            val absoluteCoordinate = new Coordinate(x * (cellSize + cellGapSize), y * (cellSize + cellGapSize))
            val cell = new Cell(this, cellDimension, new Coordinate(x, y))
            cell.dimension = cellDimension
            cell.absoluteLocation = absoluteCoordinate
            grid(x)(y) = cell
        } // for x, y
    } // initializeGrid
    
    
    /**
      *
      * @param graphics the graphics object
      */
    override def paintComponent(graphics: Graphics2D): Unit = {
        for (x <- grid.indices; y <- grid(0).indices) {
            val cell = grid(x)(y)
            graphics.setColor(getCellColor(cell))
            graphics.fillRect(cell.absoluteLocation.X, cell.absoluteLocation.Y, cellSize, cellSize)
            if (paintAgent) {
                for (a <- cell.agents if a != null) {
                    graphics.setColor(getAgentColor(a))
                    graphics.fillOval(cell.absoluteLocation.X + 5, cell.absoluteLocation.Y + 5, a.dimension.width, a.dimension.height)
                } // for a
            } // paintAgent
        } // for x, y
    } // paintComponent()
    
    
    private def cellAt(c : Coordinate) : Cell = {
        if (c == null) return grid(0)(0)
        if (circular) {
            if (c.X < 0) return cellAt(new Coordinate(c.X + _dimension.width, c.Y))
            if (c.Y < 0) return cellAt(new Coordinate(c.X, c.Y + + _dimension.height))
            return grid(c.X % (_dimension.width - 0))(c.Y % (_dimension.height - 0))
        } // if circular
        if (c.X < 0 || c.Y < 0 || c.Y >= _dimension.width || c.Y >= _dimension.height) return grid(0)(0)
        grid(c.X)(c.Y)
    } // cellAt()
    
    
    override def getNeighborsOfTypes(agent : AgentUI, radius : Integer, types : Array[MockCogentType.Value]): ArrayBuffer[AgentUI] = getNeighbors(agent, radius).intersect(getAgentsWithTypes(types))
    
    
    /************/
    def getNeighbors(agent : AgentUI, radius : Integer) : ArrayBuffer[AgentUI] = {
        val start = new Coordinate(agent.cell.coordinate).subtract(radius)
        val end = new Coordinate(agent.cell.coordinate).add(radius)
        val agents = ArrayBuffer[AgentUI]()
        for (x <- start.X to end.X; y <- start.Y to end.Y if cellAt(new Coordinate(x, y)) != agent.cell) agents ++= cellAt(new Coordinate(x, y)).agents
        agents
    } // getNeighbors()
    
    
    
    
    def addAgent(agent : AgentUI, c : Coordinate) : Unit = {
        val cell = cellAt(c)
        agent.cell = cell
        cell.sendNotificationToAgents(AgentUINotification.addedAgentToCell)
        cell.agents += agent
        allAgents += agent
        repaint()
    } // addAgent()
    
    
    def addAgents(agents : Array[AgentUI]) : Unit = {
        val cell = cellAt(new Coordinate(0, 0))
        for (agent <- agents) {
            agent.cell = cell
            cell.agents += agent
        }
        cell.sendNotificationToAgents(AgentUINotification.addedAgentToCell)
        allAgents ++ agents
        repaint()
    } // addAgents()
    
    
    def removeAgent(agent : AgentUI) : Unit = {
        agent.cell.agents -= agent
        allAgents -= agent
        agent.cell.sendNotificationToAgents(AgentUINotification.removedAgentFromCell)
        agent.cell = null
        repaint()
    } // removeAgent
    
    
    override def removeAllAgents() : Unit = {
        for (x <- grid.indices; y <- grid(0).indices) cellAt(new Coordinate(x, y)).agents.clear()
        super.removeAllAgents()
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
    def move(agent : AgentUI, direction : Direction.Value, magnitude : Int) : Unit = {
        var c = new Coordinate(agent.cell.coordinate)
        direction match {
            case Direction.up => c = c.subY(magnitude)
            case Direction.right => c = c.addX(magnitude)
            case Direction.down => c = c.addY(magnitude)
            case Direction.left => c = c.subX(magnitude)
        } // match direction
        move(agent, c)
    } // move()
    
    
} // Grid
