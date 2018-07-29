package cvt.context
import cvt.Agent
import cvt.context.projection.{ColorScheme, Coordinate, Projection}


/** @constructor Context is a wrapper class containing Agents and Projections.
  *              It allows for Agents to be represented on multiple Projections
  *              Simultaneously.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  *
  *     {{{
  * val context = new Context()
  * val graph1 = new Grid(new Dimension(10, 10), 20, 2, true)
  * val graph2 = new Grid(new Dimension(20, 20), 20, 2, true)
  * val network1 = new Network(new Dimension(500, 500), new AdjacencyList())
  * val network2 = new Network(new Dimension(1000, 1500), new AdjacencyMatrix())
  * val space1 = new Space(new Dimension(500, 500))
  * val space2 = new Space(new Dimension(700, 500))
  *
  * context.addProjection(graph1)
  * context.addProjection(graph2)
  * context.addProjection(network1)
  * context.addProjection(network2)
  * context.addProjection(space1)
  * context.addProjection(space2)
  *
  * context.applyColorScheme(ColorSchemes.agentColorRandom)
  * graph1.applyColorScheme(ColorSchemes.cellColorByPopulation)
  * graph2.removeColorScheme(ColorSchemes.agentColorRandom)
  * space2.removeColorScheme(ColorSchemes.agentColorRandom)
  *
  * for (_ <- 1 to 500)
  *     context.addAgent(new Agent())
  *
  * for (a1 <- context.agents; a2 <- context.agents if scala.util.Random.nextInt(10000) == 5) {
  *     network1.addConnection(a1, a2, 1, directed = true)
  *     space1.move(a1, Direction.right, 30)
  *     space1.move(a2, Direction.down, 50)
  *     space2.move(a1, Direction.right, 50)
  *     space2.move(a2, Direction.down, 30)
  * }
  *     }}}
  */
class Context {

    /** An Array of all the Projections that are part of this Context. */
    private var projections : Array[Projection] = Array[Projection]()

    /** An Array of all the Agents currently in the Context. */
    var agents : Array[Agent] = Array[Agent]()


    /**
      * Set all Projections hidden or visible.
      * @param visible Boolean variable to set the Projections hidden or visible.
      */
    def projectionsVisible(visible : Boolean) : Unit = for (p <- projections) p.setVisible(visible)


    /**
      * Adds a ColorScheme to all Projections.
      * @param c The ColorScheme we wish to add.
      */
    def applyColorScheme(c : ColorScheme) : Unit = for (p <- projections) p.applyColorScheme(c)


    /**
      * Removes a ColorScheme from all Projections.
      * @param c The ColorScheme we wish to remove.
      */
    def removeColorScheme(c : ColorScheme) : Unit = for (p <- projections) p.removeColorScheme(c)


    /**
      * Adds multiple Agents to all Projections at a default Coordinate.
      * Network overrides the default location by placing Agents in
      * random locations on the Projection.
      * @param agents The Array of Agents we are adding to the Context.
      */
    def addAgents(agents : Array[Agent]) : Unit = for (a <- agents) addAgent(a)


    /**
      * Adds an Agent to all Projections at a default Coordinate.
      * Network overrides the default location by placing Agents in
      * random locations on the Projection.
      * @param agent The Agent we are adding to all Projections.
      */
    def addAgent(agent : Agent) : Unit = addAgent(agent, new Coordinate(0, 0))


    /**
      * Adds an Agent to all Projections at a specific Coordinate.
      * 'addAgent(Agent)' is dependent on this function.
      * @param agent The Agent we are adding to all Projections.
      * @param c
      */
    def addAgent(agent : Agent, c : Coordinate) : Unit = {
        agent.context = this
        agents = agents :+ agent
        for (p <- projections) p.addAgent(agent, c)
    } // addAgent()


    /**
      * Removes a single Agent from the Context.
      * @param agent The Agent which we wish to remove.
      */
    def removeAgent(agent : Agent) : Unit = {
        if (!agents.contains(agent)) return
        val idx = agents.indexOf(agent)
        agents = agents.take(idx)
        for (p <- projections) p.removeAgent(agent)
    } // removeAgent()


    /**
      *  Removes all Agents from the Context. I.E. Removes all Agents
      * From all Projections in this Context.
      */
    def removeAllAgents() : Unit = {
        agents = Array[Agent]()
        for (p <- projections) p.removeAllAgents()
    } // removeAllAgents()


    /**
      * Add a Projection to this Context.
      * @param projection The Projection we are adding to the Context.
      */
    def addProjection(projection : Projection) : Unit = {
        projection.context = this
        projections = projections :+ projection
    } // addProjection()
    
} // Context()


