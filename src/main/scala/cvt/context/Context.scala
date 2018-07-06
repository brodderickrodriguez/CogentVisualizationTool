package cvt.context

import cvt._
import java.awt.Graphics2D

import scala.swing._
import scala.collection.mutable.ArrayBuffer


/**
  *
  * @param dimension the dimension of the context
  */
abstract class Context(dimension: Dimension) extends Component   {
    println("Context Initializing")
    
    protected val window = new Window(dimension, this)
    private val allAgents : ArrayBuffer[AgentUI] = new ArrayBuffer[AgentUI]()
    private val colorSchemes : ArrayBuffer[ColorScheme] = new ArrayBuffer[ColorScheme]()
    
    protected object Direction extends Enumeration {
        val up: Value = Value(-1)
        val right: Value = Value(1)
        val down: Value = Value(1)
        val left: Value  = Value(-1)
    } // Direction
    

    
    protected override def paintComponent(g : Graphics2D) : Unit = {
        println("painting context")
    } // paintComponent()
    
    

    def getAgentsWithTypes(array: Array[Int]) : ArrayBuffer[cvt.AgentUI] = {
        allAgents
    } // getAllAgents()
    

    def addAgent(agent : AgentUI) : Boolean
    
    
    def addAgents(agents: ArrayBuffer[AgentUI]) : Boolean
    
    
    def removeAgent(agent : AgentUI) : Boolean
    
    
    def removeAllAgents() : Unit
    
    
    def applyColorScheme(c : ColorScheme) : Boolean = {
        colorSchemes.append(c)
        repaint()
        false
    } // applyColorScheme
    
    
    def removeColorScheme(c : ColorScheme) : Boolean = {
        if (colorSchemes.indexOf(c) != -1) {
            colorSchemes.remove(colorSchemes.indexOf(c))
            repaint()
            return true
        } // if colorScheme is active
        false
    } // removeColorScheme
    
    
} // Context
