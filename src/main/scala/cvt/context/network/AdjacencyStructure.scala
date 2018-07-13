package cvt.context.network
import cvt.uiobject.AgentUI
import scala.collection.immutable.{List, Map}


abstract class AdjacencyStructure() {
    def connections : Array[(AgentUI, AgentUI, Double)]
    def add(agent : AgentUI ) : Unit
    def remove(agent : AgentUI) : Unit
    def addConnection(a1 : AgentUI, a2 : AgentUI, weight : Double, directed : Boolean = false) : Boolean
    def removeConnection(a1 : AgentUI, a2 : AgentUI) : Boolean
} // AdjacencyStructure


class AdjacencyList() extends  AdjacencyStructure {
    
    private var map = Map[AgentUI, List[(AgentUI, Double)]]()
    
    
    def connections : Array[(AgentUI, AgentUI, Double)] = {
        var results = Array[(AgentUI, AgentUI, Double)]()
        for ((a1, list) <- map) for ((a2, weight) <- list) results = results :+ (a1, a2, weight)
        results
    } // getConnections
    
    
    override def add(agent : AgentUI) : Unit = map += (agent -> List[(AgentUI, Double)]())
    
    // gets a2's index in a1's list
    private def indexOf(a1 : AgentUI, a2 : AgentUI) : Int = {
         map(a1).zipWithIndex.foreach(e => if (e._1._1 == a2) return e._2)
        -1
    } // indexOf()
    
    
    override def remove(agent : AgentUI) : Unit = {
        if (!map.contains(agent)) return
        for ((a1, list) <- map) for ((a2, _) <- list if a2 == agent) removeConnection(agent, a1)
        map -= agent
    } // remove()
    
    
    def addConnection(a1 : AgentUI, a2 : AgentUI, weight : Double, directed : Boolean) : Boolean = {
        if (!map.contains(a1) || !map.contains(a2)) return false
        map = map.updated(a1, map(a1) :+ (a2, weight))
        if (!directed) map = map.updated(a2, map(a2) :+ (a1, weight))
        true
    } // addConnection()
    
    
    def removeConnection(a1 : AgentUI, a2 : AgentUI) : Boolean = {
        if (!map.contains(a1) || !map.contains(a2)) return false
        val a1Idx = indexOf(a1, a2)
        val a2Idx = indexOf(a2, a1)
        if (a1Idx >= 0) map = map.updated(a1, map(a2).drop(a1Idx + 1))
        if (a2Idx >= 0) map = map.updated(a2, map(a1).drop(a2Idx + 1))
        true
    } // removeConnection()
    
    
    override def toString : String = {
        var s = ""
        for ((a, list) <- map) { s += a + ": "; for ((a2, weight) <- list) s += "-> (" + a2 + ":" + weight + ")"; s += "\n" }
        s
    } // toString
    
} // AdjacencyList


class AdjacencyMatrix() extends AdjacencyStructure {
    
    private var map = Map[AgentUI, Array[Double]]()
    
    
    def connections : Array[(AgentUI, AgentUI, Double)] = {
        var results = Array[(AgentUI, AgentUI, Double)]()
        for ((a1, array) <- map) for (i <- array.indices if array(i) > 0) results = results :+ (a1, agentAtIndex(i) , array(i))
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
    
    
    private def agentAtIndex(i : Int) : AgentUI = {
        map.zipWithIndex.foreach(e => if (e._2 == i) return e._1._1 )
        null
    } // agentAtIndex()
    
    
    def add(agent : AgentUI ) : Unit = {
        for ((a, _) <- map) map = map.updated(a, map(a) :+ 0.0)
        map += (agent -> new Array[Double](map.size + 1))
    } // add()
    
    
    def remove(agent : AgentUI) : Unit = {
        if (!map.contains(agent)) return
        val index = indexOf(agent)
        map -= agent
        for ((a, array) <- map) map = map.updated(a, array.drop(index + 1))
    } // remove()
    
    
    def addConnection(a1 : AgentUI, a2 : AgentUI, weight : Double, directed : Boolean) : Boolean = {
        if (!map.contains(a1) || !map.contains(a2)) return false
        map(a1)(indexOf(a2)) = weight
        if (!directed) map(a2)(indexOf(a1)) = weight
        true
    } // addConnection
    
    
    def removeConnection(a1 : AgentUI, a2 : AgentUI) : Boolean = {
        if (!map.contains(a1) || !map.contains(a2)) return false
        map(a1)(indexOf(a2)) = 0.0
        map(a2)(indexOf(a1)) = 0.0
        true
    } // removeConnection()
    
} // AdjacencyMatrix

