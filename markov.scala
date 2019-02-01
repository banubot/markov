import scala.io.Source
import scala.io.StdIn
import scala.collection.mutable.Map

//get the filename and open
def filename() = {
  if (args.length < 1) {
    StdIn.readLine("Please enter a file name: ")
  } else {
    args(0)
  }
}
val src = Source.fromFile(filename)

var prefix1 = ""
var prefix2 = ""

/* create pairing of two words added together (prefix size 2) 
 * map with param
 * TODO word pair already exists in map
 */
def buildMap (word: String): (String, String) = {
  var mapPiece = ((prefix1 + " " + prefix2) -> word)
  prefix1 = prefix2
  prefix2 = word
  mapPiece
}

//create markov chain map from all words in file 
val mappy = Map[String, String]()
src.getLines foreach (line => 
    line.split("\\s+") foreach (word =>
        mappy += buildMap(word) )) 

println(mappy)
src.close
