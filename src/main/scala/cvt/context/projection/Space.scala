package cvt.context.projection

import java.awt.{Dimension, Graphics2D}

import cvt.MockAgentType
import cvt.context.Context
import cvt.context.projection.uiobject.{AgentUI, Coordinate}


/**
  *
  * @param _dimension the dimension of the projection
  */
class Space(_dimension: Dimension, context : Context) extends Projection(_dimension, context) {

    override def paintComponent(graphics: Graphics2D): Unit = {
        if (!context.visualizationVisible) return
        if (!paintAgent) return

        for (a <- context.agents) {
            graphics.setColor(getAgentColor(a))
            graphics.fillOval(a.absoluteLocation.X, a.absoluteLocation.Y, a.dimension.width, a.dimension.height)
        } // for each agent

    } // paintComponent()
    
    
    def getNeighbors(agent : AgentUI, radius : Integer) : Array[AgentUI] = {
        val start = agent.absoluteLocation.subtract(radius)
        val end = agent.absoluteLocation.add(radius)
        val result = Array[AgentUI]()

        for (x <- start.X to end.X; y <- start.Y to end.Y if !agent.absoluteLocation.equals(new Coordinate(x, y)) ) {
        }

        null
    } // getNeighbors()
    
    
    override def getNeighborsOfTypes(agent : AgentUI, radius : Integer, types : Array[MockAgentType.Value]): Array[AgentUI] = {
        null
    } // getNeighborsOfTypes()


    override def addAgent(agent : AgentUI, c : Coordinate) : Unit = move(agent, c)


    override def move(agent : AgentUI, direction : Direction.Value, magnitude : Int) : Unit = {
        var c = new Coordinate(agent.absoluteLocation)
        // map the direction parameter to an actual cell
        // this is done by adding/subtracting magnitude
        direction match {
            case Direction.up => c = c.subY(magnitude)
            case Direction.right => c = c.addX(magnitude)
            case Direction.down => c = c.addY(magnitude)
            case Direction.left => c = c.subX(magnitude)
        } // match direction
        // move the AgentUI to the new cell
        move(agent, c)
    } // move()


    override def move(agent : AgentUI, c : Coordinate) : Unit = {
        agent.absoluteLocation = c
        repaint()
    } // move()


} // Space


