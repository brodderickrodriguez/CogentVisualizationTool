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
}


private class CellColorByPopulationColorScheme extends ColorScheme {
    use = ColorSchemeUse.cellColorUse
    
    override def getCellColor(cell: Cell) : Color = {
      //  val numberOfAgents = cell.grid.allAgents.length
        //val population : Float = cell.agents.length / numberOfAgents
        cell.agents.length match {
            case 0 => Color.lightGray
            case 1 => Color.magenta
            case 2 => Color.blue
            case 3 => Color.green
            case 4 => Color.yellow
            case 5 => Color.red
            Color.black
        } // match
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




