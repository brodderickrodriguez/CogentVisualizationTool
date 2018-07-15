package cvt.uiobject
import cvt.context.grid.Cell
import cvt.context.network.ConnectionUI
import cvt.{Agent, MockAgentNotification, MockAgentType}

import scala.swing.Dimension


object AgentUINotification extends Enumeration {
    val addedAgentToCell : Value = Value
    val removedAgentFromCell : Value = Value
    val removingAllAgentsFromCell : Value = Value
} // Direction


class AgentUI(val agent : Agent) extends UIObject {
    var cell : Cell = _
    dimension = new Dimension(10, 10)
    
    def agentType : MockAgentType.Value = agent.agentType
    
    def center : Coordinate = absoluteLocation.add(new Coordinate(dimension.width / 2, dimension.height / 2))
    
    override def toString : String = "AgentUI(" + ID + " - t:" + agent.agentType + ")"
    
    
    def receiveNotification(notification : AgentUINotification.Value): Unit = {
        //println("[AgentUI] - receiveNotification: " + notification)
        notification match {
            case AgentUINotification.addedAgentToCell =>
              //  println("got addedAgentToCell notification")
                agent.receiveNotification(MockAgentNotification.move)

            case AgentUINotification.removedAgentFromCell =>
               // println("got removedAgentFromCell notification")

            case AgentUINotification.removingAllAgentsFromCell =>
              //  println("got removingAllAgentsFromCell notification")
        } // notification match
    } // receiveNotification()
    
    
} // Agent
