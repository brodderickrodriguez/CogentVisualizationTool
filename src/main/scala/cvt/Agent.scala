package cvt
import cvt.context.Context
import cvt.context.projection
import cvt.context.projection.Projection


/** AgentNotification object is used for notifying an Agent that an event has
  * occurred on a given Projection. This information is used for the Agent to react
  * to something such as a new neighbor, an overpopulated Cell, etc.
  */
object AgentNotification extends Enumeration {
    val move : Value = Value
} // MockCogentNotification


/** A test Object for Agent types. This is supposed to be replaced by the actual Agent type.
  * It was needed to implement 'getAgentsOfType' and 'getNeighborsOfType'.
  * This object should be replaced with the actual Agent type in the Cogent project.
  */
object AgentType extends  Enumeration {
    val boring : Value = Value
    val exciting : Value = Value
    val daring : Value = Value
} // MockCogentType


/**
  * A placeholder for the Agent in the Cogent project. This class was used purely for testing. It would be replaced
  * by the actual Agent.
  * DISCLAIMER:
  *
  * The sole purpose of this class is to mimic how the CogentVisualizationTool will be used.
  * It is essentially a placeholder.
  * This class should be replaced with the Agent class in the Cogent Project.
  *
  * The important parts of this class (which should be retained) are:
  * (1) AgentNotification - the event (or change) we want to inform the Agent of.
  * (2) Agent.receiveNotification - notifies the Agent that an event (or change) has occurred on a Projection.
  * (3) context - the Context which this Agent is a part of.
  */
class Agent {
    /** A mock type for the Agent. */
    var agentType : AgentType.Value = AgentType.boring
    /** The Context which this Agent is a part of. */
    var context : Context = _


    /** @constructor Creates an agent of a given AgentType.
      * @param t The AgentType we are assigning to this Agent.
      */
    def this (t : AgentType.Value) {
        this
        this.agentType = t
    } // this()


    /** Generates a random direction. Used to mimic how Cogents will decide to move around a given Projection.
      * @return A random Direction.
      */
    def randomDirection : cvt.context.projection.Direction.Value = {
        scala.util.Random.nextInt(4) match  {
            case 0 => projection.Direction.up
            case 1 => projection.Direction.right
            case 2 => projection.Direction.left
            case 3 => projection.Direction.down
        } // match
    } // randomDirection()


    /** This is called by AgentUI to notify the Agent that a change has occurred in a Projection.
      * @param notification The Notification (event) we want to inform the Agent of.
      * @param fromProjection The Projection in which the change occurred.
      */
    def receiveNotification(notification: AgentNotification.Value, fromProjection : Projection) : Unit = {
        // randomly move the agentUI on the projection 'fromProjection'. Used for testing.
        if (scala.util.Random.nextInt(10) == 1)
            fromProjection.move(this, randomDirection, 1)
    } // receiveNotification()


} // MockAgent
