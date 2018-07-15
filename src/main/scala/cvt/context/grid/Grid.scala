package cvt.context.grid
import java.awt.{Dimension, Graphics2D}
import cvt.context.{Context, ContextController, Direction}
import cvt.uiobject.{AgentUI, AgentUINotification, Coordinate}
import cvt._

import scala.collection.mutable.ArrayBuffer


/** @constructor Extends Context. The Grid Context. A two dimensional grid context.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 10 July 2018
  *
  * @param _dimension the dimension in cells.
  * @param _cellSize the size of the cell in pixels.
  * @param _cellGapSize the size of the gap between cells in pixels.
  * @param _circular represents the grid being circular. Meaning, an agent will can traverse off grid and appear on an opposing size.
  */
class Grid(val _dimension: Dimension, _cellSize : Int = 50, _cellGapSize : Int = 2, _circular : Boolean = true, controller : ContextController) extends Context(_dimension, controller = controller) {
    // two dimensional representation of grid in cells
    private val grid : Array[Array[Cell]] = Array.ofDim[Cell](_dimension.width, _dimension.height)
    // boolean variable to tack if grid was created. Used at initialization (required for large dimensions)
    private var createdGrid = false
    // create grid and resize window to fit all cells
    createGrid()
    sizeWindowToGrid()
    
    println("start1")
    
    
    
