package ex2

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.postfixOps

class RobotWithBatteryTest extends AnyFlatSpec with Matchers:
  val directionWithStep = Direction.values.zip(Stream.iterate(1)(_ + 1))
  val startPosition = (0, 0)
  val actionCost = 25
  val battery = 100

  "A SimpleRobot with battery" should "act correctly" in :
    val robot = new RobotWithBattery(SimpleRobot((0, 0), Direction.North), actionCost, battery)
    directionWithStep.foreach((direction, step) => {
      robot.act()
      robot.battery should be(battery - (actionCost * step))
    })

  it should "turn with zero battery" in :
    val zeroBattery = 0
    val robot = new RobotWithBattery(SimpleRobot((0, 0), Direction.North), actionCost, zeroBattery)
    robot.act()
    robot.position should be(startPosition)












    
    