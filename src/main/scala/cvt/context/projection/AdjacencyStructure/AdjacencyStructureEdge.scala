package cvt.context.projection.AdjacencyStructure
import cvt.context.projection.uiobject.AgentUI


/** @constructor An abstract edge class used for both the AdjacencyMatrix and AdjacencyList.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  * @param _toAgent the AgentUI which this edge points to.
  * @param _weight the weight of this edge.
  */
abstract class AdjacencyStructureEdge(_toAgent : AgentUI = null, private var _weight : Double = 0.0) {

  /** A getter for the AgentUI this edge points to. */
  def toAgent : AgentUI = _toAgent


  /** A getter for the weight of this edge. */
  def weight : Double = _weight


  /** A setter for the weight of this edge. */
  def weight_  (value : Double) : Unit = _weight = value


  /** Creates a string representation of this edge. Simply uses the weight.
    * @return a string representation.
    */
  override def toString : String = weight.toString

} // AdjacencyStructureEdge()


/** @constructor A default edge.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  * @param _toAgent the AgentUI which this edge points to.
  * @param _weight the weight of this edge.
  */
class DefaultEdge(_toAgent : AgentUI = null, _weight : Double = 0.0) extends AdjacencyStructureEdge(_toAgent, _weight) {

} // DefaultEdge()
