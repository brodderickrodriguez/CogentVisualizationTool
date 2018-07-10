package cvt

import scala.collection.mutable.ArrayBuffer
import scala.swing.Dimension

class Cell(val grid : context.Grid, dimension : Dimension) extends UIObject {
    
    var cellCoordinate : Coordinate = _
    var agents : ArrayBuffer[AgentUI] = new ArrayBuffer[AgentUI]()
    
    override def toString : String = ""
    
    def sendNotificationToAgents(notification : AgentUINotification.Value) : Unit = for (a <- agents) a.receiveNotification(notification)
    
} // Cell
