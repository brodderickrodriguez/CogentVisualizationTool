package cvt

import java.awt.{Color, Graphics2D}
import scala.swing.Dimension
import scala.collection.mutable.ArrayBuffer


/**
  *
  */
class AgentUI {
    println("creating AgentUI")
    
    var color : Color = Color.gray
    var size : Dimension = new Dimension(50, 50)
    //val agent : Unit = _
    
    var connections : ArrayBuffer[Connection] = new ArrayBuffer[Connection]()
    
    
    /**
      *
      * @param c the connection we wish to make
      */
    def addConnection(c : Connection): Unit = {
        connections.append(c)
    } // addConnection
    
    
} // Agent
