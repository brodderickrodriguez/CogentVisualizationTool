package cvt.context

import java.awt.Dimension

import cvt.{Agent, ColorScheme}
import cvt.context.grid.Grid
import cvt.context.space.Space
import cvt.context.network.{AdjacencyStructure, Network}
import cvt.uiobject.{AgentUI, Coordinate}

class ContextController {
    
    private var contexts : Array[Context] = Array[Context]()
    var agents : Array[AgentUI] = Array[AgentUI]()
    
    def grid : Grid = { for (c <- contexts if c.isInstanceOf[Grid]) return c.asInstanceOf[Grid]; null }
    def network : Network = { for (c <- contexts if c.isInstanceOf[Network]) return c.asInstanceOf[Network]; null }
    def space : Space = { for (c <- contexts if c.isInstanceOf[Space]) return c.asInstanceOf[Space]; null }
    
    def applyColorScheme(c : ColorScheme) : Unit = for (context <- contexts) context.applyColorScheme(c)
    def removeColorScheme(c : ColorScheme) : Unit = for (context <- contexts) context.removeColorScheme(c)
    
    
    def repaintContexts() : Unit = for (c <- contexts) c.repaint()
    
    
    def addAgents(agents : Array[Agent]) : Unit = for (a <- agents) addAgent(a)
    def addAgent(agent : Agent) : Unit = addAgent(agent, new Coordinate(0, 0))
    def addAgent(agent : Agent, c : Coordinate) : Unit = {
        val aui = new AgentUI(agent)
        agent.contextController = this
        agents = agents :+ aui
        for (context <- contexts) context.addAgent(aui, c)
    } // addAgent()
    
    
    
    def agentUIFor(agent : Agent) : AgentUI = {
        if (agent == null) return null
        for (aui <- agents if aui.agent == agent) return aui
        null
    } // agentUIForAgent()
    
    
    def move(agent : Agent, c : Coordinate) : Unit = {
        println("asked to move")
        val a = agentUIFor(agent)
        if (a == null) return
        for (context <- contexts) context.move(a, c)
    } // moveAgent
    
    
    def move(agent : Agent, direction : Direction.Value, magnitude : Int) : Unit = {
        val a = agentUIFor(agent)
        if (a == null) return
        for (context <- contexts) context.move(a, direction, magnitude)
    } // move()
    
    
    def createGridContext(dimension: Dimension, cellSize : Int, cellGapSize : Int, circular : Boolean) : Unit = {
        if (grid != null) return
        val g = new Grid(dimension, cellSize, cellGapSize, circular, this)
        g.addAgents(agents)
        contexts = contexts :+ g
    } // createGridContext()
    

    def createNetworkContext(dimension: Dimension, dataStructure : AdjacencyStructure) : Unit = {
        if (network != null) return
        val n = new Network(dimension, dataStructure, this)
        n.addAgents(agents)
        contexts = contexts :+ n
    } // createNetworkContext()
    
    
    def addConnection(a1 : Agent, a2 : Agent, weight : Double, directed : Boolean) : Unit = {
        val aui1 = agentUIFor(a1)
        val aui2 = agentUIFor(a2)
        if (aui1 == null || aui2 == null) return
        if (network != null) network.addConnection(aui1, aui2, weight, directed)
    } // addConnection()
    
    
    
} // ContextController()
