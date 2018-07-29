package cvt.context.projection
import java.awt.{Dimension, Graphics2D}
import cvt.context.projection.AdjacencyStructure._
import cvt.context.projection.uiobject.{AgentUI, Coordinate}
import cvt.Agent


/**
  *
  * @param _dimension the dimension of the context
  */
class Network(_dimension: Dimension, dataStructure : AdjacencyStructure) extends Projection(_dimension) {


    /** Inherited from Component. Paints the window, grid, cells and all agents within the cells.
      *
      * @param graphics the graphics object.
      */
    override def paintComponent(graphics: Graphics2D): Unit = {
        if (!visible) return
    
        for ((a1, a2, _) <- dataStructure.connections) graphics.drawLine(a1.center.X, a1.center.Y, a2.center.X, a2.center.Y)
    
        if (paintAgent) {
            for (a <- dataStructure.agents) {
                graphics.setColor(getAgentColor(a))
                graphics.fillOval(a.absoluteLocation.X, a.absoluteLocation.Y, a.dimension.width, a.dimension.height)
            } // for agent in dataStructure
        } // paintAgent
        
    } // paintComponent()
    
    
    def addConnection(agent1 : Agent, agent2 : Agent, weight : Double, directed : Boolean = false) : Unit = {
        if (!agentMap.contains(agent1) || !agentMap.contains(agent2)) return
        val aui1 = agentMap(agent1)
        val aui2 = agentMap(agent2)
        dataStructure.addConnection(aui1, aui2, weight, directed)
        repaint()
    } // addConnection()
    
    
    def removeConnection(aui1 : AgentUI, aui2 : AgentUI) : Unit = {
        dataStructure.removeConnection(aui1, aui2)
        repaint()
    } // addConnection()

    
    override def getNeighbors(agent : Agent, radius : Integer) : Array[Agent] = {

        def recursivelyGetNeighbors(agent : AgentUI, radius : Integer) : Array[Agent] = {
            var results = Array[Agent]()
            if (radius == 0) return results

            for ((_, a2, _) <- dataStructure.connectionsOf(agent)) {
                for (a3 <- getNeighbors(agentUIMap(a2), radius - 1)) results = results :+ a3
                results = results :+ agentUIMap(a2)
            } // for each connection in connections of agent

            results.distinct
        } // recursivelyGetNeighbors()

        val aui = agentMap(agent)
        val results = recursivelyGetNeighbors(aui, radius)
        results.drop(results.indexOf(agent) + 1)
    } // getNeighbors()

    
    override def addAgent(agent : Agent, c : Coordinate) : Unit = {
        val aui = new AgentUI(agent)
        agentMap = agentMap + (agent -> aui)
        dataStructure.add(aui)

        val r = scala.util.Random
        aui.absoluteLocation = new Coordinate(r.nextInt(_dimension.width - aui.dimension.width - 20), r.nextInt(_dimension.height - aui.dimension.height - 30))
        repaint()
    } // addAgent()
    
    
    override def addAgents(agents: Array[Agent]) : Unit = for (a <- agents) addAgent(a)
    
    
    override def removeAgent(agent : Agent) : Unit = dataStructure.remove(agentMap(agent))
    
    
    override def removeAllAgents() : Unit = {
        for ((a1, _, _) <- dataStructure.connections) dataStructure.remove(a1)
        repaint()
    } // removeAllAgents()


    override def move(agent : Agent, direction : Direction.Value, magnitude : Int) : Unit = {  }


    override def move(agent : Agent, c : Coordinate) : Unit = { }
    
    
} // Network

