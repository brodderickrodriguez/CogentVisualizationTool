package cvt

import java.awt.{Color, Graphics2D}
import scala.swing.Dimension
import scala.collection.mutable.ArrayBuffer


class AgentUI(var dimension: Dimension) extends UIObject(dimension: Dimension) {
    println("creating AgentUI")
    
    //val agent : Unit = _
    var connections : ArrayBuffer[Connection] = new ArrayBuffer[Connection]()
    

    def addConnection(c : Connection): Unit = {
        connections.append(c)
    } // addConnection
    
    
    override def toString : String = {
        "AgentUI()"
    } // toString
    
    
} // Agent
