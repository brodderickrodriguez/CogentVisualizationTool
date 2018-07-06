package cvt.context

import scala.swing.{BorderPanel, Dimension, MainFrame, ScrollPane, Swing}
import java.awt.{Color}

/**
  *
  * @param dimension the dimension of the window
  * @param context the context to add to the window
  */
class Window(private val dimension: Dimension, private val context: Context) extends MainFrame {
    println("creating window...")
    
    this.visible = true
    preferredSize = dimension
    private val scrollPane : ScrollPane = new ScrollPane(context)
    
    scrollPane.verticalScrollBarPolicy = ScrollPane.BarPolicy.Always
    scrollPane.preferredSize = new Dimension(500, 500)
    scrollPane.contents = context
    //centerOnScreen()
    
    
    // add all contents to the GUI (order matters!)
    contents = new BorderPanel {
        border = Swing.MatteBorder(10, 10, 10, 10, new Color(1, 1, 44, 0))
        add(scrollPane, BorderPanel.Position.Center)
    } // contents of the GUI
    
    
} // Window
