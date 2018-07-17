package cvt.context.grid
import cvt.uiobject.{AgentUI, AgentUINotification, Coordinate, UIObject}
import scala.collection.mutable.ArrayBuffer
import scala.swing.Dimension

/** @constructor A cell User Interface Object. This is used in the Grid context.
  * @param dimension the dimension of the cell in pixels.
  * @param _coordinate the x,y coordinate of where the Cell is on the Grid.
  */
class Cell(dimension : Dimension, _coordinate: Coordinate) extends UIObject {
    /** A getter for the Cell's coordinates. */
    val coordinate : Coordinate = _coordinate
    /** an ArrayBuffer containing pointers to all the AgentUI's in the cell. */
    val agents : ArrayBuffer[AgentUI] = new ArrayBuffer[AgentUI]()
    
    
    def add(agent : AgentUI) : Unit = {
        // set the agents cell to the coordinate c
        agent.cell = this
        // send a notification to all AgentUIs in the cell that there has been another AgentUI added
        sendNotificationToAgents(AgentUINotification.addedAgentToCell)
        // append to the list of AgentUIs in the cell
        agents += agent
    } // add()
    
    
    def remove(agent : AgentUI) : Unit = {
        // remove from the list of agents
        agents -= agent
        // send a notification to all the AgentUIs in the cell that we are removing an AgentUI
        sendNotificationToAgents(AgentUINotification.removedAgentFromCell)
        // set the AgentUIs cell to null
        agent.cell = null
    } // remove()
    
    
    def removeAllAgents() : Unit = agents.clear()
    
    
    /** Builds and returns a string representation of the Cell.
      * @return A string in the format: "Cell(coordinates:<Coordinate>)"
      */
    override def toString : String = "Cell(coordinates:" + _coordinate + ")"
    
    
    /** Loops through all the AgentUI's in the cells and sends them a notification.
      * @param notification is the notification we wish to send to all the AgentUI's in the Cell.
      */
    def sendNotificationToAgents(notification : AgentUINotification.Value) : Unit = for (a <- agents if a != null) a.receiveNotification(notification)
    
    
} // Cell
