package cvt.context
import cvt.{AgentUI, Cell, Coordinate}

import scala.swing.Dimension
import scala.collection.mutable.ArrayBuffer

/**
  *
  * @param dimension the dimension of the grid in cells
  */
class Grid(dimension: Dimension) extends Context(dimension: Dimension) {
    println("Grid Context initializing")
    
    var cellSize : Integer = 50
    var cells : ArrayBuffer[Cell] = ArrayBuffer[Cell]()
    
    
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
    def move(agent : AgentUI, direction : Integer, magnitude : Integer) : Unit = {
    
    } // move()
    
    
} // Grid
