package cvt.context.projection.AdjacencyStructure

import cvt.context.projection.uiobject.AgentUI


abstract class AdjacencyStructureEdge(_toAgent : AgentUI = null, private var _weight : Double = 0.0) {

  def toAgent : AgentUI = _toAgent
  def weight : Double = _weight

  def weight_  (value : Double) : Unit = _weight = value

  override def toString : String = weight.toString

} // AdjacencyStructureEdge()


class DefaultEdge(_toAgent : AgentUI = null, _weight : Double = 0.0) extends AdjacencyStructureEdge(_toAgent, _weight) {

} // DefaultEdge()
