package cvt.context.grid

import cvt.uiobject.{AgentUI, AgentUINotification, Coordinate, UIObject}

import scala.collection.mutable.ArrayBuffer
import scala.swing.Dimension

class Cell(_grid : Grid, _dimension : Dimension, _coordinate: Coordinate) extends UIObject {
    
    val coordinate : Coordinate = _coordinate
    val agents : ArrayBuffer[AgentUI] = new ArrayBuffer[AgentUI]()
    val grid : Grid = _grid
    
    override def toString : String = ""
    
    def sendNotificationToAgents(notification : AgentUINotification.Value) : Unit = for (a <- agents if a != null) a.receiveNotification(notification)
    
} // Cell
