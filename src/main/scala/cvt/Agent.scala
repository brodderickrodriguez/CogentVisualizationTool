package cvt

import cvt.projection.Context
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
    var agentType : MockAgentType.Value = MockAgentType.daring
    var contextController : Context = _
    
    def randomDirection() : projection.Direction.Value = {
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
            contextController.move(this, randomDirection(), 1)
    } // receiveNotification()
    
} // MockAgent
