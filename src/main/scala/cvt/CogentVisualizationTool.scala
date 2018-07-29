package cvt
import java.awt.Dimension
import cvt.context.Context
import cvt.context.projection.{ColorSchemes, Coordinate, Grid, _}
import cvt.context.projection.AdjacencyStructure._

object CogentVisualizationTool {
    
    def main(args: Array[String]) : Unit = {
        println("Hello, CVT!")

        //createContextWithMultipleProjections()
        createContextWithNetwork()
        //createContextWithGrid()
        //createContextWithSpace()
    } // main()


    /** Demonstrates how to create multiple Projections. */
    def createContextWithMultipleProjections() : Unit = {
        val context = new Context()
        val graph1 = new Grid(new Dimension(10, 10), 20, 2, true)
        val graph2 = new Grid(new Dimension(20, 20), 20, 2, true)
        val network1 = new Network(new Dimension(500, 500), new AdjacencyList())
        val network2 = new Network(new Dimension(1000, 1500), new AdjacencyMatrix())
        val space1 = new Space(new Dimension(500, 500))
        val space2 = new Space(new Dimension(700, 500))

        context.addProjection(graph1)
        context.addProjection(graph2)
        context.addProjection(network1)
        context.addProjection(network2)
        context.addProjection(space1)
        context.addProjection(space2)

        context.applyColorScheme(ColorSchemes.agentColorRandom)
        graph1.applyColorScheme(ColorSchemes.cellColorByPopulation)
        graph2.removeColorScheme(ColorSchemes.agentColorRandom)
        space2.removeColorScheme(ColorSchemes.agentColorRandom)

        for (_ <- 1 to 500)
            context.addAgent(new Agent())

        for (a1 <- context.agents; a2 <- context.agents if scala.util.Random.nextInt(10000) == 5) {
            network1.addConnection(a1, a2, 1, directed = true)
            space1.move(a1, Direction.right, 30)
            space1.move(a2, Direction.down, 50)
            space2.move(a1, Direction.right, 50)
            space2.move(a2, Direction.down, 30)
        }

    } // createContextWithMultipleProjections()


    /** Demonstrates how to create a 2D Space Projection. */
    def createContextWithSpace() : Unit = {
        val context = new Context()
        val space = new Space(new Dimension(500, 500))
        val space2 = new Space(new Dimension(500, 500))

        context.addProjection(space)
        context.addProjection(space2)
        //context.projectionsVisible(true)
        //space.setVisible(true)

        val a1 = new Agent(AgentType.exciting)
        val a2 = new Agent()
        val a3 = new Agent()

        val ar = Array(a1, a2)

        context.addAgents(ar)
        context.addAgent(a3)

        space.removeAgent(a2)

        space.move(a1, new Coordinate(50, 50))
        space.move(a2, new Coordinate(50, 100))

        val types = Array(AgentType.exciting)

        val neighbors = space.getNeighborsOfTypes(a2, 50, types)

        for (a <- neighbors) println(a)

    } // createContextWithSpace


    /** Demonstrates how to create a Network Projection. */
    def createContextWithNetwork() : Unit = {
        val context = new Context()
        val network1 = new Network(new Dimension(500, 500), new AdjacencyList())

        context.addProjection(network1)
        context.applyColorScheme(ColorSchemes.agentColorRandom)

        val a1 = new Agent()
        val a2 = new Agent()

        context.addAgent(a1)
        context.addAgent(a2)

        network1.addConnection(a1, a2, 1, directed = true)

        for (_ <- 1 to 100)
            context.addAgent(new Agent())

        for (a1 <- context.agents; a2 <- context.agents if scala.util.Random.nextInt(1000) == 5)
            network1.addConnection(a1,a2, 1, directed = true)

    } // createContextWithNetwork()


    /** Demonstrates how to create a Grid Projection. */
    def createContextWithGrid() : Unit = {
        val context = new Context()
        val g1 = new Grid(new Dimension(10, 10), 20, 2, true)
        val g2 = new Grid(new Dimension(20, 20), 20, 2, true)
        context.addProjection(g1)
        context.addProjection(g2)
        //context.projectionsVisible(false)

        context.applyColorScheme(ColorSchemes.agentColorRandom)
        g1.applyColorScheme(ColorSchemes.cellColorByPopulation)

        val a1 = new Agent()
        val a2 = new Agent()

        context.addAgent(a1)
        g2.addAgent(a2)

        g2.move(a1, Direction.down, 1)
        g1.move(a1, Direction.right, 2)

        for (_ <- 1 to 1000000)
            context.addAgent(new Agent())

    } // createContextWithGrid()


} // CogentVisualizationTool




