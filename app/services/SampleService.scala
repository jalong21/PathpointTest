package services

import com.google.gson.JsonParseException
import play.api.libs.json.{Json, Writes}

import java.io.FileNotFoundException
import scala.io.Source
import scala.util.parsing.json.JSON

case class ScoreResult(score: Int, id: String)
object ScoreResult {
  implicit val jsonWrites: Writes[ScoreResult] = Json.writes[ScoreResult]
}

object SampleService {

  def getHighestScores(filePath: String = "conf/highest_scores_clean.data") = {

    var returnJson: String = ""

    try {
      // read file
      val scoreLines: Iterator[String] = Source.fromFile(filePath).getLines()

      val scoreResults: Seq[ScoreResult] = scoreLines.map(line => {
        val splitIndex = line.indexOf(':')
        if (splitIndex != -1) {
          val score = line.substring(0, splitIndex).trim.toInt
          val jsonString = line.substring(splitIndex + 1).trim

          try {
            // parse JSON and check for 'id'
            JSON.parseFull(jsonString) match {
              // JSON.parseFull returns a map of String => Any.
              case Some(json: Map[String, Any]) => json.get("id") match {
                // Check for the "id" key
                case Some(id: String) => Some(ScoreResult(score, id))
                // if the id isn't there, throw exception
                case _ => throw new JsonParseException("invalid json format No JSON object could be decoded")
              }
              // if we get here, the JSON.parseFull didn't work.
              case _ => throw new JsonParseException("invalid json format No JSON object could be decoded")
            }
          } catch {
            case e: Exception => throw new JsonParseException("invalid json format No JSON object could be decoded")
          }
        } else {
          None
        }
      }).flatten.toSeq

      // sort scores
      val topScores = scoreResults.sortWith((result1, result2) => result1.score > result2.score)
      // create json string
      returnJson = Json.prettyPrint(Json.toJson[Seq[ScoreResult]](topScores))
      // print for logs
      println(returnJson)

    } catch {
      case e: JsonParseException => {
        println(s"JsonParseException: ${e.getMessage}")
        // set return string as error
        returnJson = "invalid json format No JSON object could be decoded"
      }
      case e: FileNotFoundException => println(s"File not found: $filePath")
      case e: NumberFormatException => println("Error parsing number from the file.")
      case e: Exception => println(s"An unexpected error occurred: ${e.getMessage}")
    }

    // return result string
    returnJson
  }
}
