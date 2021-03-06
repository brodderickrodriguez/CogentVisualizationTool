package cvt.context.projection.uiobject
import scala.collection.mutable.ArrayBuffer
import scala.swing.Dimension
import cvt.context.projection.{Coordinate, Grid}


/**
  * @constructor A cell User Interface Object. This is used in the Grid Projection.
  * @param _dimension the dimension of the cell in pixels.
  * @param _coordinate the x,y coordinate of where the Cell is on the Grid.
  * @param _grid the Grid this cell belongs to.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  */
class Cell(_dimension : Dimension, _coordinate: Coordinate, _grid : Grid) extends UIObject {
    /** A getter for the Cell's coordinates. */
    val coordinate : Coordinate = _coordinate
    /** an ArrayBuffer containing pointers to all the AgentUI's in the cell. */
    val agents : ArrayBuffer[AgentUI] = new ArrayBuffer[AgentUI]()


    /**
      * Adds an AgentUI to this Cell.
      * @param agent The Agent we wish to add to the Cell.
      */
    def add(agent : AgentUI) : Unit = {
        // send a notification to all AgentUIs in the cell that there has been another AgentUI added
        sendNotificationToAgents(AgentUINotification.addedAgentToCell)
        // append to the list of AgentUIs in the cell
        agents += agent
    } // add()


    /**
      * Removes an Agent from this Cell.
      * @param agent The Agent we wish to remove.
      */
    def remove(agent : AgentUI) : Unit = {
        // remove from the list of agents
        agents -= agent
        // send a notification to all the AgentUIs in the cell that we are removing an AgentUI
        sendNotificationToAgents(AgentUINotification.removedAgentFromCell)
        // set the AgentUIs cell to null
    } // remove()


    /** Removes all the Agents from the Cell. */
    def removeAllAgents() : Unit = agents.clear()
    
    
    /**
      * Builds and returns a string representation of the Cell.
      * @return A string in the format: "Cell(coordinates:<Coordinate>)"
      */
    override def toString : String = "Cell(coordinates:" + _coordinate + ")"
    
    
    /**
      * Loops through all the AgentUI's in the cells and sends them a notification.
      * @param notification is the notification we wish to send to all the AgentUI's in the Cell.
      */
    def sendNotificationToAgents(notification : AgentUINotification.Value) : Unit = {
        for (a <- agents if a != null)
            a.receiveNotification(notification, _grid)
    } // sendNotificationToAgents()
    
    
} // Cell
