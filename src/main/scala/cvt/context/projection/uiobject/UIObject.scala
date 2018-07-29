package cvt.context.projection.uiobject
import java.awt.{Color, Dimension}
import cvt.context.projection.Coordinate


/**
  * An Abstract class for all UI Objects. UI Objects are those which appear on screen.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  */
abstract class UIObject {
    var color : Color = _
    var dimension : Dimension = new Dimension(0, 0)
    var ID : Integer = -1
    var description : String = "[UIObject]"
    var absoluteLocation : Coordinate = new Coordinate(0 ,0)

    /**
      * Quickly finds the center of a UIObject's view.
      * @return a Coordinate of the center of the UIObject.
      */
    def center : Coordinate = absoluteLocation.add(new Coordinate(dimension.width / 2, dimension.height / 2))
} // UIObject
