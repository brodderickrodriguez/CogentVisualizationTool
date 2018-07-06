package cvt.context

import cvt.AgentUI

import scala.collection.mutable.ArrayBuffer
import scala.swing.Dimension


/**
  *
  * @param dimension the dimension of the context
  */
class Network(dimension: Dimension) extends Context(dimension: Dimension) {
    
    
    def refactorLayout() : Unit = {
    
    } // refactorLayout()
    
    
    def addAgent(agent : AgentUI) : Boolean = {
        false
    } // addAgent()
    
    
    def addAgents(agents: ArrayBuffer[AgentUI]) : Boolean = {
        false
    } // addAgents()
    
    
    def removeAgent(agent : AgentUI) : Boolean = {
        false
    } // removeAgent
    
    
    def removeAllAgents() : Unit = {
    
    } // removeAllAgents()


} // Network

