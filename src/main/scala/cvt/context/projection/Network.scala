package cvt.context.projection
import java.awt.{Dimension, Graphics2D}
import cvt.context.projection.AdjacencyStructure._
import cvt.context.projection.uiobject.AgentUI
import cvt.Agent


/** @constructor A Network Projection. Inherits from Projection.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  * @param _dimension     the dimension of the Network. Unit is in points.
  * @param _dataStructure The DataStructure of choice. AdjacencyMatrix and AdjacencyList available.
  *
  * {{{
  * val context = new Context()
  * val network = new Network(new Dimension(500, 500), new AdjacencyList())
  *
  * context.addProjection(network)
  * context.applyColorScheme(ColorSchemes.agentColorRandom)
  *
  * val a1 = new Agent()
  * val a2 = new Agent()
  *
  * context.addAgent(a1)
  * context.addAgent(a2)
  *
  * network.addConnection(a1, a2, 1, directed = true)
  * }}}
  */
class Network(_dimension: Dimension, _dataStructure : AdjacencyStructure) extends Projection(_dimension) {
    window.title = "Network Projection"


    /** Inherited from Component. Paints the window, Agents and connections.
      * @param graphics the graphics component.
      */
    override def paintComponent(graphics: Graphics2D): Unit = {
        if (!visible) return

        for ((a1, a2, _) <- _dataStructure.connections) graphics.drawLine(a1.center.X, a1.center.Y, a2.center.X, a2.center.Y)
    
        if (paintAgent) {
            for (a <- _dataStructure.agents) {
                graphics.setColor(getAgentColor(a))
                graphics.fillOval(a.absoluteLocation.X, a.absoluteLocation.Y, a.dimension.width, a.dimension.height)
            } // for agent in dataStructure
        } // paintAgent
        
    } // paintComponent()


    /** Adds a connection between two Agents in the Network.
      * @param agent1 The Agent from which the connection begins. Connection from agent1 to agent2.
      * @param agent2 The Agent from which the connection ends. Connection from agent1 to agent2.
      * @param weight The Weight of the connection.
      * @param directed A boolean to determine if the connection is directed or undirected.
      */
    def addConnection(agent1 : Agent, agent2 : Agent, weight : Double, directed : Boolean = false) : Unit = {
        if (!agentMap.contains(agent1) || !agentMap.contains(agent2)) return
        val aui1 = agentMap(agent1)
        val aui2 = agentMap(agent2)
        _dataStructure.addConnection(aui1, aui2, weight, directed)
        repaint()
    } // addConnection()


    /** Removes a connection between two Agents in the Network. A connection to and from Agents must be known.
      * Meaning, a connection (A, B) is not the same as (B, A).
      * @param agent1 The From Agent. Connection from agent1 to agent2.
      * @param agent2 The To Agent. Connection from agent1 to agent2.
      */
    def removeConnection(agent1 : Agent, agent2 : Agent) : Unit = {
        if (!agentMap.contains(agent1) || !agentMap.contains(agent2)) return
        val aui1 = agentMap(agent1)
        val aui2 = agentMap(agent2)
        _dataStructure.removeConnection(aui1, aui2)
        repaint()
    } // addConnection()


    /** Recursively gets the neighbors of an Agent using connections between Agents.
      * @param agent the agent which we wish to retrieve the neighbors of.
      * @param radius is the distance in the neighborhood in which we want to get the Agents of. Unit is in connections
      *               between Agents.
      * @return the list of Agents in the neighborhood
      */
    override def getNeighbors(agent : Agent, radius : Integer) : Array[Agent] = {

        /** Traverses the Network in all directions to find all Agents within the radius.
          * @param agent The Agent which we are currently operating on.
          * @param radius The current radius. I.E. the number of remaining connections to traverse.
          * @return An array of Agents within a radius.
          */
        def recursivelyGetNeighbors(agent : AgentUI, radius : Integer) : Array[Agent] = {
            var results = Array[Agent]()
            if (radius == 0) return results

            for ((_, a2, _) <- _dataStructure.connectionsOf(agent)) {
                for (a3 <- getNeighbors(agentUIMap(a2), radius - 1)) results = results :+ a3
                results = results :+ agentUIMap(a2)
            } // for each connection in connections of agent

            results.distinct
        } // recursivelyGetNeighbors()

        val aui = agentMap(agent)
        val results = recursivelyGetNeighbors(aui, radius)
        results.drop(results.indexOf(agent) + 1)
    } // getNeighbors()


    /** Adds an Agent to a random Coordinate on the Projection.
      * @param agent the Agent which we wish to add to the projection.
      * @param c the coordinate which we wish to add the Agent to.
      */
    override def addAgent(agent : Agent, c : Coordinate) : Unit = {
        val aui = new AgentUI(agent)
        agentMap = agentMap + (agent -> aui)
        _dataStructure.add(aui)
        val r = scala.util.Random
        aui.absoluteLocation = new Coordinate(r.nextInt(_dimension.width - aui.dimension.width - 20), r.nextInt(_dimension.height - aui.dimension.height - 30))
        repaint()
    } // addAgent()


    /** Adds an Array of Agents to the Network Projection.
      * @param agents the list of Agents which we wish to add to the projection.
      */
    override def addAgents(agents: Array[Agent]) : Unit = for (a <- agents) addAgent(a)


    /** Removes An Agent from the Network Projection.
      * @param agent the Agent which we wish to remove.
      */
    override def removeAgent(agent : Agent) : Unit = _dataStructure.remove(agentMap(agent))


    /** Removes all Agents from the Network Projection. */
    override def removeAllAgents() : Unit = {
        for (a <- _dataStructure.agents) _dataStructure.remove(a)
        repaint()
    } // removeAllAgents()
    
    
} // Network

