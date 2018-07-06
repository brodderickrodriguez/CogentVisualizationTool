package cvt.context

import scala.swing.{BorderPanel, MainFrame, Swing, Dimension}
import java.awt.{Color, Graphics2D}

/**
  *
  * @param dimension the dimension of the window
  * @param context the context to add to the window
  */
class Window(dimension: Dimension, context: Context) extends MainFrame {
    println("creating window...")
    
    
    this.visible = true
    preferredSize = dimension
    //centerOnScreen()
    
    
    // add all contents to the GUI (order matters!)
    contents = new BorderPanel {
        border = Swing.MatteBorder(10, 10, 10, 10, new Color(1, 1, 44, 0))
        add(context, BorderPanel.Position.Center)
    } // contents of the GUI
    
    
} // Window
