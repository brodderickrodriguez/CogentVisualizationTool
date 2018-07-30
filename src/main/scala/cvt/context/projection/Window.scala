package cvt.context.projection
import scala.swing.{BorderPanel, Dimension, MainFrame}

/** @constructor Each Projection is contained within a Window.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  * @param _dimension the dimension of the window
  * @param _projection the projection to add to the window
  */
class Window(_dimension: Dimension, _projection: Projection) extends MainFrame {
    /** Default is true. Can be changed using '<Projection>.setVisible(false)'. */
    visible = true // default value
    /** resizable is set to false. This means the window cannot be dragged to be enlarged. */
    resizable = false
    /** Set the preferred size to the parameter dimension. */
    preferredSize = _dimension

    // add all contents to the GUI (order matters!)
    contents = new BorderPanel {
        add(_projection, BorderPanel.Position.Center)
    } // contents of the GUI
    
} // Window
