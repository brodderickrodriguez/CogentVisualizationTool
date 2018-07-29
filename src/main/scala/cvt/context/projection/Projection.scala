package cvt.context.projection
import scala.swing.{Component, Dimension}
import java.awt.Color
import cvt.context.Context
import cvt.context.projection.uiobject.{AgentUI, Cell}
import cvt.{Agent, AgentType}


/** An Object which makes it user-friendly to move AgentUIs within a projection.
  * Directions translate to Coordinates: Up: (0, -1), Right: (1, 0),
  * * Down: (0, 1), Left: (-1, 0)
  */
object Direction extends Enumeration {
    val up: Value = Value
    val right: Value = Value
    val down: Value = Value
    val left: Value  = Value

    /** Converts a Direction to a Coordinate. The translation is as follows: Up: (0, -1), Right: (1, 0),
      * Down: (0, 1), Left: (-1, 0)
      * @param direction The Direction which we are translating.
      * @return Returns the translated Coordinate
      */
    def toCoordinate(direction: Direction.Value) : Coordinate = {
        var c : Coordinate = new Coordinate(0, 0)
        direction match {
            case Direction.up => c = c.subY(1)
            case Direction.right => c = c.addX(1)
            case Direction.down => c = c.addY(1)
            case Direction.left => c = c.subX(1)
        } // match direction
        c
    } // toCoordinate()

} // Direction


/** @constructor An abstract class which is inherited by Grid, Network and 2D Space. Projection is used by the Context.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 28 July 2018
  * @param _dimension the dimension of the Projection.
  */
abstract class Projection(_dimension: Dimension) extends Component {
    /** The window which contains the Projection. */
    protected val window = new Window(_dimension, this)

    /** An Array of current ColorSchemes on this Projection. */
    private var colorSchemes : Array[ColorScheme] = new Array[ColorScheme](0)

    /** The Context which this projection is part of. Context can have multiple Projections. */
    var context : Context = _

    /** Maps Agents to AgentUIs. Used for the Context to easily command projections. Projections are
      * responsible for creating and maintaining the AgentUIs. */
    protected var agentMap : Map[Agent, AgentUI] = Map[Agent, AgentUI]()


    /** An inverse of the 'agentMap'. Allows for mapping from AgentUI to Agent.
      * @return Returns the inverted Map.
      */
    protected def agentUIMap : Map[AgentUI, Agent] = agentMap.map(_.swap)


    /** Sets the Projection visible or hidden.
      * @param visible If true, the Projection will be visible, if false it will be hidden.
      */
    final def setVisible(visible : Boolean) : Unit = {
        window.visible = visible
        this.visible = visible
    } // setVisible()


    /** Adds a ColorScheme to the Projection.
      * @param c The ColorScheme we wish to add.
      */
    final def applyColorScheme(c : ColorScheme) : Unit = {
        colorSchemes = colorSchemes :+ c
        repaint()
    } // applyColorScheme


    /** Removes an active ColorScheme
      * @param c The ColorScheme which we want to remove
      */
    final def removeColorScheme(c : ColorScheme) : Unit = {
        colorSchemes = colorSchemes.drop(colorSchemes.indexOf(c) + 1)
        repaint()
    } // removeColorScheme


    /** Searches the active ColorSchemes for a ColorScheme which chooses the Cell color.
      * @param cell The Cell which we wish to retrieve the color of.
      * @return The color for the Cell.
      */
    final def getCellColor(cell : Cell) : Color = {
        for (c <- colorSchemes if c.use == ColorSchemeUse.cellColorUse) return c.getCellColor(cell)
        ColorSchemes.default.getCellColor(cell)
    } // getCellColor


    /** Searches the active ColorSchemes for a ColorScheme which chooses to not draw the AgentUI.
      * @return Returns a boolean if we should paint the AgentUI.
      */
    final def paintAgent : Boolean = {
        for (c <- colorSchemes if c.use == ColorSchemeUse.agentColorUse && !c.paintAgent) return false
        true
    } // paintAgent


    /** Gets the color for the agent. Called when we repaint the Projection
      * @param agent The AgentUI which we are retrieving the color of.
      * @return Color to represent the AgentUI. Color is decided based on which ColorSchemes are active.
      */
    final def getAgentColor(agent : AgentUI) : Color = {
        if (agent.color != null) return agent.color
        for (c <- colorSchemes if c.use == ColorSchemeUse.agentColorUse) return c.getAgentColor(agent)
        ColorSchemes.default.getAgentColor(agent)
    } // getAgentColor


    /** Retrieves all Agents which are of the specified types.
      * @param types is an array of the types of agents we wish to retrieve.
      * @return the list of Agents which are of the specified types.
      */
    final def getAgentsWithTypes(types: Array[AgentType.Value]) : Array[Agent] = context.agents.filter(a => types.contains(a.agentType))


    /** Retrieves the neighbor Agents which are of the specified types.
      * Uses the intersection of getNeighbors() and getAgentsWithTypes().
      * @param agent the Agent which we wish to retrieve the neighbors of.
      * @param radius is the distance in the neighborhood in which we want to get the Agents of.
      * @param types is an array of the types of agents we wish to retrieve.
      * @return the list of Agents in the neighborhood
      */
    final def getNeighborsOfTypes(agent : Agent, radius : Integer, types : Array[AgentType.Value]) : Array[Agent] =
        getNeighbors(agent, radius).intersect(getAgentsWithTypes(types))


    /** Retrieves the all neighbor Agents which are of the specified type.
      * @param agent the agent which we wish to retrieve the neighbors of.
      * @param radius is the distance in the neighborhood in which we want to get the Agents of.
      * @return the list of Agents in the neighborhood
      */
    def getNeighbors(agent : Agent, radius : Integer) : Array[Agent]


    /** Adds an Agent to the projection to the default location of (0, 0).
      * Network projection overrides the default location by assigning a random location.
      * @param agent the agent to ass to all projections
      */
    def addAgent(agent : Agent) : Unit = addAgent(agent, new Coordinate(0, 0))


    /** Adds an Agent to the given projection at a designated coordinate.
      * @param agent the Agent which we wish to add to the projection.
      * @param c the coordinate which we wish to add the Agent to.
      */
    def addAgent(agent : Agent, c : Coordinate) : Unit


    /** Adds a list of Agent to the given projection at a default coordinate.
      * @param agents the list of Agents which we wish to add to the projection.
      */
    def addAgents(agents : Array[Agent]) : Unit


    /** Removes an Agent from the projection.
      * @param agent the Agent which we wish to remove.
      */
    def removeAgent(agent : Agent) : Unit


    /** Removes all Agent from the projection. */
    def removeAllAgents() : Unit


    /** Moves an Agent from its current cell to one in near proximity using directions
      * Left, Right, Up, Down and a magnitude.
      * Projection implements move but does not use it. This is so classes which inherit
      * from projection do not have to support a move operation.
      * @param agent the Agent to move.
      * @param direction the direction which we want to move.
      * @param magnitude the amount of cells we wish to move.
      */
    def move(agent : Agent, direction : Direction.Value, magnitude : Int) : Unit = { }


    /** Moves an Agent from its current cell to one designated by the parameter coordinate.
      * Projection implements move but does not use it. This is so classes which inherit
      * from projection do not have to support a move operation.
      * @param agent the Agent which we wish to move.
      * @param c the coordinate which we wish to move the AgentUI to.
      */
    def move(agent : Agent, c : Coordinate) : Unit = { }
    
} // Projection
