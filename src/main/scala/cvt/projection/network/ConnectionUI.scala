package cvt.projection.network

import cvt.uiobject.{AgentUI, Coordinate, UIObject}

/**
  *
  */
class ConnectionUI(val a1 : AgentUI, val a2 : AgentUI, val weight : Double) extends UIObject {
    private var startLocation : Coordinate = a1.center
    private var endLocation : Coordinate = a2.center
    override def toString : String = "Connection(from: " + a1 + " to: " + a2 + " weight: " + weight + " description: " + description + ")"
} // Connection

