package cvt

import java.awt.{Color, Dimension}
import scala.swing.Label

abstract class UIObject {
    
    var color : Color = Color.gray
    val size : Dimension = new Dimension(0, 0)
    val ID : Integer = -1
    var description : String = "[UIObject]"
    var absoluteLocation : Coordinate = new Coordinate(0 ,0)
    val label : Label = new Label(toString)
    
    override def toString : String = { "UIObject(id: " + ID + ")" } // toString()
    
} // UIObject
