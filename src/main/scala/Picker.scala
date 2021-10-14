import scala.util.Random

object Picker {
  def choose(objects: List[String], count: Int): List[String] = {
    Random.shuffle(objects).take(count)
  }

  def roll(min: Int, max: Int): Int = {
    Random.nextInt(max + 1 - min) + min
  }
}