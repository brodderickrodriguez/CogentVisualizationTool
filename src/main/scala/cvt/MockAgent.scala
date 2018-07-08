package cvt

object MockAgentNotification extends Enumeration {
    val move : Value = Value
}

object MockAgentType extends  Enumeration {
    val boring : Value = Value
    val exciting : Value = Value
    val daring : Value = Value
}


class MockAgent {
    var agentUI : AgentUI = _
    var agentType : MockAgentType.Value = MockAgentType.boring
    
    
    def randomDirection() : context.Direction.Value = {
        scala.util.Random.nextInt(4) match  {
            case 1 => context.Direction.up
            case 2 => context.Direction.right
            case 3 => context.Direction.left
            case 4 => context.Direction.down
        }
    }
    
    
    def receiveNotification(notification: MockAgentNotification.Value) : Unit = {
    
        if (agentUI.cell.agents.length > 5) {
            val r = scala.util.Random
            val c = new Coordinate(r.nextInt(25), r.nextInt(25))
    
           // agentUI.cell.grid.move(agentUI, context.Direction.up, 1)
            agentUI.cell.grid.move(agentUI, c)
        }
        
        
    }
    
    
}
