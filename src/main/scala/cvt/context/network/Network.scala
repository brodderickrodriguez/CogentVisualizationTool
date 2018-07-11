package cvt.context.network

import java.awt.{Dimension, Graphics2D}

import AdjacencyStructure._
import cvt.context.Context
import cvt.uiobject.{AgentUI, Coordinate}
import cvt.MockCogentType

import scala.collection.mutable.ArrayBuffer



/**
  *
  * @param dimension the dimension of the context
  */
class Network(dimension: Dimension) extends Context(dimension: Dimension) {
    println("[Network] initializing")
    
    private var dataStructure : AdjacencyStructure = new AdjacencyMatrix()
    
    
    override def paintComponent(graphics: Graphics2D): Unit = {
        println("[Network] paintComponent: top")
        
    } // paintComponent()
    
    
    def refactorLayout() : Unit = {
    
    } // refactorLayout()
    
    def getNeighbors(agent : AgentUI, radius : Integer) : ArrayBuffer[AgentUI] = {
        null
    } // getNeighbors()
    
    override def getNeighborsOfTypes(agent : AgentUI, radius : Integer, types : Array[MockCogentType.Value]): ArrayBuffer[AgentUI] = {
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

