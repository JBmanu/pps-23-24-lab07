package ex2

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.postfixOps

class RobotWithBatteryTest extends AnyFlatSpec with Matchers:

  "A SimpleRobot with battery" should "turn correctly" in :
    val robot = new RobotWithBattery(SimpleRobot((0, 0), Direction.North), 4)

    robot.turn(Direction.East)
    robot.direction should be(Direction.East)
    robot.battery should be(3)

    robot.turn(Direction.South)
    robot.direction should be(Direction.South)
    robot.battery should be(2)

    robot.turn(Direction.West)
    robot.direction should be(Direction.West)
    robot.battery should be(1)

    robot.turn(Direction.North)
    robot.direction should be(Direction.North)
    robot.battery should be(0)
    







    
    