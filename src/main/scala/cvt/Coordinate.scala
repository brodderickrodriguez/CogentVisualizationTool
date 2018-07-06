package cvt

/**
  *
  * @param x the x coordinate
  * @param y the y coordinate
  */
class Coordinate(x : Integer, y : Integer) {
    
    
    /** toString
      *
      * @return a string in the format : "Coordinate(x: <X>, y: <Y>)"
      */
    override def toString : String = {
       "Coordinate(x: " + x + ", y: " + y + ")"
    } // toString()
    
} // Coordinate
