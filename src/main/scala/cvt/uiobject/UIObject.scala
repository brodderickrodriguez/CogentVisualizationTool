package cvt.uiobject

import java.awt.{Color, Dimension}

import scala.swing.Label

abstract class UIObject {
    var color : Color = _
    var dimension : Dimension = new Dimension(0, 0)
    var ID : Integer = -1
    var description : String = "[UIObject]"
    var absoluteLocation : Coordinate = new Coordinate(0 ,0)
    val label : Label = new Label(toString)
} // UIObject
