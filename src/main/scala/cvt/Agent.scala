package cvt

import cvt.context.Context
import cvt.context.projection.uiobject.Coordinate
import cvt.context.projection


object MockAgentNotification extends Enumeration {
    val move : Value = Value
} // MockCogentNotification


object MockAgentType extends  Enumeration {
    val boring : Value = Value
    val exciting : Value = Value
    val daring : Value = Value
} // MockCogentType


class Agent {
    var agentType : MockAgentType.Value = MockAgentType.boring
    var context : Context = _

    def this (mock : MockAgentType.Value) {
        this
        this.agentType = mock
    }


    def randomDirection() : cvt.context.projection.Direction.Value = {
        scala.util.Random.nextInt(4) match  {
            case 0 => projection.Direction.up
            case 1 => projection.Direction.right
            case 2 => projection.Direction.left
            case 3 => projection.Direction.down
        }
    } // randomDirection()
    
    def receiveNotification(notification: MockAgentNotification.Value) : Unit = {
        val r = scala.util.Random
   //     contextController.move(this, new Coordinate(r.nextInt(500), r.nextInt(500)))
        
        if (r.nextInt(10) == 1)
            context.move(this, randomDirection(), 1)
    } // receiveNotification()
    
} // MockAgent
