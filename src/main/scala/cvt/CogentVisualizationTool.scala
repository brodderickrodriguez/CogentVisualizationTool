package cvt
import java.awt.Dimension
import cvt.context.grid.Grid
import cvt.uiobject.{AgentUI, Coordinate}
import cvt.context.network._

import cvt.context.ContextController


object CogentVisualizationTool {
    
    def main(args: Array[String]) : Unit = {
        println("Hello, CVT!")
        
        createANetworkContext()
    //   createAGridContext()
        //createTestAdjacencyList()
        //createTestAdjacencyMatrix()
        
    } // main()
    
    
    def createANetworkContext() : Unit = {
    
        val controller = new ContextController()
        controller.createNetworkContext(new Dimension(1000,800), AdjacencyStructures.list)
        
        controller.createGridContext(new Dimension(30,30), 20, 2, true)
       // controller.createGridContext(new Dimension(30,30), 20, 2, false)
    
    
        controller.applyColorScheme(ColorSchemes.agentColorRandom)
        controller.applyColorScheme(ColorSchemes.cellColorByPopulation)
        controller.applyColorScheme(ColorSchemes.doNotPaintAgent)
        controller.network.removeColorScheme(ColorSchemes.doNotPaintAgent)
        controller.network.applyColorScheme(ColorSchemes.agentColorRandom)

        val r = scala.util.Random
        for (i <- 1 to 1000) {
            controller.addAgent(new Agent())
        }
        
        
        for (a1 <- controller.agents; a2 <- controller.agents if r.nextInt(10000) == 5)
            controller.addConnection(a1.agent,a2.agent, 1, true)
        
        val a1 = new Agent()
        val a2 = new Agent()
        controller.addAgent(a1)
        controller.addAgent(a2)
        controller.addConnection(a1, a2, 60, true)
        
    } // createANetworkContext
    
    
    def createAGridContext() : Unit = {
        val widthCells = 50
        val heightCells = 50
        val cellSize = 15
        val cellGapSize = 2
    
        val dimension = new Dimension(widthCells, heightCells)
        val grid : Grid = new Grid(dimension, cellSize, cellGapSize,true, new ContextController())
        
        grid.applyColorScheme(ColorSchemes.agentColorRandom)
        grid.applyColorScheme(ColorSchemes.cellColorByPopulation)
        grid.applyColorScheme(ColorSchemes.cellColorByAgentType)
        grid.applyColorScheme(ColorSchemes.doNotPaintAgent)
        
        val types = Array(MockAgentType.daring, MockAgentType.exciting, MockAgentType.boring)
        val r = scala.util.Random
    
        for (_ <- 1 to 10000) {
            val ma = new Agent()
            val a = new AgentUI(ma)
            ma.agentType = types(Math.abs(r.nextInt()) % types.length)
          //  ma.agentUI = a
            grid.addAgent(a)
         //   grid.addAgent(a, new Coordinate(widthCells / 2, heightCells / 2))
            // println(grid.getNeighbors(a, 1))
            // println(grid.getNeighborsOfTypes(a, 1, Array(MockCogentType.exciting)))
        } // for
    } // createAGridContext()
    
    
    def createTestAdjacencyMatrix() : Unit = {
        val aui1 = new AgentUI(new Agent())
        aui1.ID = 1
        val aui2 = new AgentUI(new Agent())
        aui2.ID = 2
        val aui3 = new AgentUI(new Agent())
        aui3.ID = 3
        val aui4 = new AgentUI(new Agent())
        aui4.ID = 4
        
        val am = new AdjacencyMatrix()
        am.add(aui1)
        am.add(aui2)
        am.add(aui3)
        am.add(aui4)
        am.addConnection(aui1, aui2, 1.1, false)
        am.addConnection(aui4, aui3, 69, false)
        am.removeConnection(aui1, aui2)
        am.remove(aui1)
        println(am)
        
        for ((a1, a2, weight) <- am.connections) println(a1, a2, weight)
        
    } // createTestAdjacencyMatrix()
    
    
    def createTestAdjacencyList() : Unit = {
        
        val aui1 = new AgentUI(new Agent())
        aui1.ID = 1
        val aui2 = new AgentUI(new Agent())
        aui2.ID = 2
        val aui3 = new AgentUI(new Agent())
        aui3.ID = 3
        val aui4 = new AgentUI(new Agent())
        aui4.ID = 4
        
        val al = new AdjacencyList()
        al.add(aui1)
        al.add(aui2)
        al.add(aui3)
        al.add(aui4)
        
        al.addConnection(aui1, aui2, 43.2, false)
        al.addConnection(aui4, aui3, 69, false)
        
        al.removeConnection(aui1, aui2)
        al.addConnection(aui1, aui2, 1, false)
        al.remove(aui2)
        println("\n\n" + al)
        
        for ((a1, a2, weight) <- al.connections) println(a1, a2, weight)
        
    } // createTestAdjacencyList()

} // CogentVisualizationTool




