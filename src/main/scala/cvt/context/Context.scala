package cvt.context

import cvt.Agent
import cvt.context.projection.uiobject.Coordinate
import cvt.context.projection.{Projection, ColorScheme}


class Context {
    
    private var projections : Array[Projection] = Array[Projection]()
    var agents : Array[Agent] = Array[Agent]()

    def projectionsVisible(visible : Boolean) : Unit = for (p <- projections) p.setVisible(visible)

    def applyColorScheme(c : ColorScheme) : Unit = for (p <- projections) p.applyColorScheme(c)

    def removeColorScheme(c : ColorScheme) : Unit = for (p <- projections) p.removeColorScheme(c)
    
    def addAgents(agents : Array[Agent]) : Unit = for (a <- agents) addAgent(a)

    def addAgent(agent : Agent) : Unit = addAgent(agent, new Coordinate(0, 0))


    def addAgent(agent : Agent, c : Coordinate) : Unit = {
        agent.context = this
        agents = agents :+ agent
        for (p <- projections) p.addAgent(agent, c)
    } // addAgent()


    def removeAgent(agent : Agent) : Unit = {
        if (!agents.contains(agent)) return
        val idx = agents.indexOf(agent)
        agents = agents.take(idx)
        for (p <- projections) p.removeAgent(agent)
    } // removeAgent()


    def removeAllAgents() : Unit = {
        agents = Array[Agent]()
        for (p <- projections) p.removeAllAgents()
    } // removeAllAgents()


    def addProjection(projection : Projection) : Unit = {
        projection.context = this
        projections = projections :+ projection
    } // addProjection()
    
} // Context()


