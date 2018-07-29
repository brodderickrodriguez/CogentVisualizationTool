package cvt.context.projection

/** A Coordinate is used by the Projections and UIObjects as reference points to actual points on the window.
  * @param _x the x coordinate
  * @param _y the y coordinate
  */
class Coordinate(_x : Int, _y : Int) {

    /** A getter for the X value of this Coordinate. */
    val X : Int = _x

    /** A getter for the Y value of this Coordinate. */
    val Y : Int = _y

    /** An Alternative Constructor that creates another Coordinate object using the X and Y
      * values of the parameter Coordinate.
      * @param c the Coordinate we wish to duplicate.
      * @return A new Coordinate.
      */
    def this(c : Coordinate) = this(c.X, c.Y)


    /** Creates a String representing this Coordinate in the format of:
      * "Coordinate(x: <X>, y: <Y>)"
      * @return the String representation.
      */
    override def toString : String = "Coordinate(x: " + _x + ", y: " + _y + ")"

    /** Checks if some other Coordinate is equal to this Coordinates.
      * @param c The Coordinate we want to compare.
      * @return True if X and Y values match. False otherwise.
      */
    def equals(c : Coordinate) : Boolean = c.X == _x && c.Y == _y


    /** Adds some value to this Coordinate.
      * @param m the value we wish to add.
      * @return a new Coordinate.
      */
    def add(m: Int) : Coordinate = add(new Coordinate(m, m))


    /** Adds a Coordinate to this Coordinate.
      * @param c The Coordinate we wish to add.
      * @return A new Coordinate.
      */
    def add(c : Coordinate) : Coordinate = new Coordinate(_x + c.X, _y + c.Y)


    /** Adds some value to the X value of this Coordinate.
      * @param m the value we wish to add to X.
      * @return a new Coordinate.
      */
    def addX(m : Int) = new Coordinate(_x + m, _y)


    /** Adds some value to this Coordinate.
      * @param m the amount we wish to add.
      * @return a new Coordinate.
      */
    def addY(m : Int) = new Coordinate(_x, _y + m)


    /** Subtracts this Coordinate by some value m.
      * @param m the value we wish to subtract.
      * @return a new Coordinate.
      */
    def subtract(m : Int) : Coordinate = subtract(new Coordinate(m , m))


    /** Subtracts this Coordinate by another Coordinate.
      * @param c The Coordinate we wish to subtract.
      * @return a new Coordinate.
      */
    def subtract(c : Coordinate) : Coordinate = new Coordinate(_x - c.X, _y - c.Y)


    /** Subtract the X value of this Coordinate by some value m.
      * @param m the amount we wish to subtract X by.
      * @return a new Coordinate.
      */
    def subX(m : Int) = new Coordinate(_x - m, _y)


    /** Subtract the Y value of this Coordinate by some value m.
      * @param m the amount we wish to subtract Y by.
      * @return a new Coordinate.
      */
    def subY(m : Int) = new Coordinate(_x, _y - m)


    /** Multiplies this Coordinate with some scalar and returns a new Coordinate.
      * @param m the scalar.
      * @return a new Coordinate.
      */
    def multiply(m : Int) : Coordinate = new Coordinate(_x * m, _y * m)

    
} // Coordinate
