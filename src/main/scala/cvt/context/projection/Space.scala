package cvt.context.projection

import java.awt.{Dimension, Graphics2D}

import cvt.context.projection.uiobject.{AgentUI, Coordinate}
import cvt.{Agent, AgentType}


/**
  *
  * @param _dimension the dimension of the projection
  */
class Space(_dimension: Dimension) extends Projection(_dimension) {

    private var tree = Array[AgentUI]()

    override def paintComponent(graphics: Graphics2D): Unit = {
        if (!visible) return
        if (!paintAgent) return

        for (a <- tree) {
            graphics.setColor(getAgentColor(a))
            graphics.fillOval(a.absoluteLocation.X, a.absoluteLocation.Y, a.dimension.width, a.dimension.height)
        } // for each agent

    } // paintComponent()
    
    
    def getNeighbors(agent : Agent, radius : Integer) : Array[Agent] = { null }
    
    
    override def getNeighborsOfTypes(agent : Agent, radius : Integer, types : Array[AgentType.Value]): Array[Agent] = { null }


    override def addAgent(agent : Agent, c : Coordinate) : Unit = move(agent, c)


    override def addAgents(agents: Array[Agent]) : Unit = for (a <- agents) move(a, new Coordinate(0, 0))


    override def move(agent : Agent, direction : Direction.Value, magnitude : Int) : Unit = {
        val aui = agentMap(agent)
        val newCoordinate = aui.absoluteLocation.add(Direction.toCoordinate(direction).multiply(magnitude))
        move(agent, newCoordinate)
    } // move()


    override def move(agent : Agent, c : Coordinate) : Unit = {
        val aui = agentMap(agent)
        aui.absoluteLocation = c
        repaint()
    } // move()


    override def removeAllAgents(): Unit = {
        tree =  Array[AgentUI]()
    }

} // Space


