package cvt.context.projection.uiobject

import cvt.context.projection.Projection
import cvt.{Agent, AgentNotification}

import scala.swing.Dimension


object AgentUINotification extends Enumeration {
    val addedAgentToCell : Value = Value
    val removedAgentFromCell : Value = Value
    val removingAllAgentsFromCell : Value = Value
} // Direction


class AgentUI(val agent : Agent) extends UIObject {
    dimension = new Dimension(10, 10)

    
    override def toString : String = "AgentUI(" + ID + " - t:" + agent.agentType + ")"
    
    
    def receiveNotification(notification : AgentUINotification.Value, fromProjection : Projection): Unit = {
        notification match {
            case AgentUINotification.addedAgentToCell =>
              //  println("got addedAgentToCell notification")
                agent.receiveNotification(AgentNotification.move, fromProjection)

            case AgentUINotification.removedAgentFromCell =>
               // println("got removedAgentFromCell notification")

            case AgentUINotification.removingAllAgentsFromCell =>
              //  println("got removingAllAgentsFromCell notification")
        } // notification match
    } // receiveNotification()
    
    
} // Agent
