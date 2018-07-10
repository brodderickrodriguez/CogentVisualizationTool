package cvt.context

import cvt._
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
    //println("[Context] Initializing")
    
    protected val window = new Window(dimension, this)
    val allAgents : ArrayBuffer[AgentUI] = new ArrayBuffer[AgentUI]()
    val colorSchemes : ArrayBuffer[ColorScheme] = new ArrayBuffer[ColorScheme]()
    
    
    def applyColorScheme(c : ColorScheme) : Unit = {
        colorSchemes.append(c)
        repaint()
    } // applyColorScheme
    
    
    def removeColorScheme(c : ColorScheme) : Unit = {
        if (!colorSchemes.contains(c)) return
        colorSchemes.remove(colorSchemes.indexOf(c))
        repaint()
    } // removeColorScheme
    
    
    def getCellColor(cell : Cell) : Color = {
        for (c <- colorSchemes if c.use == ColorSchemeUse.cellColorUse) return c.getCellColor(cell)
        ColorSchemes.default.getCellColor(cell)
    } // getCellColor
    
    
    def getAgentsWithType(t :  MockCogentType.Value) : ArrayBuffer[AgentUI] = for (a <- allAgents if t == a.mockAgent.agentType) yield a
    
    
    def getAgentsWithTypes(types: Array[MockCogentType.Value]) : ArrayBuffer[AgentUI] = for (a <- allAgents if types.contains(a.mockAgent.agentType)) yield a
    
    
    def getNeighbors(agent : AgentUI, radius : Integer) : ArrayBuffer[AgentUI]
    
    def addAgent(agent : AgentUI) : Unit
    
    def addAgent(agent : AgentUI, c : Coordinate) : Unit
    
    def addAgents(agents: Array[AgentUI]) : Unit
    
    def removeAgent(agent : AgentUI) : Boolean
    
    def removeAllAgents() : Unit
    
} // Context
