package cvt.context.network

import java.awt.{Dimension, Graphics2D}
import cvt.context.Context
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
class Network(_dimension: Dimension, dataStructure : AdjacencyStructure) extends Context(_dimension: Dimension) {
    println("[Network] initializing")
    
  //  private var dataStructure : AdjacencyStructure = chosenStructure
    
    
    override def paintComponent(graphics: Graphics2D): Unit = {
        println("[Network] paintComponent: top")
        
    } // paintComponent()
    

    
    def getNeighbors(agent : AgentUI, radius : Integer) : ArrayBuffer[AgentUI] = {
        null
    } // getNeighbors()
    
    override def getNeighborsOfTypes(agent : AgentUI, radius : Integer, types : Array[MockAgentType.Value]): ArrayBuffer[AgentUI] = {
        null
    }
    
    
    def addAgent(agent : AgentUI, c : Coordinate) : Unit = {
        // DO Stuff
    } // addAgent()
    
    def addAgents(agents: Array[AgentUI]) : Unit = {
        // DO Stuff
        allAgents ++ agents
    } // addAgents()
    
    def removeAgent(agent : AgentUI) : Unit = {
        // DO Stuff
    } // removeAgent()
    
    override def removeAllAgents() : Unit = {
        // DO Stuff
        super.removeAllAgents()
    } // removeAllAgents()
    
    
} // Network

