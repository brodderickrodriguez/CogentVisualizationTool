package cvt.context.projection.uiobject
import cvt.context.projection.Projection
import cvt.{Agent, AgentNotification}
import scala.swing.Dimension


/**
  * AgentUINotifications are used to notify an AgentUI of a change in a given Projection.
  * They are used in each Projection to notify other nearby Agents of events such as
  * a new neighbor, removed a neighbor, etc.
  */
object AgentUINotification extends Enumeration {
    val addedAgentToCell : Value = Value
    val removedAgentFromCell : Value = Value
    val removingAllAgentsFromCell : Value = Value
} // Direction


/** @constructor Creates an AgentUI from an Agent object.
  *              AgentUI's are used to represent an Agent on a specific Projection.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  * @param agent The Agent we are creating an AgentUI for.
  */
class AgentUI(val agent : Agent) extends UIObject {
    /** The Dimension of the AgentUI (default is 10x10). */
    dimension = new Dimension(10, 10)


    /** Creates a String representation of the AgentUI.
      * @return A String in the format of: "AgentUI(<ID> - t:  <AGENT_TYPE>)"
      */
    override def toString : String = "AgentUI(" + ID + " - t:" + agent.agentType + ")"


    /** Notifies an AgentUI of a change on a specific Projection.
      * @param notification - the notification (or change) that has occurred.
      * @param fromProjection - The projection in which the event has occurred.
      */
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
