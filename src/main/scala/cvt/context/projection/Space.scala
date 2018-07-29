package cvt.context.projection
import java.awt.{Dimension, Graphics2D}
import cvt.context.projection.uiobject.{AgentUI, Coordinate}
import cvt.Agent


/**
  * @param _dimension the dimension of the projection
  */
class Space(_dimension: Dimension) extends Projection(_dimension) {

    private var tree = Array[AgentUI]()

    override def paintComponent(graphics: Graphics2D): Unit = {
        if (!visible || !paintAgent) return
        for (a <- tree) {
            graphics.setColor(getAgentColor(a))
            graphics.fillOval(a.absoluteLocation.X, a.absoluteLocation.Y, a.dimension.width, a.dimension.height)
        } // for each agent
    } // paintComponent()
    
    
    def getNeighbors(agent : Agent, radius : Integer) : Array[Agent] = {
        val aui = agentMap(agent)
        if (aui == null) return null

        def withinBounds(a : AgentUI) : Boolean = {
            val x = Math.abs(aui.absoluteLocation.X - a.absoluteLocation.X) <= radius
            val y = Math.abs(aui.absoluteLocation.Y - a.absoluteLocation.Y) <= radius
            x && y
        } // withinBounds()

        for (a <- tree if a != aui && withinBounds(a)) yield agentUIMap(a)
    } // getNeighbors()
    

    override def addAgent(agent : Agent, c : Coordinate) : Unit = {
        val aui = new AgentUI(agent)
        val r = scala.util.Random
        aui.absoluteLocation = new Coordinate(r.nextInt(_dimension.width - aui.dimension.width - 20), r.nextInt(_dimension.height - aui.dimension.height - 30))
        agentMap = agentMap + (agent -> aui)
        tree = tree :+ aui
        move(agent, c)
    } // addAgent()


    override def addAgents(agents: Array[Agent]) : Unit = for (a <- agents) move(a, new Coordinate(0, 0))


    override def removeAgent(agent: Agent): Unit = {
        val aui = agentMap(agent)
        if (!tree.contains(aui)) return
        tree = tree.drop(tree.indexOf(aui))
    } // removeAgent()


    override def removeAllAgents(): Unit = {
        tree =  Array[AgentUI]()
        repaint()
    } // removeAllAgents()


    override def move(agent : Agent, direction : Direction.Value, magnitude : Int) : Unit = {
        if (!agentMap.contains(agent)) return
        val aui = agentMap(agent)
        val newCoordinate = aui.absoluteLocation.add(Direction.toCoordinate(direction).multiply(magnitude))
        move(agent, newCoordinate)
    } // move()


    override def move(agent : Agent, c : Coordinate) : Unit = {
        if (!agentMap.contains(agent)) return
        val aui = agentMap(agent)
        aui.absoluteLocation = c
        repaint()
    } // move()

} // Space


