package cvt

import cvt.context.Context
import cvt.context.projection
import cvt.context.projection.Projection


object AgentNotification extends Enumeration {
    val move : Value = Value
} // MockCogentNotification


object AgentType extends  Enumeration {
    val boring : Value = Value
    val exciting : Value = Value
    val daring : Value = Value
} // MockCogentType


class Agent {
    var agentType : AgentType.Value = AgentType.boring
    var context : Context = _

    def this (mock : AgentType.Value) {
        this
        this.agentType = mock
    }


    override def toString : String = "Agent()"

    def randomDirection : cvt.context.projection.Direction.Value = {
        scala.util.Random.nextInt(4) match  {
            case 0 => projection.Direction.up
            case 1 => projection.Direction.right
            case 2 => projection.Direction.left
            case 3 => projection.Direction.down
        }
    } // randomDirection()
    
    def receiveNotification(notification: AgentNotification.Value, fromProjection : Projection) : Unit = {
        // randomly move the agentUI on the projection 'fromProjection'. Used for testing.
        if (scala.util.Random.nextInt(10) == 1)
            fromProjection.move(this, randomDirection, 1)
    } // receiveNotification()
    
} // MockAgent
