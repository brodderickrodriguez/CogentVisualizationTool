package cvt.context.projection

import scala.swing.{Component, Dimension}

import java.awt.Color

import cvt.context.Context
import cvt.context.projection.uiobject.{AgentUI, Cell, Coordinate}
import cvt.{Agent, AgentType}


object Direction extends Enumeration {
    val up: Value = Value
    val right: Value = Value
    val down: Value = Value
    val left: Value  = Value

    def toCoordinate(direction: Direction.Value) : Coordinate = {
        var c : Coordinate = new Coordinate(0, 0)
        direction match {
            case Direction.up => c = c.subY(1)
            case Direction.right => c = c.addX(1)
            case Direction.down => c = c.addY(1)
            case Direction.left => c = c.subX(1)
        } // match direction
        c
    } // toCoordinate()
} // Direction



abstract class Projection(dimension: Dimension) extends Component {
    protected val window = new Window(dimension, this)
    protected var colorSchemes : Array[ColorScheme] = new Array[ColorScheme](0)
    var context : Context = _
    protected var agentMap : Map[Agent, AgentUI] = Map[Agent, AgentUI]()

    protected def agentUIMap : Map[AgentUI, Agent] = agentMap.map(_.swap)


    def setVisible(visible : Boolean) : Unit = {
        window.visible = visible
        this.visible = visible
    } // setVisible()
    
    
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


    def getAgentsWithTypes(types: Array[AgentType.Value]) : Array[Agent] = for (a <- context.agents if types.contains(a.agentType)) yield a
    
    
    def getNeighbors(agent : Agent, radius : Integer) : Array[Agent]
    
    
    def getNeighborsOfTypes(agent : Agent, radius : Integer, types : Array[AgentType.Value]) : Array[Agent]
    

    def addAgent(agent : Agent) : Unit = addAgent(agent, new Coordinate(0, 0))
    

    def addAgent(agent : Agent, c : Coordinate) : Unit
    

    def addAgents(agents : Array[Agent]) : Unit
    

    def removeAgent(agent : Agent) : Unit = repaint()


    def removeAllAgents() : Unit


    def move(agent : Agent, direction : Direction.Value, magnitude : Int) : Unit


    def move(agent : Agent, c : Coordinate) : Unit
    
} // Projection
