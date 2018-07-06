package cvt

import cvt.context._
import scala.swing.Dimension



object CogentVisualizationTool {
    
    def main(args: Array[String]) : Unit = {
        println("Hello, CVT!")
    
        
        val widthCells = 50
        val heightCells = 30
        val cellSize = 10
        
        val dimension = new Dimension(widthCells, heightCells)
        val context : Context = new Grid(dimension, cellSize)
        
        
        
    } // main()
    
} // CogentVisualizationTool
