package cvt


object MockCogentNotification extends Enumeration {
    val move : Value = Value
} // MockCogentNotification


object MockCogentType extends  Enumeration {
    val boring : Value = Value
    val exciting : Value = Value
    val daring : Value = Value
} // MockCogentType


class MockAgent {
    var agentUI : AgentUI = _
    var agentType : MockCogentType.Value = MockCogentType.boring
    
    def randomDirection() : context.Direction.Value = {
        scala.util.Random.nextInt(4) match  {
            case 0 => context.Direction.up
            case 1 => context.Direction.right
            case 2 => context.Direction.left
            case 3 => context.Direction.down
        }
    } // randomDirection()
    
    def receiveNotification(notification: MockCogentNotification.Value) : Unit = {
        val r = scala.util.Random
        val s = agentUI.cell.grid._dimension
        
        if (agentUI.cell.agents.length > 5) {
            agentUI.cell.grid.move(agentUI, randomDirection(), 1)
          //  agentUI.cell.grid.move(agentUI, new Coordinate(r.nextInt(s.width), r.nextInt(s.height)))
        }
    }
    
} // MockAgent
