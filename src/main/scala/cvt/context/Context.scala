package cvt.context


//import java.awt.{Color, Graphics2D}

import scala.swing._
import scala.collection.mutable.ArrayBuffer


/**
  *
  * @param dimension the dimension of the context
  */
abstract class Context(dimension: Dimension) extends Component   {
    println("Context Initializing")
    
    
    val window = new Window(dimension, this)
    private var allAgents : ArrayBuffer[cvt.AgentUI] = new ArrayBuffer[cvt.AgentUI]()
    
    /**
      *
      * @return an array of all the agents
      */
    def getAllAgents : ArrayBuffer[cvt.AgentUI] = {
        allAgents
    } // getAllAgents()
    
    
    /**
      *
      */
    def removeAllAgents() : Unit = {
    
    } // removeAllAgents()
    
    /**
      * - Context(d : Dimension)
      * - getAgentsWithTypes(t : Array)
      * - addAgent(c : Coordinate)
      * - addAgents(agents : Array)
      * - removeAllAgents()
      * - applyColorScheme(cs : ColorScheme)
      * - removeColorScheme(cs : ColorScheme)
      */
    
    

    
} // Context
