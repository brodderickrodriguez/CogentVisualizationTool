package cvt

import scala.collection.mutable.ArrayBuffer

import scala.swing.Dimension


class Cell(grid : context.Grid, dimension : Dimension) {
    
    var absoluteLocation : Coordinate = _
    var identity : Coordinate = _
    var agents : ArrayBuffer[AgentUI] = new ArrayBuffer[AgentUI]()
    
} // Cell
