package cvt.context.projection
import java.awt.Color
import cvt.context.projection.uiobject.{AgentUI, Cell}
import cvt.AgentType

/**
  * An Object which allows for user-friendly creation and use of ColorSchemes.
  * A ColorSchemeUse determines what the purpose of a ColorScheme is.
  */
object ColorSchemeUse extends Enumeration {

    /** Used as a default. All Projections and UIObjects will fall back on their default ColorScheme. */
    val defaultColorUse : Value = Value

    /** Used to determine what color an AgentUI is. Applies to all Projections. */
    val agentColorUse : Value = Value

    /** Used to determine what color a Cell is. Applies to the Grid Projection. */
    val cellColorUse : Value = Value

    /** Used to determine what color a connection is. Applies to the Network Projection. */
    val connectionColorUse : Value = Value

} // ColorSchemeUse


/**
  * An Object which allows for user-friendly creation and use of ColorSchemes.
  * A ColorSchemeUse ter
  *
  */
object ColorSchemes extends Enumeration {

    /** Default ColorScheme is applied to all Projections and UIObjects if their is no other active ColorScheme. */
    val default : ColorScheme = new Default

    /** A ColorScheme which determines Cell colors based on how many Agents are in the Cell. */
    val cellColorByPopulation : ColorScheme = new CellColorByPopulation

    /** ColorScheme which determines Cell colors based the type of Agents within the Cell. */
    val cellColorByAgentType : ColorScheme = new CellColorByAgentType

    /** A ColorScheme which assigns a random color to an AgentUI. */
    val agentColorRandom : ColorScheme = new AgentColorRandom

    /** A ColorScheme which disables painting AgentUIs. When applied, AgentUIs will not appear on screen. */
    val doNotPaintAgent : ColorScheme = new DoNotPaintAgent

} // ColorSchemes


/**
  *  @constructor A ColorScheme which disables painting AgentUIs. When applied, AgentUIs will not appear on screen.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  */
private class DoNotPaintAgent extends ColorScheme {

    /** Determines what the ColorScheme is used for. See the Enumeration for [[ColorSchemeUse]] for details. */
    use = ColorSchemeUse.agentColorUse

    /** A boolean variable which if true, the AgentUIs in a Projection are not drawn. */
    paintAgent = false

} // DoNotPaintAgent


/**
  * @constructor A ColorScheme which assigns a random color to an AgentUI.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  */
private class AgentColorRandom extends ColorScheme {

    /** Determines what the ColorScheme is used for. See the Enumeration for [[ColorSchemeUse]] for details. */
    use = ColorSchemeUse.agentColorUse

    /**
      * Generates a random color for the AgentUI.
      * @param agentUI the AgentUI which we want to get the color of.
      * @return the color of the AgentUI.
      */
    override def getAgentColor(agentUI: AgentUI): Color = {
        val r = scala.util.Random
        agentUI.color = new Color(r.nextFloat(), r.nextFloat(), r.nextFloat())
        agentUI.color
    } // getAgentColor()
    
} // CogentColorRandomColorScheme


/**
  * @constructor A ColorScheme which determines Cell colors based the type of Agents within the Cell.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  */
private class CellColorByAgentType extends ColorScheme {

    /** Determines what the ColorScheme is used for. See the Enumeration for [[ColorSchemeUse]] for details. */
    use = ColorSchemeUse.cellColorUse

    /**
      *  Retrieves the color for a Grid Cell based on the type of Agents in the Cell.
      * @param cell the Cell we wish to get the color of.
      * @return the color for the Cell.
      */
    override def getCellColor(cell: Cell) : Color = {
        var color = Color.gray
        if (cell.agents.isEmpty) return color
        
        cell.agents.head.agent.agentType match {
            case AgentType.exciting  => color = Color.yellow
            case AgentType.boring  => color = Color.white
            case AgentType.daring  => color = Color.green
            case default => Color.gray
        } // match
        color
    } // getCellColor()
    
} // GridCellColorByPopulation()


/**
  *  @constructor A ColorScheme which determines Cell colors based on how many Agents are in the Cell.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  */
private class CellColorByPopulation extends ColorScheme {

    /** Determines what the ColorScheme is used for. See the Enumeration for [[ColorSchemeUse]] for details. */
    use = ColorSchemeUse.cellColorUse


    /**
      *  Retrieves the color for a Grid Cell based on the population.
      * @param cell the Cell we wish to get the color of.
      * @return the color for the Cell.
      */
    override def getCellColor(cell: Cell) : Color = {
        var color = Color.black
        cell.agents.length match {
            case 0 => color = Color.lightGray
            case 1 => color = Color.magenta
            case 2 => color = Color.blue
            case 3 => color = Color.green
            case 4 => color = Color.yellow
            case 5 => color = Color.red
            case default => Color.black
        } // match
        color
    } // getCellColor()
    
} // GridCellColorByPopulation()


/**
  * @constructor A default ColorScheme.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  */
private class Default extends ColorScheme {

    /** Determines what the ColorScheme is used for. See the Enumeration for [[ColorSchemeUse]] for details. */
    use = ColorSchemeUse.defaultColorUse

} // Default


/**
  * @constructor An abstract class for ColorSchemes.
  * @author Brodderick Rodriguez (bcr@brodderick.com)
  * @since 29 July 2018
  */
abstract class ColorScheme {

    /**
      * A boolean variable which inheriting ColorSchemes can choose to set.
      * If true, the AgentUIs in a Projection are not drawn.
      * */
    var paintAgent : Boolean = true


    /** Determines what the ColorScheme is used for. See the Enumeration for [[ColorSchemeUse]] for details. */
    var use : ColorSchemeUse.Value = _


    /**
      * Retrieves the color for an AgentUI.
      * @param agentUI the AgentUI which we want to get the color of.
      * @return the color of the AgentUI.
      */
    def getAgentColor(agentUI: AgentUI) : Color = { Color.blue } // getAgentColor()


    /**
      * Retrieves the color for a Grid Cell.
      * @param cell the Cell we wish to get the color of.
      * @return the color for the Cell.
      */
    def getCellColor(cell: Cell) : Color = { Color.gray } // getCellColor()


} // ColorScheme




