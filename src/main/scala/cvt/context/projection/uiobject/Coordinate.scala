package cvt.context.projection.uiobject

/**
  *
  * @param x the x coordinate
  * @param y the y coordinate
  */
class Coordinate(x : Int, y : Int) {
    
    val X : Int = x
    val Y : Int = y
    
    def this(c : Coordinate) = this(c.X, c.Y)
    
    override def toString : String = "Coordinate(x: " + x + ", y: " + y + ")"

    def equals(c : Coordinate) : Boolean = c.X == x && c.Y == y
    
    def add(m: Int) : Coordinate = add(new Coordinate(m, m))
    def add(c : Coordinate) : Coordinate = new Coordinate(x + c.X, y + c.Y)
    def addX(m : Int) = new Coordinate(x + m, y)
    def addY(m : Int) = new Coordinate(x, y + m)
    
    def subtract(m : Int) : Coordinate = subtract(new Coordinate(m , m))
    def subtract(c : Coordinate) : Coordinate = new Coordinate(x - c.X, y - c.Y)
    def subX(m : Int) = new Coordinate(x - m, y)
    def subY(m : Int) = new Coordinate(x, y - m)
    
} // Coordinate
