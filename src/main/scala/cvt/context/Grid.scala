package cvt.context

import java.awt.Dimension

import cvt._

import scala.collection.mutable.ArrayBuffer
import java.awt.{Color, Graphics2D}


/**
  *
  * @param dimension the dimension of the grid in cells
  */
class Grid(dimension: Dimension, cellSize : Integer = 50, cellGapSize : Integer = 2, circular : Boolean) extends Context(dimension: Dimension) {
    //println("[Grid] initializing")
    private val grid : Array[Array[Cell]] = Array.ofDim[Cell](dimension.width, dimension.height)
    // private var populatedGrid = false
    
    createGrid()
    sizeWindowToGrid()
    
    /** sizeWindowToGrid
      * shape the window to fit all cells
      */
    private def sizeWindowToGrid() : Unit = {
        //println("[Grid] - sizeWindowToGrid: top")
        val borderPixels = new Dimension(2 * window.borderSize, 2 * window.borderSize + 25)
        val gapPixels = new Dimension((dimension.width - 1) * cellGapSize, (dimension.height - 1) * cellGapSize)
        val cellPixels = new Dimension(dimension.width * cellSize, dimension.height * cellSize)
        window.size = new Dimension(borderPixels.width + gapPixels.width + cellPixels.width,
            borderPixels.height + gapPixels.height + cellPixels.height)
    } // sizeWindowToGrid()
    
    
    /** createGrid
      * adds cells to the grid 2D array
      */
    def createGrid() : Unit = {
        //println("[Grid] - initializeGrid: top")
        val cellDimension = new Dimension(cellSize, cellSize)
        for (x <- 0 until dimension.width; y <- 0 until dimension.height) {
            val cell = new Cell(this, cellDimension)
            val absoluteCoordinate = new Coordinate(x * (cellSize + cellGapSize), y * (cellSize + cellGapSize))
            cell.absoluteLocation = absoluteCoordinate
            cell.cellCoordinate = new Coordinate(x, y)
            //cell.color = cell.color
            grid(x)(y) = cell
        } // for x, y
        //populatedGrid = true
    } // initializeGrid
    
    
    /**
      *
      * @param graphics the graphics object
      */
    override def paintComponent(graphics: Graphics2D): Unit = {
        //println("[Grid] - paintComponent: top")
        // if the grid is empty, populate it first
        //if (!populatedGrid)
        //    createGrid()
        // for each cell, draw it
        for (x <- grid.indices; y <- grid(0).indices) {
            val cell = grid(x)(y)

            graphics.setColor(getCellColor(cell))
            graphics.fillRect(cell.absoluteLocation.x, cell.absoluteLocation.y, cellSize, cellSize)
            
            for (a <- cell.agents) {
                graphics.setColor(a.color)
                graphics.fillOval(cell.absoluteLocation.x + 5, cell.absoluteLocation.y + 5, 10, 10)
            }
            
        } // for x, y
    } // paintComponent()
    
    
    def cellAt(c : Coordinate) : Cell = {
        println("[Grid] - cellAt: top")
        if (c == null) return null
        if (circular) {
            if (c.x < 0) return cellAt(new Coordinate(c.x + dimension.width, c.y))
            if (c.y < 0) return cellAt(new Coordinate(c.x, c.y + + dimension.height))
            
            return grid(c.x % (dimension.width - 0))(c.y % (dimension.height - 0))
        } // if circular
        
        if (c.x < 0 || c.y < 0 || c.y > dimension.width || c.y > dimension.height)
            return null
        grid(c.x)(c.y)
    } // cellAt()
    
    
    def getNeighbors(agent : AgentUI, radius : Integer) : ArrayBuffer[AgentUI] = {
        println("[Grid] - getNeighbors: top")
        val agents = ArrayBuffer[AgentUI]()
        val agentCellCoordinate = agent.cell.cellCoordinate
        val startX : Int = agentCellCoordinate.x - radius
        val endX : Int = agentCellCoordinate.x + radius
        val startY : Int = agentCellCoordinate.y - radius
        val endY : Int = agentCellCoordinate.y + radius
        
        for (x <- startX to endX; y <- startY to endY)
            agents ++ cellAt(new Coordinate(x,y)).agents
        
        agents
    } // getNeighbors()
    
    
    def getNeighborsOfTypes(agent : AgentUI, radius : Integer, types : ArrayBuffer[MockAgentType.Value]): ArrayBuffer[AgentUI] = {
        val result = getNeighbors(agent, radius)
        result.union(getAgentsWithTypes(types))
        result
    } // getNeighborsOfTypes
    
    
    def addAgent(agent : AgentUI) : Unit = {
        println("[Grid] - addAgent(AgentUI): top")
        addAgent(agent, new Coordinate(0, 0))
    } // addAgent()
    
    
    def addAgent(agent : AgentUI, c : Coordinate) : Unit = {
        println("[Grid] - addAgent(AgentUI, Coordinate): top")
        val cell = cellAt(c)
        agent.cell = cell
        cell.sendNotificationToAgents(AgentUINotification.addedAgentToCell)
        cell.agents.append(agent)
        allAgents.append(agent)
        repaint()
    } // addAgent()
    
    
    def addAgents(agents: ArrayBuffer[AgentUI]) : Unit = {
        println("[Grid] - addAgents: top")
        val cell = cellAt(new Coordinate(0, 0))
        for (agent <- agents) {
            agent.cell = cell
            cell.agents.append(agent)
        }
        cell.sendNotificationToAgents(AgentUINotification.addedAgentToCell)
        allAgents ++ agents
        repaint()
    } // addAgents()
    
    
    def removeAgent(agent : AgentUI) : Boolean = {
        println("[Grid] - removeAgent: top")
        if (agent.cell == null)
            return false
        val cell = agent.cell
        
        //   if (cell.agents.indexOf(agent) >= 0) {
        //cell.agents.remove(cell.agents.indexOf(agent))
        // allAgents.remove(allAgents.indexOf(agent))
        cell.sendNotificationToAgents(AgentUINotification.removedAgentFromCell)
        agent.cell = null
        repaint()
        return true
        //  }
        
        false
    } // removeAgent
    
    
    
