package cvt.context.space

import java.awt.{Dimension, Graphics2D}

import cvt.context.Context
import cvt.uiobject.{AgentUI, Coordinate}
import cvt.MockAgentType

import scala.collection.mutable.ArrayBuffer

/**
  *
  * @param dimension the dimension of the context
  */
class Space(dimension: Dimension) extends Context(dimension: Dimension) {
    
    
    override def paintComponent(graphics: Graphics2D): Unit = {
    
    } // paintComponent()
    
    def getNeighbors(agent : AgentUI, radius : Integer) : ArrayBuffer[AgentUI] = {
        null
    } // getNeighbors()
    
    override def getNeighborsOfTypes(agent : AgentUI, radius : Integer, types : Array[MockAgentType.Value]): ArrayBuffer[AgentUI] = {
        null
    }
    
    
    def addAgent(agent : AgentUI, c : Coordinate) : Unit = {
        // DO Stuff
        allAgents.append(agent)
    } // addAgent()
    
    def addAgents(agents: Array[AgentUI]) : Unit = {
        // DO Stuff
        allAgents ++ agents
    } // addAgents()
    
    def removeAgent(agent : AgentUI) : Unit = {
        // DO Stuff
        allAgents.remove(allAgents.indexOf(agent))
    } // removeAgent()
    
    override def removeAllAgents() : Unit = {
        // DO Stuff
        super.removeAllAgents()
    } // removeAllAgents()
    

} // Space

