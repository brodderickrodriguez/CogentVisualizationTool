package cvt.context

import scala.swing.{BorderPanel, Dimension, Label, MainFrame, ScrollPane, Swing}
import java.awt.Color

/**
  *
  * @param dimension the dimension of the window
  * @param context the context to add to the window
  */
class Window(val dimension: Dimension, private val context: Context) extends MainFrame {
    //println("[Window] initializing")
    
    visible = true
    resizable = false
    preferredSize = dimension
    
    private val scrollPane : ScrollPane = new ScrollPane(context)
    val borderSize = 10
    
   // scrollPane.verticalScrollBarPolicy = ScrollPane.BarPolicy.Always
    //scrollPane.preferredSize = new Dimension(50, 50)
    //scrollPane.contents = context
    //centerOnScreen()
    
    
    // add all contents to the GUI (order matters!)
    contents = new BorderPanel {
        border = Swing.MatteBorder(borderSize, borderSize, borderSize, borderSize, Color.darkGray)
        
        add(scrollPane, BorderPanel.Position.Center)
    } // contents of the GUI
    
    
} // Window
