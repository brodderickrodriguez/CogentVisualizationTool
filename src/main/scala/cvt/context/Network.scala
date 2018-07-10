package cvt.context
import cvt.{Cell, Coordinate, AgentUI, AgentUINotification, MockCogentType}
import java.awt.{Dimension, Graphics2D}
import scala.collection.mutable.ArrayBuffer

/**
  *
  * @param dimension the dimension of the context
  */
class Network(dimension: Dimension) extends Context(dimension: Dimension) {
    println("[Network] initializing")
    
    
    override def paintComponent(graphics: Graphics2D): Unit = {
        println("[Network] paintComponent: top")
        
    } // paintComponent()
    
    
    def refactorLayout() : Unit = {
    
    } // refactorLayout()
    
    def getNeighbors(agent : AgentUI, radius : Integer) : ArrayBuffer[AgentUI] = {
        null
    } // getNeighbors()
    
    def addAgent(agent : AgentUI) : Unit = {
        // DO Stuff
        allAgents.append(agent)
    } // addAgent()
    
    def addAgent(agent : AgentUI, c : Coordinate) : Unit = {
        // DO Stuff
        allAgents.append(agent)
    } // addAgent()
    
    def addAgents(agents: Array[AgentUI]) : Unit = {
        // DO Stuff
        allAgents ++ agents
    } // addAgents()
    
    def removeAgent(agent : AgentUI) : Boolean = {
        // DO Stuff
        allAgents.remove(allAgents.indexOf(agent))
        false
    } // removeAgent()
    
    def removeAllAgents() : Unit = {
        // DO Stuff
        allAgents.clear()
    } // removeAllAgents()


} // Network

