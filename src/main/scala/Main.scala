import scopt.OptionParser

// Config class for the "randomizer"
case class Config(count: Int = 1, objects: List[String] = List(), mode: String = "choose")

object Main extends App {
  val parser = new OptionParser[Config]("randomizer") {
    override def showUsageOnError = true

    head("randomizer", "0.0.1")

    cmd("choose")
      .action((_, c) => c.copy(mode = "choose"))
      .text("'choose' picks from an unbounded number of strings!")
      .children(
        opt[Int]('c', "count")
          .action((x, c) => c.copy(count = x))
          .text("'count' determines how many items will be picked!")
      )

    cmd("roll")
      .action((_, c) => c.copy(mode = "roll"))
      .text("'roll' picks as number between the min and max arguments!")
      .children(
        arg[String]("<options>...").minOccurs(2).maxOccurs(2).required()
          .action((x, c) => c.copy(objects = c.objects :+ x))
          .text("Min followed by Max!")
      )

    arg[String]("<options>...").unbounded().optional().action((x,c) =>
      c.copy(objects = c.objects :+ x)).text("Options to choose from. Ex 'x y z'")

    help("help").text("Prints THIS usage text")
  }

  parser.parse(args, Config()) match {
    case Some(config) =>
      config.mode match {
        case "choose" =>
          Picker.choose(config.objects, config.count).foreach(println)
        case "roll" =>
          println(Picker.roll(config.objects(0).toInt, config.objects(1).toInt))
      }

    case None =>
      // arguments are bad, error message will have been displayed
  }
}