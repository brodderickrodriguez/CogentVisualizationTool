package cvt.context

import java.awt.Dimension

import cvt.{Agent, MockAgentType}
import cvt.context.projection.{Space, _}
import cvt.context.projection.uiobject.{AgentUI, Coordinate}
import cvt.context.projection.AdjacencyStructure.AdjacencyStructure


class Context(val visualizationVisible : Boolean = true) {
    
    private var projections : Array[Projection] = Array[Projection]()
    var agents : Array[AgentUI] = Array[AgentUI]()
    
    def grid : Grid = { for (p <- projections if p.isInstanceOf[Grid]) return p.asInstanceOf[Grid]; null }
    def network : Network = { for (p <- projections if p.isInstanceOf[Network]) return p.asInstanceOf[Network]; null }
    def space : Space = { for (p <- projections if p.isInstanceOf[Space]) return p.asInstanceOf[Space]; null }
    
    def applyColorScheme(c : ColorScheme) : Unit = for (p <- projections) p.applyColorScheme(c)

    def removeColorScheme(c : ColorScheme) : Unit = for (p <- projections) p.removeColorScheme(c)
    
    def addAgents(agents : Array[Agent]) : Unit = for (a <- agents) addAgent(a)

    def addAgent(agent : Agent) : Unit = addAgent(agent, new Coordinate(0, 0))


    def addAgent(agent : Agent, c : Coordinate) : Unit = {
        val aui = new AgentUI(agent)
        agent.context = this
        agents = agents :+ aui
        for (p <- projections) p.addAgent(aui, c)
    } // addAgent()


    def removeAgent(agent : Agent) : Unit = {
        val aui = agentUIFor(agent)
        if (aui == null) return
        agents = agents.take(agents.indexOf(aui))
        for (p <- projections) p.removeAgent(aui)
    } // removeAgent()


    def removeAllAgents() : Unit = {
        agents = Array[AgentUI]()
        for (p <- projections) p.repaint()
    }



    def getNeighbors(agent : Agent, radius : Integer) : Array[AgentUI] = {
        var results = Array[AgentUI]()
        for (p <- projections) results = results ++ p.getNeighbors(agentUIFor(agent), radius)
        results
    } // getNeighbors()


    def getNeighborsOfTypes(agent : Agent, radius : Integer, types : Array[MockAgentType.Value]): Array[AgentUI] = {
        for (a <- getNeighbors(agent, radius) if types.contains(a.agentType)) yield a
    } // getNeighborsOfTypes()




    def agentUIFor(agent : Agent) : AgentUI = {
        if (agent == null) return null
        for (aui <- agents if aui.agent == agent) return aui
        null
    } // agentUIForAgent()
    
    
    def move(agent : Agent, c : Coordinate) : Unit = {
        val a = agentUIFor(agent)
        if (a == null) return
        for (p <- projections) p.move(a, c)
    } // moveAgent
    
    
    def move(agent : Agent, direction : Direction.Value, magnitude : Int) : Unit = {
        val a = agentUIFor(agent)
        if (a == null) return
        for (p <- projections) p.move(a, direction, magnitude)
    } // move()
    
    
    def createGridContext(dimension: Dimension, cellSize : Int, cellGapSize : Int, circular : Boolean) : Unit = {
     //   if (grid != null) return
        val g = new Grid(dimension, cellSize, cellGapSize, circular, this)
        g.addAgents(agents)
        g.numer = projections.length
        projections = projections :+ g
    } // createGridContext()
    

    def createNetworkContext(dimension: Dimension, dataStructure : AdjacencyStructure) : Unit = {
        if (network != null) return
        val n = new Network(dimension, dataStructure, this)
        n.addAgents(agents)
        projections = projections :+ n
    } // createNetworkContext()


    def createSpaceContext(dimension: Dimension) : Unit = {
        if (network != null) return
        val s = new Space(dimension, this)
        s.addAgents(agents)
        projections = projections :+ s
    } // createNetworkContext()
    
    
    def addConnection(a1 : Agent, a2 : Agent, weight : Double, directed : Boolean) : Unit = {
        val aui1 = agentUIFor(a1)
        val aui2 = agentUIFor(a2)
        if (aui1 == null || aui2 == null) return
        if (network != null) network.addConnection(aui1, aui2, weight, directed)
    } // addConnection()
    
    
    def removeConnection(a1 : Agent, a2 : Agent) : Unit = {
        val aui1 = agentUIFor(a1)
        val aui2 = agentUIFor(a2)
        if (aui1 == null || aui2 == null) return
        if (network != null) network.removeConnection(aui1, aui2)
    } // addConnection()

    
} // Context()
