package cvt

import cvt.context.ContextController
import cvt.uiobject.Coordinate


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
    var contextController : ContextController = _
    
    
    def randomDirection() : context.Direction.Value = {
        scala.util.Random.nextInt(4) match  {
            case 0 => context.Direction.up
            case 1 => context.Direction.right
            case 2 => context.Direction.left
            case 3 => context.Direction.down
        }
    } // randomDirection()
    
    def receiveNotification(notification: MockAgentNotification.Value) : Unit = {
        val r = scala.util.Random
        contextController.move(this, new Coordinate(r.nextInt(500), r.nextInt(500)))
    }
    
} // MockAgent
