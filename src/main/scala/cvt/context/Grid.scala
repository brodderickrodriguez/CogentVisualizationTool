package cvt.context

import java.awt.{Color, Graphics2D, Dimension}
import cvt.{AgentUI, Cell, Coordinate}

import scala.collection.mutable.ArrayBuffer



/**
  *
  * @param dimension the dimension of the grid in cells
  */
class Grid(var dimension: Dimension, cellSize : Integer = 50) extends Context(dimension: Dimension) {
    println("Grid Context initializing")
    
    val grid : Array[Array[Cell]] = Array.ofDim[Cell](dimension.width, dimension.height)
    
    // shape the window to fit all cells
    window.size = new Dimension(dimension.width * cellSize + (cellSize - 1), dimension.height * cellSize + (cellSize - 1))
    window.repaint()
    
    
    def addAgent(agent : AgentUI) : Boolean = {
        false
    } // addAgent()
    
    
    def addAgents(agents: ArrayBuffer[AgentUI]) : Boolean = {
        false
    } // addAgents()
    
    
    def removeAgent(agent : AgentUI) : Boolean = {
        false
    } // removeAgent
    
    
    def removeAllAgents() : Unit = {
    
    } // removeAllAgents()
    
    
    
    /**
      *
      * @param graphics the graphics object
      */
    override def paintComponent(graphics: Graphics2D): Unit = {
        println("painting grid")
    
        graphics.setColor(Color.BLACK)
        graphics.fillRect(0, 0, cellSize, cellSize)
    
    /*
        
 
       
        
        val cellDimension = new Dimension(cellSize, cellSize)
        for (y <- grid.indices) {
            
            for (x <- grid(0).indices) {
                
                println("creating cell")
    
                val xPosition = x * cellSize
                val yPosition = y * cellSize
    
    
                val cell = new Cell(this, cellDimension)
    

            //    graphics.setColor(Color.BLACK)
            //    graphics.fillRect(xPosition, yPosition, cellSize- 1, cellSize - 1)
    
    
            }
            
            
        }
        */
        
      //  super.paintComponent(g)
    } // paintComponent()
    
    
    /**
      *
      * @param agent the agent we wish to move
      * @param c the coordinate of the cell we wish to go to
      */
    def move(agent : AgentUI, c : Coordinate) : Unit = {
    
    } // move()
    
    
    /**
      *
      * @param agent the agent to move
      * @param direction which we want to move (1 = N, 2 = E, 3 = S, 4 = W)
      * @param magnitude is the amount of cells we wish to move
      */
    def move(agent : AgentUI, direction : Direction.Value, magnitude : Integer) : Unit = {
    
    } // move()

    
    
} // Grid
