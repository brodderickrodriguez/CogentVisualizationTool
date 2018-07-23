package cvt.context.network

import java.awt.{Dimension, Graphics2D}

import cvt.context.{Context, ContextController}
import cvt.uiobject.{AgentUI, Coordinate}
import cvt.MockAgentType


object AdjacencyStructures extends Enumeration {
    val matrix : AdjacencyStructure = new AdjacencyMatrix
    val list : AdjacencyStructure = new AdjacencyList
} // AdjacencyStructures

/**
  *
  * @param _dimension the dimension of the context
  */
class Network(_dimension: Dimension, dataStructure : AdjacencyStructure, controller : ContextController) extends Context(_dimension, controller = controller) {
    println("[Network] initializing")
    
    
    override def paintComponent(graphics: Graphics2D): Unit = {
    
        for ((a1, a2, _) <- dataStructure.connections) graphics.drawLine(a1.center.X, a1.center.Y, a2.center.X, a2.center.Y)
    
        if (paintAgent) {
            for (a <- dataStructure.entries) {
                graphics.setColor(getAgentColor(a))
                graphics.fillOval(a.absoluteLocation.X, a.absoluteLocation.Y, a.dimension.width, a.dimension.height)
            } // for agent in dataStructure
        } // paintAgent
        
    } // paintComponent()
    
    
    def addConnection(aui1 : AgentUI, aui2 : AgentUI, weight : Double, directed : Boolean) : Unit = {
        dataStructure.addConnection(aui1, aui2, weight, directed)
        repaint()
    } // addConnection()
    
    
    def removeConnection(aui1 : AgentUI, aui2 : AgentUI) : Unit = {
        dataStructure.removeConnection(aui1, aui2)
        repaint()
    } // addConnection()


    override def getNeighborsOfTypes(agent : AgentUI, radius : Integer, types : Array[MockAgentType.Value]): Array[AgentUI] = {
        for (a2 <-  getNeighbors(agent, radius) if types.contains(a2.agentType)) yield a2
    } // getNeighborsOfTypes()

    
    override def getNeighbors(agent : AgentUI, radius : Integer) : Array[AgentUI] = {
        val results = recursivelyGetNeighbors(agent, radius)
        results.drop(results.indexOf(agent))
    } // getNeighbors()


    private def recursivelyGetNeighbors(agent : AgentUI, radius : Integer) : Array[AgentUI] = {
        var results = Array[AgentUI]()
        if (radius == 0) return results

        for ((_, a2, _) <- dataStructure.connectionsOf(agent)) {
            for (a3 <- getNeighbors(a2, radius - 1)) results = results :+ a3
            results = results :+ a2
        } // for each connection in connections of agent

        results.distinct
    } // recursivelyGetNeighbors()

    
    override def addAgent(agent : AgentUI, c : Coordinate) : Unit = {
        val r = scala.util.Random
        agent.absoluteLocation = new Coordinate(r.nextInt(_dimension.width - agent.dimension.width - 20), r.nextInt(_dimension.height - agent.dimension.height - 30))
        dataStructure.add(agent)
        repaint()
    } // addAgent()
    
    
    override def addAgents(agents: Array[AgentUI]) : Unit = for (a <- agents) addAgent(a)
    
    
    override def removeAgent(agent : AgentUI) : Unit = dataStructure.remove(agent)
    
    
    override def removeAllAgents() : Unit = {
        for ((a1, _, _) <- dataStructure.connections) dataStructure.remove(a1)
        super.removeAllAgents()
    } // removeAllAgents()
    
    
} // Network

