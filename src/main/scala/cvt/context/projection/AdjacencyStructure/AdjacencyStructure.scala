package cvt.context.projection.AdjacencyStructure
import cvt.context.projection.uiobject.AgentUI
import scala.collection.immutable.{List, Map}


/** An abstract class (acts like an interface) for the two Adjacency Structure types.
  * Used by Network Projection.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 july 2018
  */
abstract class AdjacencyStructure {

    /** @return an Array of all the Agents in the Network and the AdjacencyStructure. */
    def agents : Array[AgentUI]


    /** @return an Array of all connections in the AdjacencyStructure.
      *         In the format of a tuple: "(<From_AgentUI>, <To_AgentUI>, Weight)".
      */
    def connections : Array[(AgentUI, AgentUI, Double)]


    /** Gets the connections of an AgentUI.
      * @param agent the AgentUI we wish to get the connections of
      * @return an Array of connections from the parameter AgentUI.
      *         In the format of a tuple: "(<From_AgentUI>, <To_AgentUI>, Weight)".
      */
    def connectionsOf(agent: AgentUI): Array[(AgentUI, AgentUI, Double)] = {
        var results = Array[(AgentUI, AgentUI, Double)]()
        for ((a1, a2, weight) <- connections if a1 == agent) results = results :+ (a1, a2, weight)
        results
    } // connectionsOf()


    /** Adds an AgentUI to the AdjacencyStructure.
      * @param agent The AgentUI we are adding.
      */
    def add(agent : AgentUI) : Unit


    /** Removes an AgentUI from the AdjacencyStructure.
      * @param agent The AgentUi we are removing.
      */
    def remove(agent : AgentUI) : Unit


    /** Adds a connection between two AgentUIs already in the AdjacencyStructure.
      * @param a1 the AgentUI which the connection is from.
      * @param a2 the AgentUI which the connection is to.
      * @param weight the weight of the connection.
      * @param directed a boolean to determine if the connection is directed or not.
      * @return true if we were able to successfully add the connection. False if
      *         at least one of the AgentUIs is not already in the AdjacencyStructure.
      */
    def addConnection(a1 : AgentUI, a2 : AgentUI, weight : Double, directed : Boolean = false) : Boolean


    /** Removes a connection between two AgentUIs in the AdjacencyStructure.
      * Notice: the order of a1 and a2 is important when the conneciton is directed.
      * @param a1 the AgentUI where the connection begins.
      * @param a2 the AgentUI where the connection ends.
      * @return true if we were able to successfully remove the connection.
      */
    def removeConnection(a1 : AgentUI, a2 : AgentUI) : Boolean
    
} // AdjacencyStructure


/** A [[scala.collection.immutable.List]] representation of an Adjacency List.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  */
class AdjacencyList() extends  AdjacencyStructure {

    /** A private [[scala.collection.immutable.Map]] from [[AgentUI]] to [[scala.collection.immutable.List]]. */
    private var map = Map[AgentUI, List[AdjacencyStructureEdge]]()


    /** @return an Array of all the Agents in the Network and the AdjacencyStructure. */
    override def agents : Array[AgentUI] = {
        var results = Array[AgentUI]()
        for ((a,_) <- map) results = results :+ a
        results
    } // entries


    /** @return an Array of all connections in the AdjacencyStructure.
      *         In the format of a tuple: "(<From_AgentUI>, <To_AgentUI>, Weight)".
      */
    override def connections : Array[(AgentUI, AgentUI, Double)] = {
        var results = Array[(AgentUI, AgentUI, Double)]()
        for ((a1, list) <- map) for (edge <- list) results = results :+ (a1, edge.toAgent, edge.weight)
        results
    } // getConnections


    /** Creates a String representation of the AdjacencyList.
      * @return a String representation.
      */
    override def toString : String = {
        var s = ""
        for ((a, list) <- map) {
            s += a + ": "
            for (edge <- list) s += "-> (" + edge.toAgent + ":" + edge.weight + ")"
            s += "\n"
        }
        s
    } // toString


    /** Adds an AgentUI to the AdjacencyStructure.
      * @param agent The AgentUI we are adding.
      */
    override def add(agent : AgentUI) : Unit = map += (agent -> List[AdjacencyStructureEdge]())


    /** Removes an AgentUI from the AdjacencyStructure.
      * @param agent The AgentUi we are removing.
      */
    override def remove(agent : AgentUI) : Unit = {
        if (!map.contains(agent)) return
        for ((a1, list) <- map) for (edge <- list if edge.toAgent == agent) removeConnection(agent, a1)
        map -= agent
    } // remove()


