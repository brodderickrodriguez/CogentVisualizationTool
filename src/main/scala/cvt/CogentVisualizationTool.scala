package cvt
import java.awt.Dimension

import cvt.context.grid.Grid
import cvt.uiobject.{AgentUI, Coordinate}


object CogentVisualizationTool {
    
    
    def createAGridContext() : Unit = {
        val widthCells = 50
        val heightCells = 50
        val cellSize = 15
        val cellGapSize = 2
    
        val dimension = new Dimension(widthCells, heightCells)
        val grid : Grid = new Grid(dimension, cellSize, cellGapSize,true)
    
        grid.applyColorScheme(ColorSchemes.agentColorRandom)
        grid.applyColorScheme(ColorSchemes.cellColorByPopulation)
        //grid.applyColorScheme(ColorSchemes.cellColorByAgentType)
        grid.applyColorScheme(ColorSchemes.doNotPaintAgent)
    
    
        // SPAWN
        val types = Array(MockCogentType.daring, MockCogentType.exciting, MockCogentType.boring)
        val r = scala.util.Random
    
        for (_ <- 1 to 10000) {
            val ma = new MockAgent()
            val a = new AgentUI(ma)
            ma.agentType = types(Math.abs(r.nextInt()) % types.length)
            ma.agentUI = a
            //grid.addAgent(a)
            grid.addAgent(a, new Coordinate(widthCells / 2, heightCells / 2))
        
            // println(grid.getNeighbors(a, 1))
            // println(grid.getNeighborsOfTypes(a, 1, Array(MockCogentType.exciting)))
        } // for
    } // createAGridContext()
    
    
    def main(args: Array[String]) : Unit = {
        println("Hello, CVT!")
        createAGridContext()
    } // main()
    
} // CogentVisualizationTool

/*

  
        // ADDING
        // A
        
        val mock = new MockAgent()
        val AUI = new AgentUI(mock)
        mock.agentUI = AUI
        grid.addAgent(AUI)
        
        
        //
        val AUI2 = new AgentUI(mock)
        mock.agentUI = AUI2
        grid.addAgent(AUI2, new Coordinate(2, 2))
        
        
        
        // COLOR SCHEME
        grid.applyColorScheme(ColorSchemes.cellColorByAgentType)
        grid.applyColorScheme(ColorSchemes.agentColorRandom)
        
        
        // MOVE
        // A
        grid.move(AUI, Direction.right, 3)
        
        // B
        grid.move(AUI, new Coordinate(5,5))
        grid.move(AUI2, new Coordinate(0,10))
        
        
        
        // NEIGHBORS
        
        val types = Array(MockCogentType.daring, MockCogentType.exciting, MockCogentType.boring)
        val r = scala.util.Random
        for (i <- 1 to 10) {
            val ma = new MockAgent()
            val a = new AgentUI(ma)
            a.ID = i
            ma.agentUI = a
            ma.cogentType = types(Math.abs(r.nextInt()) % types.length)
            grid.addAgent(a, new Coordinate(i + 1, 10))
        }
        
        println(grid.getNeighbors(AUI2, 2))
        println(grid.getNeighborsOfTypes(AUI2, 2, Array(MockCogentType.daring)))
        
        
        
 */
