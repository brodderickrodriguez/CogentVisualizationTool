package cvt
import java.awt.Color

import cvt.context.Context
import cvt.context.grid.Cell
import cvt.uiobject.AgentUI


object ColorSchemeUse extends Enumeration {
    val defaultColorUse : Value = Value
    val backgroundColorUse : Value = Value
    val agentColorUse : Value = Value
    val cellColorUse : Value = Value
    val connectionColorUse : Value = Value
} // ColorSchemeUse


object ColorSchemes extends Enumeration {
    val default : ColorScheme = new Default
    val cellColorByPopulation : ColorScheme = new CellColorByPopulation
    val cellColorByAgentType : ColorScheme = new CellColorByAgentType
    val agentColorRandom : ColorScheme = new AgentColorRandom
    val doNotPaintAgent : ColorScheme = new DoNotPaintAgent
} // ColorSchemes


private class DoNotPaintAgent extends ColorScheme {
    use = ColorSchemeUse.agentColorUse
    paintAgent = false
} // DoNotPaintAgent


private class AgentColorRandom extends ColorScheme {
    use = ColorSchemeUse.agentColorUse
    
    override def getAgentColor(agentUI: AgentUI): Color = {
        val r = scala.util.Random
        new Color(r.nextFloat(), r.nextFloat(), r.nextFloat())
    } // getAgentColor()
    
} // CogentColorRandomColorScheme


private class CellColorByAgentType extends ColorScheme {
    use = ColorSchemeUse.cellColorUse
    
    override def getCellColor(cell: Cell) : Color = {
        var color = Color.gray
        if (cell.agents.isEmpty) return color
        
        cell.agents.head.agentType match {
            case MockAgentType.exciting  => color = Color.yellow
            case MockAgentType.boring  => color = Color.white
            case MockAgentType.daring  => color = Color.green
            case default => Color.gray
        } // match
        color
    } // getCellColor()
    
} // GridCellColorByPopulation()


private class CellColorByPopulation extends ColorScheme {
    use = ColorSchemeUse.cellColorUse
    
    override def getCellColor(cell: Cell) : Color = {
        var color = Color.black
        cell.agents.length match {
            case 0 => color = Color.lightGray
            case 1 => color = Color.magenta
            case 2 => color = Color.blue
            case 3 => color = Color.green
            case 4 => color = Color.yellow
            case 5 => color = Color.red
            case default => Color.black
        } // match
        color
    } // getCellColor()
    
} // GridCellColorByPopulation()


private class Default extends ColorScheme {
    use = ColorSchemeUse.defaultColorUse
} // Default


abstract class ColorScheme {
    var paintAgent : Boolean = true
    var use : ColorSchemeUse.Value = _
    def getAgentColor(agentUI: AgentUI) : Color = { Color.blue } // getAgentColor()
    def getCellColor(cell: Cell) : Color = { Color.gray } // getCellColor()
    def getBackgroundColor(context : Context) : Color = { Color.white } // getBackgroundColor()
} // ColorScheme




