package cvt

import cvt.context._
import java.awt.{Color, Dimension}


object CogentVisualizationTool {
    
    def main(args: Array[String]) : Unit = {
        println("Hello, CVT!")
        
        val widthCells = 25
        val heightCells = 20
        val cellSize = 25
        val cellGapSize = 2
        
        val dimension = new Dimension(widthCells, heightCells)
        val grid : Grid = new Grid(dimension, cellSize, cellGapSize,true)
        
        
        
        grid.applyColorScheme(ColorSchemes.cellColorByPopulation)
        
        
        for (_ <- 1 to 250) {
            val ma = new MockAgent()
            ma.agentType = MockAgentType.daring
            val a = new AgentUI(ma)
            ma.agentUI = a
            val r = scala.util.Random
            
            a.color = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat())
            grid.addAgent(a)
        }
        
        
        
    } // main()
    
} // CogentVisualizationTool
