package cvt

import scala.collection.mutable.ArrayBuffer

import scala.swing.Dimension


class Cell(grid : context.Grid, dimension : Dimension) {
    
    var cellCoordinate : Coordinate = _
    var agents : ArrayBuffer[AgentUI] = new ArrayBuffer[AgentUI]()
    
    
    override def toString : String = {
        ""
    } // toString()
    
} // Cell
