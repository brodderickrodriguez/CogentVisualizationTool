package cvt

/**
  *
  * @param x the x coordinate
  * @param y the y coordinate
  */
class Coordinate(var x : Integer, var y : Integer) {
    
    
    /** toString
      *
      * @return a string in the format : "Coordinate(x: <X>, y: <Y>)"
      */
    override def toString : String = {
       "Coordinate(x: " + x + ", y: " + y + ")"
    } // toString()
    
    
    def add(c : Coordinate) : Unit = {
        this.x += c.x
        this.y += c.y
    } // add()
    
    def add(m : Int) : Unit = {
        this.x += m
        this.y += m
    } // add()
    
    def subtract(c : Coordinate) : Unit = {
        this.x -= c.x
        this.y -= c.y
    } // subtract()
    
    def subtract(m : Int) : Unit = {
        this.x -= m
        this.y -= m
    } // subtract()
    
    def multiply(c : Coordinate) : Unit = {
        this.x *= c.x
        this.y *= c.y
    } // multiply()
    
    def multiply(m : Int) : Unit = {
        this.x *= m
        this.y *= m
    } // multiply()
    
    def divide(c : Coordinate) : Unit = {
        this.x /= c.x
        this.y /= c.y
    } // divide()
    
    def divide(m : Int) : Unit = {
        this.x /= m
        this.y /= m
    } // divide()
    
    
} // Coordinate
