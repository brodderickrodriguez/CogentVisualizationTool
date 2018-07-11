package cvt.context

import cvt._
import cvt.MockCogentType

import scala.swing.{Component, Dimension}
import scala.collection.mutable.ArrayBuffer
import java.awt.Color

import cvt.context.grid.Cell
import cvt.uiobject.{AgentUI, AgentUINotification, Coordinate}


object Direction extends Enumeration {
    val up: Value = Value
    val right: Value = Value
    val down: Value = Value
    val left: Value  = Value
} // Direction

/**
  *
  * @param _dimension the dimension of the context
  */
abstract class Context(_dimension: Dimension) extends Component   {
    protected val window = new Window(_dimension, this)
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
    
    
    /** Adds an AgentUI to the given context at a default coordinate.
      *
      * @param agent the AgentUI which we wish to add to the context.
      * @param c the coordinate which we wish to add the AgentUI to.
      */
    def addAgent(agent : AgentUI) : Unit = addAgent(agent, new Coordinate(0, 0))
    
    
    /** Adds an AgentUI to the given context at a designated coordinate.
      *
      * @param agent the AgentUI which we wish to add to the context.
      * @param c the coordinate which we wish to add the AgentUI to.
      */
    def addAgent(agent : AgentUI, c : Coordinate) : Unit
    
    
    /** Adds a list of AgentUIs to the given context at a default coordinate.
      *
      * @param agents the list of AgentUIs which we wish to add to the context.
      */
    def addAgents(agents: Array[AgentUI]) : Unit
    
    
    /** Removes an AgentUI from the context.
      *
      * @param agent the AgentUI which we wish to remove.
      */
    def removeAgent(agent : AgentUI) : Unit
    
    /** Removes all AgentUIs form the context.
      *
      */
    def removeAllAgents() : Unit = {
        // send a notification to all AgentUIs in the context that we are removing agents
        sendNotificationToAllAgents(AgentUINotification.removingAllAgentsFromCell)
        // empty the array
        allAgents.clear()
        repaint()
    } // removeAllAgents()
    
} // Context
