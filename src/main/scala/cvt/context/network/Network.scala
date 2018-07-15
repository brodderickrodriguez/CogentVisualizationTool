package cvt.context.network

import java.awt.{Dimension, Graphics2D}

import cvt.context.{Context, ContextController}
import cvt.uiobject.{AgentUI, Coordinate}
import cvt.MockAgentType

import scala.collection.mutable.ArrayBuffer



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
    
    
        for (a <- dataStructure.entries) {
            graphics.setColor(getAgentColor(a))
            graphics.fillOval(a.absoluteLocation.X, a.absoluteLocation.Y, a.dimension.width, a.dimension.height)
        }
        
    } // paintComponent()
    
    
    def addConnection(aui1 : AgentUI, aui2 : AgentUI, weight : Double, directed : Boolean) : Unit = {
        dataStructure.addConnection(aui1, aui2, weight, directed)
        repaint()
    } // addConnection()
    

    
    def getNeighbors(agent : AgentUI, radius : Integer) : ArrayBuffer[AgentUI] = {
        null
    } // getNeighbors()
    
    
    override def getNeighborsOfTypes(agent : AgentUI, radius : Integer, types : Array[MockAgentType.Value]): ArrayBuffer[AgentUI] = {
        null
    }
    
    
    def addAgent(agent : AgentUI, c : Coordinate) : Unit = {
        val r = scala.util.Random
        agent.absoluteLocation = new Coordinate(r.nextInt(_dimension.width - 25), r.nextInt(_dimension.height - 25))
        dataStructure.add(agent)
        repaint()
    } // addAgent()
    
    
    def addAgents(agents: Array[AgentUI]) : Unit = for (a <- agents) addAgent(a)
    
    
    def removeAgent(agent : AgentUI) : Unit = dataStructure.remove(agent)
    
    
    override def removeAllAgents() : Unit = {
        for ((a1, _, _) <- dataStructure.connections) dataStructure.remove(a1)
        super.removeAllAgents()
    } // removeAllAgents()
    
    
} // Network