    /** Shapes the window to fit all cells (called at initialization)
      * by the boarder, all cells and all gaps between cells to reshape window.
      */
    private def sizeWindowToGrid() : Unit = {
        val gapPixels = new Dimension((_dimension.width - 1) * _cellGapSize, (_dimension.height - 1) * _cellGapSize)
        val cellPixels = new Dimension(_dimension.width * _cellSize, _dimension.height * _cellSize)
        window.size = new Dimension(gapPixels.width + cellPixels.width, gapPixels.height + cellPixels.height + 25)
    } // sizeWindowToGrid()
    
    
    /** Adds cells to the grid 2D array (called at initialization).
      * iterates through the 2D grid array and creates a new cell.
      */
    private def createGrid() : Unit = {
        println("start3")
    
        // local reference to cell dimension
        val cellDimension = new Dimension(_cellSize, _cellSize)
        // iterate 2D array
        for (x <- 0 until _dimension.width; y <- 0 until _dimension.height) {
            println("start32")
            // the true coordinates on which we draw on. NOT the cells coordinates
            val absoluteCoordinate = new Coordinate(x * (_cellSize + _cellGapSize), y * (_cellSize + _cellGapSize))
            // create cell and designate its coordinates on the window AND the coordinates of the cell in the grid
            
            val g = this
            println("start33")
    
            val dim = cellDimension
            println("start34")
    
            val c = new Coordinate(x,y)
            println("start35")
    
            val cell = new Cell(g, dim, c)
    
            println("start36")
    
    
            // set the dimension of the cell
            cell.dimension = cellDimension
            /// set the absolute (respective to only the window origin) coordinates. Used for painting
            cell.absoluteLocation = absoluteCoordinate
            // assign its spot in the grid
            grid(x)(y) = cell
    
    
    
        } // for x, y
        // set to true so we can paint now
        createdGrid = true
        println("start4")
    
    } // initializeGrid
    
    
    /** Inherited from Component. Paints the window, grid, cells and all agents within the cells.
      *
      * @param graphics the graphics object.
      */
    override def paintComponent(graphics: Graphics2D): Unit = {
        // wait for grid to be created. Necessary for large grids where initializing all cells takes time
        while (!createdGrid) { }
        // iterate 2D array
        for (x <- grid.indices; y <- grid(0).indices) {
            // grab the cell to work on
            val cell = grid(x)(y)
            // set color and paint
            graphics.setColor(getCellColor(cell))
            graphics.fillRect(cell.absoluteLocation.X, cell.absoluteLocation.Y, _cellSize, _cellSize)
            // if color scheme does not prevent painting
            if (paintAgent) {
                // iterate over all the agents in the cell
                for (a <- cell.agents if a != null) {
                    // set color and paint
                    graphics.setColor(getAgentColor(a))
                    graphics.fillOval(cell.absoluteLocation.X + 5, cell.absoluteLocation.Y + 5, a.dimension.width, a.dimension.height)
                } // for a
            } // paintAgent
        } // for x, y
    } // paintComponent()
    
    
    /** Fetches the cell at the parameter coordinate. Takes care of when _circular is true or false.
      *
      * @param c is the coordinate which we wish to fetch the cell of.
      * @return the cell at coordinate c.
      */
    private def cellAt(c : Coordinate) : Cell = {
        // if coordinate is null, return a default cell
        if (c == null) return grid(0)(0)
        if (_circular) {
            // if _circular and coordinate is negative, recursively add the dimension of the grid
            if (c.X < 0) return cellAt(new Coordinate(c.X + _dimension.width, c.Y))
            if (c.Y < 0) return cellAt(new Coordinate(c.X, c.Y + + _dimension.height))
            // return the cell determined by modulo
            return grid(c.X % (_dimension.width - 0))(c.Y % (_dimension.height - 0))
        } // if circular
        // if _circular is false and c is outside of the grid then return a default cell
        if (c.X < 0 || c.Y < 0 || c.Y >= _dimension.width || c.Y >= _dimension.height) return grid(0)(0)
        // otherwise return the cell at the parameter coordinate
        grid(c.X)(c.Y)
    } // cellAt()
    
    
    /** Retrieves the neighbor AgentUIs which are of the specified types.
      * Uses the intersection of getNeighbors() and getAgentsWithTypes().
      *
      * @param agent the agentUI which we wish to retrieve the neighbors of.
      * @param radius is the distance in the neighborhood in which we want to get the AgentUIs of.
      * @param types is an array of the types of agents we wish to retrieve.
      * @return the list of AgentUIs in the neighborhood
      */
    override def getNeighborsOfTypes(agent : AgentUI, radius : Integer, types : Array[MockAgentType.Value]): ArrayBuffer[AgentUI] = getNeighbors(agent, radius).intersect(getAgentsWithTypes(types))
    
    
    /** Retrieves the all neighbor AgentUIs which are of the specified type.
      *
      * @param agent the agentUI which we wish to retrieve the neighbors of.
      * @param radius is the distance in the neighborhood in which we want to get the AgentUIs of.
      * @return the list of AgentUIs in the neighborhood
      */
    def getNeighbors(agent : AgentUI, radius : Integer) : ArrayBuffer[AgentUI] = {
        // designate where to start the iteration
        val start = new Coordinate(agent.cell.coordinate).subtract(radius)
        // designate where to end the iteration
        val end = new Coordinate(agent.cell.coordinate).add(radius)
        // iterate over the cells and add all AgentUIs in the cells
        val agents = ArrayBuffer[AgentUI]()
        for (x <- start.X to end.X; y <- start.Y to end.Y if cellAt(new Coordinate(x, y)) != agent.cell) agents ++= cellAt(new Coordinate(x, y)).agents
        agents
    } // getNeighbors()
    
    
    /** Adds an AgentUI to the given context at a designated coordinate.
      *
      * @param agent the AgentUI which we wish to add to the context.
      * @param c the coordinate which we wish to add the AgentUI to.
      */
    def addAgent(agent : AgentUI, c : Coordinate) : Unit = {
        // local reference to the cell at coordinate c
        val cell = cellAt(c)
        // set the agents cell to the coordinate c
        agent.cell = cell
        // send a notification to all AgentUIs in the cell that there has been another AgentUI added
        cell.sendNotificationToAgents(AgentUINotification.addedAgentToCell)
        // append to the list of AgentUIs in the cell
        cell.agents += agent
        repaint()
    } // addAgent()
    
    
    /** Adds a list of AgentUIs to the given context at a default coordinate.
      *
      * @param agents the list of AgentUIs which we wish to add to the context.
      */
    def addAgents(agents : Array[AgentUI]) : Unit = {
        // create a local reference to a default cell
        val cell = cellAt(new Coordinate(0, 0))
        // iterate over the list of AgentUIs in the parameter array
        for (agent <- agents) {
            // set the AgentUIs cell to the default cell
            agent.cell = cell
            // append AgentUI to the list of AgentUIs in the cell
            cell.agents += agent
        }
        // send a notification to all the AgentUIs in the call that we are adding another AgentUI
        cell.sendNotificationToAgents(AgentUINotification.addedAgentToCell)
        // append parameter agents to the list of all AgentUIs
      //  allAgents ++ agents
        repaint()
    } // addAgents()
    
    
    /** Removes an AgentUI from the context.
      *
      * @param agent the AgentUI which we wish to remove.
      */
    def removeAgent(agent : AgentUI) : Unit = {
        // if the AgentUI has not been assigned a cell, no need to remove it.
        if (agent.cell == null) return
        // remove the AgentUI from the list of AgentUIs within the cell
        agent.cell.agents -= agent
        // remove the AgentUI form the list of all AgentUIs in the grid
       // allAgents -= agent
        // send a notification to all the AgentUIs in the cell that we are removing an AgentUI
        agent.cell.sendNotificationToAgents(AgentUINotification.removedAgentFromCell)
        // set the AgentUIs cell to null
        agent.cell = null
        repaint()
    } // removeAgent
    
    
    /** Removes all AgentUIs form the Grid. */
    override def removeAllAgents() : Unit = {
        // iterate over the grid and remove all AgentUIs from the cells
        for (x <- grid.indices; y <- grid(0).indices) cellAt(new Coordinate(x, y)).agents.clear()
        // call super to empty allAgents and repaint
        super.removeAllAgents()
    } // removeAllAgents()
    
    
    /** Moves an AgentUI from its current cell to one designated by the parameter coordinate.
      *
      * @param agent the AgentUI which we wish to move.
      * @param c the coordinate which we wish to move the AgentUI to.
      */
    override def move(agent : AgentUI, c : Coordinate) : Unit = {
        if (agent == null) return
        println("grid move" + agent)
        // call remove to remove the AgentUI from its current cell
        removeAgent(agent)
        // call to add the AgentUI to the cell at the parameter coordinate
        addAgent(agent, c)
    } // move()
    
    
    /** Moves an AgentUI from its current cell to one in near proximity using directions
      * Left, Right, Up, Down and a magnitude.
      *
      * @param agent the agent to move.
      * @param direction the direction which we want to move.
      * @param magnitude the amount of cells we wish to move.
      */
    override def move(agent : AgentUI, direction : Direction.Value, magnitude : Int) : Unit = {
        // create a new coordinate which points to the AgentUIs current cell coordinate
        var c = new Coordinate(agent.cell.coordinate)
        // map the direction parameter to an actual cell
        // this is done by adding/subtracting magnitude
        direction match {
            case Direction.up => c = c.subY(magnitude)
            case Direction.right => c = c.addX(magnitude)
            case Direction.down => c = c.addY(magnitude)
            case Direction.left => c = c.subX(magnitude)
        } // match direction
        // move the AgentUI to the new cell
        move(agent, c)
    } // move()
    
    
} // Grid
