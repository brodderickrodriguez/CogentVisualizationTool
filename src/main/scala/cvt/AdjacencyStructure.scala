package cvt

object AdjacencyStructure {
    
    class AdjacencyList() extends  AdjacencyStructure {
        
        private class Wrapper(val item : Any, var prev : Wrapper, var next : Wrapper)
        
        private var head : Wrapper = _
        private var tail : Wrapper = _
        
    } // AdjacencyList
    
    class AdjacencyMatrix() extends AdjacencyStructure {
    } // AdjacencyMatrix
    
    
    abstract class AdjacencyStructure() {
        //  def add(item : Any ) : Unit
        //  def remove(item : Any) : Unit
        //  def first() : Unit
        //  def last() : Unit
    } // AdjacencyStructure
    
} // AdjacencyStructure