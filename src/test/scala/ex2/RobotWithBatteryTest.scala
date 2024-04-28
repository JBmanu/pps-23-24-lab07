package ex2

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

import scala.language.postfixOps

class RobotWithBatteryTest extends AnyFlatSpec with Matchers:

  "A SimpleRobot with battery" should "turn correctly" in :
    val battery = 100
    val actionCost = 25
    val robot = new RobotWithBattery(SimpleRobot((0, 0), Direction.North), actionCost, battery)

    Direction.values.zip(Stream.iterate(1)(_ + 1)).foreach((direction, step) => {
      robot.turn(direction)
      robot.direction should be(direction)
      robot.battery should be(battery - (actionCost * step))
    })

  it should "act correctly" in :
    val robot = new RobotWithBattery(SimpleRobot((0, 0), Direction.North), 4)

    robot.act()
    robot.position should be((0, 1))

    robot.turn(Direction.East)
    robot.act()
    robot.position should be((1, 1))

    robot.turn(Direction.South)
    robot.act()
    robot.position should be((1, 0))

    robot.turn(Direction.West)
    robot.act()
    robot.position should be((0, 0))

  it should "zero battery" in :
    val robot = new RobotWithBattery(SimpleRobot((0, 0), Direction.North), 100)









    
    