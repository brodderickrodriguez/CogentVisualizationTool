package cvt
import scala.collection.mutable.ArrayBuffer
import java.awt.Color


object AgentUINotification extends Enumeration {
    val addedAgentToCell : Value = Value
    val removedAgentFromCell : Value = Value
    val removingAllAgentsFromCell : Value = Value
} // Direction


class AgentUI(val mockAgent : MockAgent) extends UIObject {
    //println("[AgentUI] initializing")
    
    //val agent : Unit = _
    val connections : ArrayBuffer[Connection] = new ArrayBuffer[Connection]()
    var cell : Cell = _
    color = Color.blue
    
    
    def addConnection(c : Connection): Unit = connections.append(c)
    
    
    override def toString : String = "AgentUI(" + ID + " - t:" + mockAgent.agentType + ")"
    
    
    def receiveNotification(notification : AgentUINotification.Value): Unit = {
        //println("[AgentUI] - receiveNotification: " + notification)
        notification match {
            case AgentUINotification.addedAgentToCell =>
              //  println("got addedAgentToCell notification")
                mockAgent.receiveNotification(MockCogentNotification.move)

            case AgentUINotification.removedAgentFromCell =>
               // println("got removedAgentFromCell notification")

            case AgentUINotification.removingAllAgentsFromCell =>
              //  println("got removingAllAgentsFromCell notification")
        } // notification match
    } // receiveNotification()
    
    
} // Agent
