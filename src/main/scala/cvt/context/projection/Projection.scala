package cvt.context.projection

import scala.swing.{Component, Dimension}
import java.awt.Color

import cvt.context.Context
import cvt.context.projection.uiobject.{AgentUI, AgentUINotification, Cell, Coordinate}
import cvt.MockAgentType


object Direction extends Enumeration {
    val up: Value = Value
    val right: Value = Value
    val down: Value = Value
    val left: Value  = Value
} // Direction

/**
  *
  * @param context the Context in which we are projecting
  */
abstract class Projection(dimension: Dimension, val context : Context) extends Component {
    protected val window = new Window(dimension, this)
    protected var colorSchemes : Array[ColorScheme] = new Array[ColorScheme](0)
    
    
    def applyColorScheme(c : ColorScheme) : Unit = {
        colorSchemes = colorSchemes :+ c
        repaint()
    } // applyColorScheme
    
    
    def removeColorScheme(c : ColorScheme) : Unit = {
        colorSchemes = colorSchemes.drop(colorSchemes.indexOf(c) + 1)
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
        if (agent.color != null) return agent.color
        for (c <- colorSchemes if c.use == ColorSchemeUse.agentColorUse) return c.getAgentColor(agent)
        ColorSchemes.default.getAgentColor(agent)
    } // getAgentColor

    
    def sendNotificationToAllAgents(notification : AgentUINotification.Value): Unit = for (a <- context.agents) a.receiveNotification(notification)
    
    
    def getAgentsWithType(t : MockAgentType.Value) : Array[AgentUI] = for (a <- context.agents if t == a.agentType) yield a
    
    
    def getAgentsWithTypes(types: Array[MockAgentType.Value]) : Array[AgentUI] = for (a <- context.agents if types.contains(a.agentType)) yield a
    
    
    def getNeighbors(agent : AgentUI, radius : Integer) : Array[AgentUI]
    
    
    def getNeighborsOfTypes(agent : AgentUI, radius : Integer, types : Array[MockAgentType.Value]): Array[AgentUI]
    
    
    /** Adds an AgentUI to the given Projection at a default coordinate.
      *
      * @param agent the AgentUI which we wish to add to the Projection.
      */
    def addAgent(agent : AgentUI) : Unit = addAgent(agent, new Coordinate(0, 0))
    
    
    /** Adds an AgentUI to the given Projection at a designated coordinate.
      *
      * @param agent the AgentUI which we wish to add to the Projection.
      * @param c the coordinate which we wish to add the AgentUI to.
      */
    def addAgent(agent : AgentUI, c : Coordinate) : Unit
    
    
    /** Adds a list of AgentUIs to the given Projection at a default coordinate.
      *
      * @param agents the list of AgentUIs which we wish to add to the Projection.
      */
    def addAgents(agents: Array[AgentUI]) : Unit = repaint()
    
    
    /** Removes an AgentUI from the Projection.
      *
      * @param agent the AgentUI which we wish to remove.
      */
    def removeAgent(agent : AgentUI) : Unit = repaint()

    
    
    /** Removes all AgentUIs form the Projection.
      *
      */
    def removeAllAgents() : Unit = {
        // send a notification to all AgentUIs in the Projection that we are removing agents
        sendNotificationToAllAgents(AgentUINotification.removingAllAgentsFromCell)
        // empty the array
        context.agents = Array[AgentUI]()
        repaint()
    } // removeAllAgents()
    
    
    def move(agent : AgentUI, direction : Direction.Value, magnitude : Int) : Unit = { }
    
    def move(agent : AgentUI, c : Coordinate) : Unit = { println("Projection move") }
    
} // Projection
