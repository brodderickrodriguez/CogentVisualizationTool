package cvt.context.grid
import cvt.uiobject.{AgentUI, AgentUINotification, Coordinate, UIObject}
import scala.collection.mutable.ArrayBuffer
import scala.swing.Dimension

/** @constructor A cell User Interface Object. This is used in the Grid context.
  * @param _grid is the container for Cell. Grid contains many Cells.
  * @param _dimension the dimension of the cell in pixels.
  * @param _coordinate the x,y coordinate of where the Cell is on the Grid.
  */
class Cell(_grid : Grid, _dimension : Dimension, _coordinate: Coordinate) extends UIObject {
    
    println("c1")
    
    /** A getter for the containing Grid. */
    val grid : Grid = _grid
    /** A getter for the Cell's coordinates. */
    val coordinate : Coordinate = _coordinate
    /** an ArrayBuffer containing pointers to all the AgentUI's in the cell. */
    val agents : ArrayBuffer[AgentUI] = new ArrayBuffer[AgentUI]()
    
    println("c2")
    
    
    /** Builds and returns a string representation of the Cell.
      * @return A string in the format: "Cell(coordinates:<Coordinate>)"
      */
    override def toString : String = "Cell(coordinates:" + _coordinate + ")"
    
    
    /** Loops through all the AgentUI's in the cells and sends them a notification.
      * @param notification is the notification we wish to send to all the AgentUI's in the Cell.
      */
    def sendNotificationToAgents(notification : AgentUINotification.Value) : Unit = for (a <- agents if a != null) a.receiveNotification(notification)
    
    
} // Cell
