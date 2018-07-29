package cvt.context
import cvt.Agent
import cvt.context.projection.{ColorScheme, Coordinate, Projection}


/** @constructor Context is a wrapper class containing Agents and Projections.
  *              It allows for Agents to be represented on multiple Projections
  *              Simultaneously.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  */
class Context {

    /** An Array of all the Projections that are part of this Context. */
    private var projections : Array[Projection] = Array[Projection]()

    /** An Array of all the Agents currently in the Context. */
    var agents : Array[Agent] = Array[Agent]()


    /** Set all Projections hidden or visible.
      * @param visible Boolean variable to set the Projections hidden or visible.
      */
    def projectionsVisible(visible : Boolean) : Unit = for (p <- projections) p.setVisible(visible)


    /** Adds a ColorScheme to all Projections.
      * @param c The ColorScheme we wish to add.
      */
    def applyColorScheme(c : ColorScheme) : Unit = for (p <- projections) p.applyColorScheme(c)


    /** Removes a ColorScheme from all Projections.
      * @param c The ColorScheme we wish to remove.
      */
    def removeColorScheme(c : ColorScheme) : Unit = for (p <- projections) p.removeColorScheme(c)


    /** Adds multiple Agents to all Projections at a default Coordinate.
      * Network overrides the default location by placing Agents in
      * random locations on the Projection.
      * @param agents The Array of Agents we are adding to the Context.
      */
    def addAgents(agents : Array[Agent]) : Unit = for (a <- agents) addAgent(a)


    /** Adds an Agent to all Projections at a default Coordinate.
      * Network overrides the default location by placing Agents in
      * random locations on the Projection.
      * @param agent The Agent we are adding to all Projections.
      */
    def addAgent(agent : Agent) : Unit = addAgent(agent, new Coordinate(0, 0))


    /** Adds an Agent to all Projections at a specific Coordinate.
      * 'addAgent(Agent)' is dependent on this function.
      * @param agent The Agent we are adding to all Projections.
      * @param c
      */
    def addAgent(agent : Agent, c : Coordinate) : Unit = {
        agent.context = this
        agents = agents :+ agent
        for (p <- projections) p.addAgent(agent, c)
    } // addAgent()


    /** Removes a single Agent from the Context.
      * @param agent The Agent which we wish to remove.
      */
    def removeAgent(agent : Agent) : Unit = {
        if (!agents.contains(agent)) return
        val idx = agents.indexOf(agent)
        agents = agents.take(idx)
        for (p <- projections) p.removeAgent(agent)
    } // removeAgent()


    /** Removes all Agents from the Context. I.E. Removes all Agents
      * From all Projections in this Context.
      */
    def removeAllAgents() : Unit = {
        agents = Array[Agent]()
        for (p <- projections) p.removeAllAgents()
    } // removeAllAgents()


    /** Add a Projection to this Context.
      * @param projection The Projection we are adding to the Context.
      */
    def addProjection(projection : Projection) : Unit = {
        projection.context = this
        projections = projections :+ projection
    } // addProjection()
    
} // Context()


