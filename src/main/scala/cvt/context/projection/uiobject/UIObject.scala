package cvt.context.projection.uiobject
import java.awt.{Color, Dimension}

import cvt.context.projection.Coordinate

abstract class UIObject {
    var color : Color = _
    var dimension : Dimension = new Dimension(0, 0)
    var ID : Integer = -1
    var description : String = "[UIObject]"
    var absoluteLocation : Coordinate = new Coordinate(0 ,0)
    def center : Coordinate = absoluteLocation.add(new Coordinate(dimension.width / 2, dimension.height / 2))
} // UIObject
