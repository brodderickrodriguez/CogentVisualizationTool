package cvt

import scala.swing.Dimension


/**
  *
  * @param dimension the dimension of the object
  */
class Connection(var dimension: Dimension) extends  UIObject(dimension : Dimension) {
    
    var agentA : AgentUI = _
    var agentB : AgentUI = _
    private var startLocation : Coordinate = _
    private var endLocation : Coordinate = _
    var weight : Double = _
    var directed : Boolean = _
    
    
    override def toString : String = {
        "Connection(from: " + agentA + " to: " + agentB + " weight: " + weight + " description: " + description + ")"
    } // toString()
    
    
} // Connection

