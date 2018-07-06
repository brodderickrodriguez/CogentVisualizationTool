package cvt

import java.awt.Color

import scala.swing.{Dimension, Label}

abstract class UIObject(dimension: Dimension) {
    
    var color : Color = Color.gray
    val size : Dimension = new Dimension(0, 0)
    val ID : Integer = -1
    var description : String = _
    protected var absoluteLocation : Coordinate = _
    val label : Label = new Label(toString)
    
    override def toString : String = {
        "UIObject(id: " + ID + ")"
    } // toString()
    
} // UIObject
