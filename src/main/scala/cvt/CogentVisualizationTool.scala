package cvt
import java.awt.Dimension

import cvt.context.Context
import cvt.context.projection.{AdjacencyStructures, ColorSchemes, Grid}
import cvt.context.projection.AdjacencyStructure.{AdjacencyList, AdjacencyMatrix}
import cvt.context.projection.uiobject.{AgentUI, Coordinate}

import cvt.context.projection._


object CogentVisualizationTool {
    
    def main(args: Array[String]) : Unit = {
        println("Hello, CVT!")

     //   createContextWithNetwork()
     //   createContextWithGrid()
        createContextWithSpace()

      //  createTestAdjacencyList()
      //  createTestAdjacencyMatrix()
        
    } // main()


    def createContextWithSpace() : Unit = {

        val context = new Context()
        val space = new Space(new Dimension(500, 500))
        context.addProjection(space)

        val a1 = new Agent()
        val a2 = new Agent()

        context.addAgent(a1)
        context.addAgent(a2)

        space.move(a1, new Coordinate(50, 50))
        space.move(a2, new Coordinate(50, 100))

        val neighbors = space.getNeighbors(a1, 50)
        for (a <- neighbors) println(a)

    } // createContextWithSpace


    def createContextWithGrid() : Unit = {
        val context = new Context()

        val g1 = new Grid(new Dimension(10, 10), 20, 2, true)
        val g2 = new Grid(new Dimension(20, 20), 20, 2, true)


        context.addProjection(g1)
        context.addProjection(g2)

      //  context.projectionsVisible(false)
        g1.setVisible(true)

        context.applyColorScheme(ColorSchemes.agentColorRandom)

        g1.applyColorScheme(ColorSchemes.cellColorByPopulation)


        //     g.applyColorScheme(ColorSchemes.cellColorByPopulation)
    //    g.applyColorScheme(ColorSchemes.doNotPaintAgent)
        //  context.applyColorScheme(ColorSchemes.agentColorRandom)




        val a1 = new Agent()
        val a2 = new Agent()

        context.addAgent(a1)
        context.addAgent(a2)

        g2.move(a1, Direction.down, 1)
        g1.move(a1, Direction.right, 2)



        for (i <- 1 to 1000000) {
            context.addAgent(new Agent())
        }

    } // createContextWithGrid()




    def createContextWithNetwork() : Unit = {
        val context = new Context()
        val network = new Network(new Dimension(500, 500), AdjacencyStructures.matrix)
        context.addProjection(network)
        context.applyColorScheme(ColorSchemes.agentColorRandom)

        val a1 = new Agent()
        val a2 = new Agent()

        context.addAgent(a1)
        context.addAgent(a2)

        network.addConnection(a1, a2, 1, true)

        for (_ <- 1 to 100)
            context.addAgent(new Agent())

        for (a1 <- context.agents; a2 <- context.agents if scala.util.Random.nextInt(1000) == 5)
            network.addConnection(a1,a2, 1, true)

    } // createContextWithNetwork()






    /*
    
    def createContext() : Unit = {
    
        val controller = new Context(true)
        controller.createNetworkContext(new Dimension(1000,800), AdjacencyStructures.list)

      //  controller.createGridContext(new Dimension(10, 10), 20, 2, true)
      //  controller.createGridContext(new Dimension(10, 10), 20, 2, true)


    //    controller.grid.applyColorScheme(ColorSchemes.doNotPaintAgent)
        controller.applyColorScheme(ColorSchemes.cellColorByPopulation)
        // controller.applyColorScheme(ColorSchemes.agentColorRandom)
       // controller.applyColorScheme(ColorSchemes.doNotPaintAgent)
       // controller.network.removeColorScheme(ColorSchemes.doNotPaintAgent)
       // controller.network.applyColorScheme(ColorSchemes.agentColorRandom)

       // val r = scala.util.Random
        for (i <- 1 to 100) {
       //     controller.addAgent(new Agent())
        }

       // for (a1 <- controller.agents; a2 <- controller.agents if r.nextInt(10000) == 5)
       //     controller.addConnection(a1.agent,a2.agent, 1, true)
        
        val a1 = new Agent()
        val a2 = new Agent()
        val a3 = new Agent()
        val a4 = new Agent(MockAgentType.daring)

        controller.addAgent(a1)
        controller.addAgent(a2)
        controller.addAgent(a3)
        controller.addAgent(a4)

     //   controller.agentUIFor(a1).ID = 1
     //   controller.agentUIFor(a2).ID = 2
     //   controller.agentUIFor(a3).ID = 3
     //   controller.agentUIFor(a4).ID = 4


     //   controller.addConnection(a2, a1, 60, false)
     //   controller.addConnection(a2, a3, 10, false)
     //   controller.addConnection(a2, a4, 10, false)
     //   controller.addConnection(a3, a4, 10, false)


      //  val neighbors = controller.getNeighbors(a1, 2)
      //  val neighbors = controller.getNeighborsOfTypes(a2, 0, Array(MockAgentType.daring))
     //   println("got results")
     //   for (a <- neighbors) println(a.toString())

    } // createANetworkContext
    

    
    def createTestAdjacencyMatrix() : Unit = {
        val aui1 = new AgentUI(new Agent())
        val aui2 = new AgentUI(new Agent())
        val aui3 = new AgentUI(new Agent())
        val aui4 = new AgentUI(new Agent())

        aui1.ID = 1
        aui2.ID = 2
        aui3.ID = 3
        aui4.ID = 4
        
        val am = new AdjacencyMatrix()
        am.add(aui1)
        am.add(aui2)
        am.add(aui3)
        am.add(aui4)
        am.addConnection(aui1, aui2, 1.1, false)
        am.addConnection(aui4, aui3, 1.2, false)
        am.removeConnection(aui1, aui2)
     //   am.remove(aui1)
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
    //    al.addConnection(aui1, aui2, 1, false)
        al.remove(aui2)
        println("\n\n" + al)
        
        for ((a1, a2, weight) <- al.connections) println(a1, a2, weight)
        
    } // createTestAdjacencyList()
*/
} // CogentVisualizationTool




