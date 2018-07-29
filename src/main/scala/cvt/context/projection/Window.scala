package cvt.context.projection
import scala.swing.{BorderPanel, Dimension, MainFrame}

/**
  *
  * @param dimension the dimension of the window
  * @param projection the projection to add to the window
  */
class Window(val dimension: Dimension, private val projection: Projection) extends MainFrame {
    visible = true // default value
    resizable = false
    preferredSize = dimension

    // add all contents to the GUI (order matters!)
    contents = new BorderPanel {
        add(projection, BorderPanel.Position.Center)
    } // contents of the GUI
    
    
} // Window
