package ex2

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class RobotSpec extends AnyFlatSpec with Matchers:
  val startPosition = (0, 0)

  "A SimpleRobot" should "turn correctly" in :
    val robot = new SimpleRobot(startPosition, Direction.North)

    Direction.values.foreach(direction => {
      robot.turn(direction)
      robot.direction shouldBe direction
    })

  it should "act correctly" in :
    val robot = new SimpleRobot(startPosition, Direction.North)

    Direction.values.zip(List((0, 1), (1, 1), (1, 0), (0, 0))).foreach((direction, position) => {
      robot.turn(direction)
      robot.act()
      robot.position shouldBe position
    })

class RobotWithBatteryTest extends AnyFlatSpec with Matchers:
  val startPosition = (0, 0)
  val actionCost = 25
  "A Robot with battery" should "act correctly" in :
    val battery = 100
    val directionWithStep = Direction.values.zip(Stream.iterate(1)(_ + 1))
    val robot = new RobotWithBattery(SimpleRobot(startPosition, Direction.North), actionCost, battery)

    directionWithStep.foreach((direction, step) => {
      robot.act()
      robot.battery shouldBe (battery - (actionCost * step))
    })

  it should "turn with zero battery" in :
    val zeroBattery = 0
    val robot = new RobotWithBattery(SimpleRobot(startPosition, Direction.North), actionCost, zeroBattery)
    robot.act()
    robot.position shouldBe startPosition

class RobotCanFailTest extends AnyFlatSpec with Matchers:
  val startPosition = (0, 0)

  "A Robot can fail with probability" should "act with 100%" in :
    val probability = 100
    val robot = new RobotCanFail(SimpleRobot(startPosition, Direction.North), probability)
    robot.act()
    robot.position shouldBe (0, 1)

  it should "act with 0%" in :
    val probability = 0
    val robot = new RobotCanFail(SimpleRobot(startPosition, Direction.North), probability)
    robot.act()
    robot.position shouldBe startPosition

class RobotRepeatedTest extends AnyFlatSpec with Matchers:
  val startPosition = (0, 0)

  "A robot repeat action" should "n times act" in:
    val repeatAction = 5
    val robot = new RobotRepeated(SimpleRobot(startPosition, Direction.North), repeatAction)
    robot.act()
    robot.position shouldBe (0, repeatAction)

  it should "0 times act" in:
    val repeatAction = 0
    val robot = new RobotRepeated(SimpleRobot(startPosition, Direction.North), repeatAction)
    robot.act()
    robot.position shouldBe startPosition