    def removeAllAgents() : Unit = {
        println("[Grid] - removeAllAgents): top")
        for (x <- grid.indices; y <- grid(0).indices) {
            val cell = cellAt(new Coordinate(x, y))
            cell.sendNotificationToAgents(AgentUINotification.removingAllAgentsFromCell)
            cell.agents.clear()
        } // for x, y
        allAgents.clear()
        repaint()
    } // removeAllAgents()
    
    
    /**
      *
      * @param agent the agent we wish to move
      * @param c the coordinate of the cell we wish to go to
      */
    def move(agent : AgentUI, c : Coordinate) : Unit = {
        println("[Grid] - move(AgentUI, Coordinate): top")
        removeAgent(agent)
        addAgent(agent, c)
    } // move()
    
    
    /**
      *
      * @param agent the agent to move
      * @param direction which we want to move (1 = N, 2 = E, 3 = S, 4 = W)
      * @param magnitude is the amount of cells we wish to move
      */
    def move(agent : AgentUI, direction : Direction.Value, magnitude : Integer) : Unit = {
        println("[Grid] - move(AgentUI, direction, magnitude): top")
        val oldCoordinate = agent.cell.cellCoordinate
        val newCoordinate = new Coordinate(0, 0)
        
        direction match {
            case Direction.up => newCoordinate.y = -1
            case Direction.right => newCoordinate.x = 1
            case Direction.down => newCoordinate.y = 1
            case Direction.left => newCoordinate.x = -1
        } // match direction
        
        newCoordinate.multiply(magnitude)
        newCoordinate.add(oldCoordinate)
        move(agent, newCoordinate)
    } // move()
    
    
} // Grid
