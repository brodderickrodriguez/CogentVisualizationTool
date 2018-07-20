package cvt.projection
import java.awt.Dimension
import cvt.{Agent, ColorScheme}
import cvt.uiobject.{AgentUI, Coordinate}
import cvt.projection.grid.Grid
import cvt.projection.space.Space
import cvt.projection.network.{AdjacencyStructure, Network}

class Context {
    
    private var projections : Array[Projection] = Array[Projection]()
    var agents : Array[AgentUI] = Array[AgentUI]()
    var useVisualPresentation : Boolean = true
    
    def grid : Grid = { for (c <- projections if c.isInstanceOf[Grid]) return c.asInstanceOf[Grid]; null }
    def network : Network = { for (c <- projections if c.isInstanceOf[Network]) return c.asInstanceOf[Network]; null }
    def space : Space = { for (c <- projections if c.isInstanceOf[Space]) return c.asInstanceOf[Space]; null }
    
    def applyColorScheme(c : ColorScheme) : Unit = for (context <- projections) context.applyColorScheme(c)
    def removeColorScheme(c : ColorScheme) : Unit = for (context <- projections) context.removeColorScheme(c)
    
    def addAgents(agents : Array[Agent]) : Unit = for (a <- agents) addAgent(a)
    def addAgent(agent : Agent) : Unit = addAgent(agent, new Coordinate(0, 0))
    def addAgent(agent : Agent, c : Coordinate) : Unit = {
        val aui = new AgentUI(agent)
        agent.contextController = this
        agents = agents :+ aui
        for (context <- projections) context.addAgent(aui, c)
    } // addAgent()
    
    
    
    
    def addProjection() = ???
    
    
    private def agentUIFor(agent : Agent) : AgentUI = {
        if (agent == null) return null
        for (aui <- agents if aui.agent == agent) return aui
        null
    } // agentUIForAgent()
    
    
    def move(agent : Agent, c : Coordinate) : Unit = {
        val a = agentUIFor(agent)
        if (a == null) return
        for (context <- projections) context.move(a, c)
    } // moveAgent
    
    
    def move(agent : Agent, direction : Direction.Value, magnitude : Int) : Unit = {
        val a = agentUIFor(agent)
        if (a == null) return
        for (context <- projections) context.move(a, direction, magnitude)
    } // move()
    
    
        def createGridContext(dimension: Dimension, cellSize : Int, cellGapSize : Int, circular : Boolean) : Unit = {
         //   if (grid != null) return
            val g = new Grid(dimension, cellSize, cellGapSize, circular, this)
            g.addAgents(agents)
            projections = projections :+ g
        } // createGridContext()
        
    
        def createNetworkContext(dimension: Dimension, dataStructure : AdjacencyStructure) : Unit = {
            if (network != null) return
            val n = new Network(dimension, dataStructure, this)
            n.addAgents(agents)
            projections = projections :+ n
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
    
    
    
} // ContextController()
