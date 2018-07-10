package cvt
import cvt.context._
import java.awt.Dimension
import scala.collection.mutable.ArrayBuffer


object CogentVisualizationTool {
    
    def main(args: Array[String]) : Unit = {
        println("Hello, CVT!")
        
        // SIZE
        // A
        val widthCells = 5
        val heightCells = 5
        val cellSize = 100
        val cellGapSize = 10
        
        
        // B
        /*
        val widthCells = 25
        val heightCells = 20
        val cellSize = 25
        val cellGapSize = 2
        */
        
        val dimension = new Dimension(widthCells, heightCells)
        val grid : Grid = new Grid(dimension, cellSize, cellGapSize,true)
        
        
        // ADDING
        // A
        /*
        val mock = new MockAgent()
        val AUI = new AgentUI(mock)
        mock.agentUI = AUI
        grid.addAgent(AUI)
        */
        
        // B
        /*
        val AUI2 = new AgentUI(mock)
        mock.agentUI = AUI2
        grid.addAgent(AUI2, new Coordinate(2, 2))
        */
        
        
        // COLOR SCHEME
        //grid.applyColorScheme(ColorSchemes.cellColorByPopulation)
        //grid.applyColorScheme(ColorSchemes.agentColorRandom)
        
        
        // MOVE
        // A
        //grid.move(AUI, Direction.right, 3)
        
        // B
        //grid.move(AUI, new Coordinate(5,5))
        //grid.move(AUI2, new Coordinate(0,10))
        
        
        
        // NEIGHBORS
        /*
        val types = Array(MockCogentType.daring, MockCogentType.exciting, MockCogentType.boring)
        val r = scala.util.Random
        for (i <- 1 to 10) {
            val ma = new MockAgent()
            val a = new AgentUI(ma)
            a.ID = i
            ma.agentUI = a
            ma.agentType = types(Math.abs(r.nextInt()) % types.length)
            grid.addAgent(a, new Coordinate(i + 1, 10))
        }
        
        println(grid.getNeighbors(AUI2, 2))
        println(grid.getNeighborsOfTypes(AUI2, 2, Array(MockCogentType.daring)))
        */
        
        
        
        // SPAWN
        /*
        for (_ <- 1 to 1000) {
            val ma = new MockAgent()
            val a = new AgentUI(ma)
            ma.agentType = MockCogentType.daring
            ma.agentUI = a
            grid.addAgent(a, new Coordinate(widthCells / 2, heightCells / 2))
        }
        */
        
        
        
    } // main()
    
} // CogentVisualizationTool