    /** Adds a connection between two AgentUIs already in the AdjacencyStructure.
      * @param a1 the AgentUI which the connection is from.
      * @param a2 the AgentUI which the connection is to.
      * @param weight the weight of the connection.
      * @param directed a boolean to determine if the connection is directed or not.
      * @return true if we were able to successfully add the connection. False if
      *         at least one of the AgentUIs is not already in the AdjacencyStructure.
      */
    override def addConnection(a1 : AgentUI, a2 : AgentUI, weight : Double, directed : Boolean) : Boolean = {
        if (!map.contains(a1) || !map.contains(a2)) return false
        val a1Edge = new DefaultEdge(a2, weight)
        val a2Edge = new DefaultEdge(a1, weight)
        map = map.updated(a1, map(a1) :+ a1Edge)
        if (!directed) map = map.updated(a2, map(a2) :+ a2Edge)
        true
    } // addConnection()


    /** Removes a connection between two AgentUIs in the AdjacencyStructure.
      * Notice: the order of a1 and a2 is important when the conneciton is directed.
      * @param a1 the AgentUI where the connection begins.
      * @param a2 the AgentUI where the connection ends.
      * @return true if we were able to successfully remove the connection.
      */
    override def removeConnection(a1 : AgentUI, a2 : AgentUI) : Boolean = {
        if (!map.contains(a1) || !map.contains(a2)) return false
        map = map.updated(a1, map(a2).filter(_.toAgent!= a1))
        map = map.updated(a2, map(a1).filter(_.toAgent!= a2))
        true
    } // removeConnection()
    
    
} // AdjacencyList


class AdjacencyMatrix() extends AdjacencyStructure {

    /** A private [[scala.collection.immutable.Map]] from [[AgentUI]] to [[Array]]. */
    private var map = Map[AgentUI, Array[AdjacencyStructureEdge]]()


    /** @return an Array of all the Agents in the Network and the AdjacencyStructure. */
    override def agents : Array[AgentUI] = {
        var results = Array[AgentUI]()
        for ((a,_) <- map) results = results :+ a
        results
    } // entries()


    /** @return an Array of all connections in the AdjacencyStructure.
      *         In the format of a tuple: "(<From_AgentUI>, <To_AgentUI>, Weight)".
      */
    override def connections : Array[(AgentUI, AgentUI, Double)] = {
        var results = Array[(AgentUI, AgentUI, Double)]()
        for ((a1, array) <- map)
            for (edge <- array if edge != null && edge.weight > 0)
                results = results :+ (a1, edge.toAgent, edge.weight)
        results
    } // connections


    /** Creates a String representation of the AdjacencyList.
      * @return a String representation.
      */
    override def toString : String = {
        var  s = ""
        for ((a, c) <- map) {s += a + ": "; for (d <- c) s += d + ", "; s += "\n" }
        s
    } // toString()


    /** Finds the index of the AgentUI inside the map
      * @param agent the AgentUI which we wish to find the index of.
      * @return the index of the AgentUI if found. -1 if not found.
      */
    private def indexOf(agent : AgentUI) : Int = {
        map.zipWithIndex.foreach(e => if (e._1._1 == agent) return e._2)
        -1
    } // indexOf()


    /** Adds an AgentUI to the AdjacencyStructure.
      * @param agent The AgentUI we are adding.
      */
    override def add(agent : AgentUI ) : Unit = {
        for ((a2, _) <- map) map = map.updated(a2, map(a2) :+ null)
        map += (agent -> new Array[AdjacencyStructureEdge](map.size + 1))
    } // add()


    /** Removes an AgentUI from the AdjacencyStructure.
      * @param agent The AgentUi we are removing.
      */
    override def remove(agent : AgentUI) : Unit = {
        if (!map.contains(agent)) return
        val index = indexOf(agent)
        map -= agent
        for ((a, array) <- map) map = map.updated(a, array.drop(index + 1))
    } // remove()


    /** Adds a connection between two AgentUIs already in the AdjacencyStructure.
      * @param a1 the AgentUI which the connection is from.
      * @param a2 the AgentUI which the connection is to.
      * @param weight the weight of the connection.
      * @param directed a boolean to determine if the connection is directed or not.
      * @return true if we were able to successfully add the connection. False if
      *         at least one of the AgentUIs is not already in the AdjacencyStructure.
      */
    override def addConnection(a1 : AgentUI, a2 : AgentUI, weight : Double, directed : Boolean) : Boolean = {
        if (!map.contains(a1) || !map.contains(a2)) return false
        map(a1)(indexOf(a2)) = new DefaultEdge(a2, weight)
        if (!directed) map(a2)(indexOf(a1)) = new DefaultEdge(a1, weight)
        true
    } // addConnection


    /** Removes a connection between two AgentUIs in the AdjacencyStructure.
      * Notice: the order of a1 and a2 is important when the conneciton is directed.
      * @param a1 the AgentUI where the connection begins.
      * @param a2 the AgentUI where the connection ends.
      * @return true if we were able to successfully remove the connection.
      */
    override def removeConnection(a1 : AgentUI, a2 : AgentUI) : Boolean = {
        if (!map.contains(a1) || !map.contains(a2)) return false
        map(a1)(indexOf(a2)) = null
        map(a2)(indexOf(a1)) = null
        true
    } // removeConnection()

    
} // AdjacencyMatrix

