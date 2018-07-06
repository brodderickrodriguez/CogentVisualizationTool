package cvt

import java.awt.Color

import cvt.context.Context


/**
  *
  */
class ColorScheme(name : String) {
    
    
    def getAgentColor(agentUI: AgentUI) : Color = {
        Color.red
    } // getAgentColor()
    
    def getCellColor(cell: Cell) : Color = {
        Color.black
    } //
    
    def getBackgroundColor(context : Context) : Color = {
        Color.white
    } // getBackgroundColor()
    
    
    
} // ColorScheme



class AvailableColorSchemes {
    
    val colorSchemes : Array[ColorScheme] = new Array[ColorScheme](0)

} // AvailableColorSchemes
