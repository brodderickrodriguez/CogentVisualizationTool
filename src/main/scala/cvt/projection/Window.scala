package cvt.projection

import scala.swing.{BorderPanel, Dimension, MainFrame, ScrollPane, Swing}

/**
  *
  * @param dimension the dimension of the window
  * @param projection the context to add to the window
  */
class Window(val dimension: Dimension, private val projection: Projection) extends MainFrame {
    //println("[Window] initializing")
    
    visible = projection.context.useVisualPresentation
    resizable = false
    preferredSize = dimension
    
    
    // add all contents to the GUI (order matters!)
    contents = new BorderPanel {
        add(projection, BorderPanel.Position.Center)
    } // contents of the GUI
    
    
} // Window
