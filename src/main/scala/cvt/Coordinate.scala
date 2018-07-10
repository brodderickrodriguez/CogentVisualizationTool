package cvt

/**
  *
  * @param x the x coordinate
  * @param y the y coordinate
  */
class Coordinate(var x : Int, var y : Int) {
    
    def this(c : Coordinate) = this(c.x + c.y, c.y + c.x)
    
    override def toString : String = "Coordinate(x: " + x + ", y: " + y + ")"
    
    def add(m: Int) : Coordinate = add(new Coordinate(m, m))
    def add(c : Coordinate) : Coordinate = new Coordinate(x + c.x, y + c.y)
    
    def subtract(m : Int) : Coordinate = subtract(new Coordinate(m , m))
    def subtract(c : Coordinate) : Coordinate = new Coordinate(x - c.x, y - c.y)
    
    def multiply(m : Int) : Coordinate = multiply(new Coordinate(m, m))
    def multiply(c : Coordinate) : Coordinate = new Coordinate(x * c.x, y * c.y)
    
    def divide(m : Int) : Coordinate = multiply(new Coordinate(m, m))
    def divide(c : Coordinate) : Coordinate = new Coordinate(x / c.x, y / c.y)

} // Coordinate
