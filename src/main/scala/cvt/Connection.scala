package cvt

import scala.swing.Dimension


/**
  *
  * @param dimension the dimension of the object
  */
class Connection(var dimension: Dimension) extends UIObject {
    
    var agentA : AgentUI = _
    var agentB : AgentUI = _
    private var startLocation : Coordinate = _
    private var endLocation : Coordinate = _
    var weight : Double = 0
    var directed : Boolean = false
    
    
    override def toString : String = {
        "Connection(from: " + agentA + " to: " + agentB + " weight: " + weight + " description: " + description + ")"
    } // toString()
    
    
} // Connection
