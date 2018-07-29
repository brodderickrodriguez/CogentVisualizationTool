package cvt.context.projection
import java.awt.{Dimension, Graphics2D}
import cvt.context.projection.uiobject.AgentUI
import cvt.Agent


/** @constructor A 2D Space Projection. Inherits from Projection.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  * @param _dimension the dimension 2D Space. Unit is in points.
  */
class Space(_dimension: Dimension) extends Projection(_dimension) {
    window.title = "2D Space Projection"
    /**  */
    private var agentUIArray = Array[AgentUI]()

    /** Paints the 2D Space. Inherited from [[scala.swing.Component]].
      * @param graphics The Graphics component we are drawing on.
      */
    override def paintComponent(graphics: Graphics2D): Unit = {
        if (!visible || !paintAgent) return
        for (a <- agentUIArray) {
            graphics.setColor(getAgentColor(a))
            graphics.fillOval(a.absoluteLocation.X, a.absoluteLocation.Y, a.dimension.width, a.dimension.height)
        } // for each agent
    } // paintComponent()


    /** Gets the neighbors of an agent within a radius.
      * @param agent the agent which we wish to retrieve the neighbors of.
      * @param radius is the distance in the neighborhood in which we want to get the Agents of.
      * @return the list of Agents in the neighborhood
      */
    def getNeighbors(agent : Agent, radius : Integer) : Array[Agent] = {
        val aui = agentMap(agent)
        if (aui == null) return null

        def withinBounds(a : AgentUI) : Boolean = {
            val x = Math.abs(aui.absoluteLocation.X - a.absoluteLocation.X) <= radius
            val y = Math.abs(aui.absoluteLocation.Y - a.absoluteLocation.Y) <= radius
            x && y
        } // withinBounds()

        for (a <- agentUIArray if a != aui && withinBounds(a)) yield agentUIMap(a)
    } // getNeighbors()


    /** Adds an Agent to the 2D Space Projection at a specific Coordinate.
      * @param agent the Agent which we wish to add to the projection.
      * @param c the coordinate which we wish to add the Agent to.
      */
    override def addAgent(agent : Agent, c : Coordinate) : Unit = {
        val aui = new AgentUI(agent)
        val r = scala.util.Random
        aui.absoluteLocation = new Coordinate(r.nextInt(_dimension.width - aui.dimension.width - 20), r.nextInt(_dimension.height - aui.dimension.height - 30))
        agentMap = agentMap + (agent -> aui)
        agentUIArray = agentUIArray :+ aui
        move(agent, c)
    } // addAgent()


    /** Add an Array of Agents to to the 2D Space Projection at a default location of (0, 0).
      * @param agents the list of Agents which we wish to add to the projection.
      */
    override def addAgents(agents: Array[Agent]) : Unit = for (a <- agents) move(a, new Coordinate(0, 0))


    /** Removes a specific Agent from the 2D Space Projection.
      * @param agent the Agent which we wish to remove.
      */
    override def removeAgent(agent: Agent): Unit = {
        val aui = agentMap(agent)
        if (!agentUIArray.contains(aui)) return
        agentUIArray = agentUIArray.drop(agentUIArray.indexOf(aui))
    } // removeAgent()


    /** Removes all Agents from ths 2D Space Projection.
      * Can be used independently of the Context.
      */
    override def removeAllAgents(): Unit = {
        agentUIArray =  Array[AgentUI]()
        repaint()
    } // removeAllAgents()


    /** Moves a specific Agent in a [[Direction]].
      * @param agent the Agent to move.
      * @param direction the direction which we want to move.
      * @param magnitude the amount of cells we wish to move.
      */
    override def move(agent : Agent, direction : Direction.Value, magnitude : Int) : Unit = {
        if (!agentMap.contains(agent)) return
        val aui = agentMap(agent)
        val newCoordinate = aui.absoluteLocation.add(Direction.toCoordinate(direction).multiply(magnitude))
        move(agent, newCoordinate)
    } // move()


    /** Moves a specific [[Agent]] to a desired Coordinate.
      * @param agent the [[Agent]] which we wish to move.
      * @param c the coordinate which we wish to move the AgentUI to.
      */
    override def move(agent : Agent, c : Coordinate) : Unit = {
        if (!agentMap.contains(agent)) return
        val aui = agentMap(agent)
        aui.absoluteLocation = c
        repaint()
    } // move()

} // Space


