package cvt.context.projection
import java.awt.{Dimension, Graphics2D}
import cvt.context.projection.uiobject.{AgentUI, Cell}
import cvt.Agent


/** @constructor Extends Projection. A two dimensional grid Projection.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 28 July 2018
  * @param _dimension   the dimension in cells.
  * @param _cellSize    the size of the cell in pixels.
  * @param _cellGapSize the size of the gap between cells in pixels.
  * @param _circular    represents the grid being circular. Meaning, an agent will can traverse off grid and appear on an opposing size.
  * {{{
  * val context = new Context()
  * val g1 = new Grid(new Dimension(10, 10), 20, 2, true)
  * context.addProjection(g1)
  * //context.projectionsVisible(false)
  *
  * context.applyColorScheme(ColorSchemes.agentColorRandom)
  * g1.applyColorScheme(ColorSchemes.cellColorByPopulation)
  *
  * val a1 = new Agent()
  * val a2 = new Agent()
  *
  * context.addAgent(a1)
  * context.addAgent(a2)
  *
  * g1.move(a1, Direction.right, 2)
  * }}}
  */
class Grid(val _dimension: Dimension, _cellSize : Int = 50, _cellGapSize : Int = 2, _circular : Boolean = true) extends Projection(new Dimension(0,0)) {
    window.title = "Grid Projection"
    /** Two-dimensional representation of grid in cells. */
    private val grid : Array[Array[Cell]] = Array.ofDim[Cell](_dimension.width, _dimension.height)
    /** Maps AgentUIs to specific cells. This replaces the need for each AgentUI to have an attribute (pointer) to their current cell. */
    private var cellMap = Map[AgentUI, Cell]()
    /** Boolean variable to tack if grid was created. Used at initialization (required for large dimensions) */
    private var createdGrid = false
    // create grid and resize window to fit all cells
    createGrid()
    sizeWindowToGrid()


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
        // local reference to cell dimension
        val cellDimension = new Dimension(_cellSize, _cellSize)
        // iterate 2D array
        for (x <- 0 until _dimension.width; y <- 0 until _dimension.height) {
            // the true coordinates on which we draw on. NOT the cells coordinates
            val absoluteCoordinate = new Coordinate(x * (_cellSize + _cellGapSize), y * (_cellSize + _cellGapSize))
            // create cell and designate its coordinates on the window AND the coordinates of the cell in the grid
            val cell = new Cell(cellDimension, new Coordinate(x, y), this)
            // set the dimension of the cell
            cell.dimension = cellDimension
            /// set the absolute (respective to only the window origin) coordinates. Used for painting
            cell.absoluteLocation = absoluteCoordinate
            // assign its spot in the grid
            grid(x)(y) = cell
        } // for x, y
        // set to true so we can paint now
        createdGrid = true
    } // initializeGrid
    
    
    /** Inherited from Component. Paints the window, grid, cells and all agents within the cells.
      * @param graphics the graphics object.
      */
    override def paintComponent(graphics: Graphics2D): Unit = {
        if (!visible) return
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
      * @param c is the coordinate which we wish to fetch the cell of.
      * @return the cell at coordinate c.
      */
    private def cellAt(c : Coordinate) : Cell = {
        // if coordinate is null, return a default cell
        if (c == null) return null
        if (_circular) {
            // if _circular and coordinate is negative, recursively add the dimension of the grid
            if (c.X < 0) return cellAt(new Coordinate(c.X + _dimension.width, c.Y))
            if (c.Y < 0) return cellAt(new Coordinate(c.X, c.Y + + _dimension.height))
            // return the cell determined by modulo
            return grid(c.X % (_dimension.width - 0))(c.Y % (_dimension.height - 0))
        } // if circular
        // if _circular is false and c is outside of the grid then return a default cell
        if (c.X < 0 || c.Y < 0 || c.Y >= _dimension.width || c.Y >= _dimension.height) return null
        // otherwise return the cell at the parameter coordinate
        grid(c.X)(c.Y)
    } // cellAt()
    

    /** Retrieves the all neighbor Agent which are of the specified type.
      * @param agent the Agent which we wish to retrieve the neighbors of.
      * @param radius is the distance in the neighborhood in which we want to get the Agents of.
      * @return the list of Agents in the neighborhood
      */
    override def getNeighbors(agent : Agent, radius : Integer) : Array[Agent] = {
        if (!agentMap.contains(agent)) return null
        val aui = agentMap(agent)
        // designate where to start the iteration
        val start = new Coordinate(cellMap(aui).coordinate).subtract(radius)
        // designate where to end the iteration
        val end = new Coordinate(cellMap(aui).coordinate).add(radius)
        // iterate over the cells and add all AgentUIs in the cells
        var agents = Array[AgentUI]()
        for (x <- start.X to end.X; y <- start.Y to end.Y if cellAt(new Coordinate(x, y)) != cellMap(aui)) {
            val AUIs = cellAt(new Coordinate(x, y)).agents.toArray
            agents = agents ++ AUIs
        } // for x,y
        agents.map(a => agentUIMap(a))
    } // getNeighbors()
    
    
    /** Adds an Agent to the given projection at a designated coordinate.
      * @param agent the Agent which we wish to add to the projection.
      * @param c the coordinate which we wish to add the Agent to.
      */
    override def addAgent(agent : Agent, c : Coordinate) : Unit = {
        // local reference to the cell at coordinate c
        val cell = cellAt(c)
        if (cell == null) return
        val aui = if (agentMap.contains(agent)) agentMap(agent) else new AgentUI(agent)
        cellMap = cellMap + (aui -> cell)
        agentMap = agentMap + (agent -> aui)
        // add AgentUI to cell
        cell.add(aui)
        repaint()
    } // addAgent()
    

    /** Adds a list of Agents to the given projection at a default coordinate.
      * @param agents the list of Agents which we wish to add to the projection.
      */
    override def addAgents(agents : Array[Agent]) : Unit = {
        // iterate over the list of AgentUIs in the parameter array
        for (agent <- agents) addAgent(agent, new Coordinate(0, 0))
        repaint()
    } // addAgents()
    
    
    /** Removes an Agent from the projection.
      * @param agent the Agent which we wish to remove.
      */
    override def removeAgent(agent : Agent) : Unit = {
        if (!agentMap.contains(agent)) return
        val aui = agentMap(agent)
        cellMap(aui).remove(aui)
        cellMap -= aui
        repaint()
    } // removeAgent
    
    
    /** Removes all Agents form the Grid. */
    override def removeAllAgents() : Unit = {
        // iterate over the grid and remove all AgentUIs from the cells
        for (x <- grid.indices; y <- grid(0).indices) cellAt(new Coordinate(x, y)).removeAllAgents()
        repaint()
    } // removeAllAgents()

    
    /** Moves an Agent from its current cell to one in near proximity using directions
      * Left, Right, Up, Down and a magnitude.
      * @param agent the Agent to move.
      * @param direction the direction which we want to move.
      * @param magnitude the amount of cells we wish to move.
      */
    override def move(agent : Agent, direction : Direction.Value, magnitude : Int) : Unit = {
        if (!agentMap.contains(agent)) return
        val aui = agentMap(agent)
        // map the direction parameter to an actual cell
        // this is done by adding/subtracting magnitude
        val newCoordinate = cellMap(aui).coordinate.add(Direction.toCoordinate(direction).multiply(magnitude))
        // move the AgentUI to the new cell
        move(agent, newCoordinate)
    } // move()


    /** Moves an Agent from its current cell to one designated by the parameter coordinate.
      * @param agent the Agent which we wish to move.
      * @param c the coordinate which we wish to move the Agent to.
      */
    override def move(agent : Agent, c : Coordinate) : Unit = {
        if (!agentMap.contains(agent)) return
        // call remove to remove the AgentUI from its current cell
        removeAgent(agent)
        // call to add the AgentUI to the cell at the parameter coordinate
        addAgent(agent, c)
    } // move()

} // Grid
