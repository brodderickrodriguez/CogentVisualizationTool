package cvt.context

import cvt._
import cvt.MockCogentType
import cvt.AgentUINotification
import scala.swing.{Dimension, Component}
import scala.collection.mutable.ArrayBuffer
import java.awt.Color


object Direction extends Enumeration {
    val up: Value = Value
    val right: Value = Value
    val down: Value = Value
    val left: Value  = Value
} // Direction

/**
  *
  * @param dimension the dimension of the context
  */
abstract class Context(dimension: Dimension) extends Component   {
    protected val window = new Window(dimension, this)
    protected val allAgents : ArrayBuffer[AgentUI] = new ArrayBuffer[AgentUI]()
    protected val colorSchemes : ArrayBuffer[ColorScheme] = new ArrayBuffer[ColorScheme]()
    
    
    def applyColorScheme(c : ColorScheme) : Unit = {
        colorSchemes += c
        repaint()
    } // applyColorScheme
    
    
    def removeColorScheme(c : ColorScheme) : Unit = {
        colorSchemes -= c
        repaint()
    } // removeColorScheme
    
    
    def getCellColor(cell : Cell) : Color = {
        for (c <- colorSchemes if c.use == ColorSchemeUse.cellColorUse) return c.getCellColor(cell)
        ColorSchemes.default.getCellColor(cell)
    } // getCellColor
    
    
    def paintAgent : Boolean = {
        for (c <- colorSchemes if c.use == ColorSchemeUse.agentColorUse && !c.paintAgent) return false
        true
    } // paintAgent
    
    
    def getAgentColor(agent : AgentUI) : Color = {
        for (c <- colorSchemes if c.use == ColorSchemeUse.agentColorUse) return c.getAgentColor(agent)
        ColorSchemes.default.getAgentColor(agent)
    } // getAgentColor
    
    
    def sendNotificationToAllAgents(notification : AgentUINotification.Value): Unit = for (a <- allAgents) a.receiveNotification(notification)
    
    
    def getAgentsWithType(t :  MockCogentType.Value) : ArrayBuffer[AgentUI] = for (a <- allAgents if t == a.agentType) yield a
    
    
    def getAgentsWithTypes(types: Array[MockCogentType.Value]) : ArrayBuffer[AgentUI] = for (a <- allAgents if types.contains(a.agentType)) yield a
    
    
    def getNeighbors(agent : AgentUI, radius : Integer) : ArrayBuffer[AgentUI]
    
    def getNeighborsOfTypes(agent : AgentUI, radius : Integer, types : Array[MockCogentType.Value]): ArrayBuffer[AgentUI]
    
    def addAgent(agent : AgentUI) : Unit = addAgent(agent, new Coordinate(0, 0))
    
    def addAgent(agent : AgentUI, c : Coordinate) : Unit
    
    def addAgents(agents: Array[AgentUI]) : Unit
    
    def removeAgent(agent : AgentUI) : Unit
    
    def removeAllAgents() : Unit = {
        sendNotificationToAllAgents(AgentUINotification.removingAllAgentsFromCell)
        allAgents.clear()
        repaint()
    } // removeAllAgents()
    
} // Context
