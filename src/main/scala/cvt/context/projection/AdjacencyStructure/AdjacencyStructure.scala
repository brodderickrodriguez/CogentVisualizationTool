package cvt.context.projection.AdjacencyStructure

import cvt.context.projection.uiobject.AgentUI
import scala.collection.immutable.{List, Map, Vector}


/** An abstract class (acts like an interface) for the two Adjacency Structure types. */
abstract class AdjacencyStructure() {

    def agents : Array[AgentUI]

    def connections : Array[(AgentUI, AgentUI, Double)]


    def connectionsOf(agent: AgentUI): Array[(AgentUI, AgentUI, Double)] = {
        var results = Array[(AgentUI, AgentUI, Double)]()
        for ((a1, a2, weight) <- connections if a1 == agent) results = results :+ (a1, a2, weight)
        results
    } // connectionsOf()


    def add(agent : AgentUI) : Unit

    def remove(agent : AgentUI) : Unit

    def addConnection(a1 : AgentUI, a2 : AgentUI, weight : Double, directed : Boolean = false) : Boolean

    def removeConnection(a1 : AgentUI, a2 : AgentUI) : Boolean
    
} // AdjacencyStructure


class AdjacencyList() extends  AdjacencyStructure {

    private var map = Map[AgentUI, List[AdjacencyStructureEdge]]()
    
    override def agents : Array[AgentUI] = {
        var results = Array[AgentUI]()
        for ((a,_) <- map) results = results :+ a
        results
    } // entries


    override def connections : Array[(AgentUI, AgentUI, Double)] = {
        var results = Array[(AgentUI, AgentUI, Double)]()
        for ((a1, list) <- map) for (edge <- list) results = results :+ (a1, edge.toAgent, edge.weight)
        results
    } // getConnections


    override def toString : String = {
        var s = ""
        for ((a, list) <- map) {
            s += a + ": "
            for (edge <- list) s += "-> (" + edge.toAgent + ":" + edge.weight + ")"
            s += "\n"
        }
        s
    } // toString

    
    override def add(agent : AgentUI) : Unit = map += (agent -> List[AdjacencyStructureEdge]())

    
    override def remove(agent : AgentUI) : Unit = {
        if (!map.contains(agent)) return
        for ((a1, list) <- map) for (edge <- list if edge.toAgent == agent) removeConnection(agent, a1)
        map -= agent
    } // remove()
    
    
    override def addConnection(a1 : AgentUI, a2 : AgentUI, weight : Double, directed : Boolean) : Boolean = {
        if (!map.contains(a1) || !map.contains(a2)) return false
        val a1Edge = new DefaultEdge(a2, weight)
        val a2Edge = new DefaultEdge(a1, weight)
        map = map.updated(a1, map(a1) :+ a1Edge)
        if (!directed) map = map.updated(a2, map(a2) :+ a2Edge)
        true
    } // addConnection()


    override def removeConnection(a1 : AgentUI, a2 : AgentUI) : Boolean = {
        if (!map.contains(a1) || !map.contains(a2)) return false
        map = map.updated(a1, map(a2).filter(_.toAgent!= a1))
        map = map.updated(a2, map(a1).filter(_.toAgent!= a2))
        true
    } // removeConnection()
    
    
} // AdjacencyList


class AdjacencyMatrix() extends AdjacencyStructure {

    private var map = Map[AgentUI, Array[AdjacencyStructureEdge]]()
    
    
    override def agents : Array[AgentUI] = {
        var results = Array[AgentUI]()
        for ((a,_) <- map) results = results :+ a
        results
    } // entries()
    
    
    override def connections : Array[(AgentUI, AgentUI, Double)] = {
        var results = Array[(AgentUI, AgentUI, Double)]()
        for ((a1, array) <- map)
            for (edge <- array if edge != null && edge.weight > 0)
                results = results :+ (a1, edge.toAgent, edge.weight)
        results
    } // connections


    override def toString : String = {
        var  s = ""
        for ((a, c) <- map) {s += a + ": "; for (d <- c) s += d + ", "; s += "\n" }
        s
    } // toString()
    
    
    // find the index of the agent inside the map
    private def indexOf(agent : AgentUI) : Int = {
        map.zipWithIndex.foreach(e => if (e._1._1 == agent) return e._2)
        -1
    } // indexOf()
    

    override def add(agent : AgentUI ) : Unit = {
        for ((a2, _) <- map) map = map.updated(a2, map(a2) :+ null)
        map += (agent -> new Array[AdjacencyStructureEdge](map.size + 1))
    } // add()
    
    
    override def remove(agent : AgentUI) : Unit = {
        if (!map.contains(agent)) return
        val index = indexOf(agent)
        map -= agent
        for ((a, array) <- map) map = map.updated(a, array.drop(index + 1))
    } // remove()
    
    
    override def addConnection(a1 : AgentUI, a2 : AgentUI, weight : Double, directed : Boolean) : Boolean = {
        if (!map.contains(a1) || !map.contains(a2)) return false
        map(a1)(indexOf(a2)) = new DefaultEdge(a2, weight)
        if (!directed) map(a2)(indexOf(a1)) = new DefaultEdge(a1, weight)
        true
    } // addConnection
    

    override def removeConnection(a1 : AgentUI, a2 : AgentUI) : Boolean = {
        if (!map.contains(a1) || !map.contains(a2)) return false
        map(a1)(indexOf(a2)) = null
        map(a2)(indexOf(a1)) = null
        true
    } // removeConnection()

    
} // AdjacencyMatrix

