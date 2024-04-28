package ex2

import scala.util.Random

type Position = (Int, Int)
enum Direction:
  case North, East, South, West
  def turnRight: Direction = this match
    case Direction.North => Direction.East
    case Direction.East => Direction.South
    case Direction.South => Direction.West
    case Direction.West => Direction.North

  def turnLeft: Direction = this match
    case Direction.North => Direction.West
    case Direction.West => Direction.South
    case Direction.South => Direction.East
    case Direction.East => Direction.North

trait Robot:
  def position: Position
  def direction: Direction
  def turn(dir: Direction): Unit
  def act(): Unit

class SimpleRobot(var position: Position, var direction: Direction) extends Robot:
  def turn(dir: Direction): Unit = direction = dir
  def act(): Unit = position = direction match
    case Direction.North => (position._1, position._2 + 1)
    case Direction.East => (position._1 + 1, position._2)
    case Direction.South => (position._1, position._2 - 1)
    case Direction.West => (position._1 - 1, position._2)
  override def toString: String = s"robot at $position facing $direction"

class RobotWithBattery(val robot: Robot, val actionCost: Int, var battery: Int = 100) extends Robot:
  export robot.{act => _, *}
  private def decreaseBattery(): Unit = battery -= actionCost
  private def isNotLowBattery: Boolean = battery > 0
  override def act(): Unit = if isNotLowBattery then { robot.act(); decreaseBattery() }

class RobotCanFail(val robot: Robot, val probability: Int) extends Robot:
  export robot.{act => _, *}
  private def isPossibleAction: Boolean = Random.nextInt(101) <= probability
  override def act(): Unit = if isPossibleAction then robot.act()

class RobotRepeated(val robot: Robot, val repeatAction: Int) extends Robot:
  export robot.{act => _, *}
  override def act(): Unit = List.iterate(0, repeatAction).apply(i => i).foreach(_ => robot.act())

class DumbRobot(val robot: Robot) extends Robot:
  export robot.{position, direction, act}
  override def turn(dir: Direction): Unit = {}
  override def toString: String = s"${robot.toString} (Dump)"

class LoggingRobot(val robot: Robot) extends Robot:
  export robot.{position, direction, turn}
  override def act(): Unit =
    robot.act()
    println(robot.toString)



@main def testRobot(): Unit =
  val robot = LoggingRobot(SimpleRobot((0, 0), Direction.North))
  robot.act() // robot at (0, 1) facing North
  robot.turn(robot.direction.turnRight) // robot at (0, 1) facing East
  robot.act() // robot at (1, 1) facing East
  robot.act() // robot at (2, 1) facing East
