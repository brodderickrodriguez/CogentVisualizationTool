package cvt.context.projection
import scala.swing.{BorderPanel, Dimension, MainFrame}

/** @constructor Each Projection is contained within a Window.
  * @param dimension the dimension of the window
  * @param projection the projection to add to the window
  */
class Window(val dimension: Dimension, private val projection: Projection) extends MainFrame {
    /** Default is true. Can be changed using '<Projection>.setVisible(false)'. */
    visible = true // default value
    /** resizable is set to false. This means the window cannot be dragged to be enlarged. */
    resizable = false
    /** Set the preferedSize to the parameter dimension. */
    preferredSize = dimension

    // add all contents to the GUI (order matters!)
    contents = new BorderPanel {
        add(projection, BorderPanel.Position.Center)
    } // contents of the GUI
    
} // Window
