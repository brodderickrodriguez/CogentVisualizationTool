package cvt

import java.awt.Color

import cvt.context.Context


object ColorSchemeUse extends Enumeration {
    val defaultColorUse : Value = Value
    val backgroundColorUse : Value = Value
    val agentColorUse : Value = Value
    val cellColorUse : Value = Value
    val connectionColorUse : Value = Value
}


object ColorSchemes extends Enumeration {
    val default : ColorScheme = new DefaultColorScheme
    val cellColorByPopulation : ColorScheme = new CellColorByPopulationColorScheme
    val agentColorRandom : ColorScheme = new agentColorRandomColorScheme
}


private class agentColorRandomColorScheme extends ColorScheme {
    use = ColorSchemeUse.agentColorUse
    
    override def getAgentColor(agentUI: AgentUI): Color = {
        val r = scala.util.Random
        new Color(r.nextFloat(), r.nextFloat(), r.nextFloat())
    } // getAgentColor()
    
} // CogentColorRandomColorScheme


private class CellColorByPopulationColorScheme extends ColorScheme {
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


private class DefaultColorScheme extends ColorScheme {
    use = ColorSchemeUse.defaultColorUse
} // Default


abstract class ColorScheme {
    var paintAgent : Boolean = true
    var use : ColorSchemeUse.Value = _
    def getAgentColor(agentUI: AgentUI) : Color = { Color.blue } // getAgentColor()
    def getCellColor(cell: Cell) : Color = { Color.gray } // getCellColor()
    def getBackgroundColor(context : Context) : Color = { Color.white } // getBackgroundColor()
} // ColorScheme




