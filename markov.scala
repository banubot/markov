/* Hannah Parraga
 * A program which reads an text file specified as program
 * argument or user input and prints a randomly generated 
 * text based on combinations of words from the file and 
 * the words which follow them 
 */

import scala.io.Source
import scala.io.StdIn
import scala.collection.mutable.Map
import scala.collection.mutable.ArrayBuffer
import scala.util.Random

//get the filename and open
val src = Source.fromFile({
  if (args.length < 1) {
    StdIn.readLine("Please enter a file name: ")
  } else {
    args(0)
  }
})

//get how many words to generate at most
val maxOutLen = {
  if (args.length < 2) {
    StdIn.readLine("Please enter maximum number of words to output: ").toInt
  } else {
    args(1).toInt
  }
}

//create a map of two words to the third word which follow 
//in the text. For example "This is a beautiful story"
//Maps: This is -> a, is a -> beautiful, a beautiful -> story
//When a pairing of words appears more than once, the following 
//word is added to an ArrayBuffer for that pair
val mappy = Map[String, ArrayBuffer[String]]()
var prefix1 = ""
var prefix2 = ""
//split the file into individual words
src.getLines foreach (line => 
    line.split("\\s+") foreach (word => {
      val prefixes: String = prefix1 + " " + prefix2
      if (mappy.contains(prefixes)) {
        //this pair of words seen before
        mappy(prefixes) += word
      } else {
        mappy += (prefixes -> ArrayBuffer(word))  
      } 
      prefix1 = prefix2
      prefix2 = word
    }))

src.close

val rando: Random = new Random()
var outStr: String = ""
prefix1 = ""
prefix2 = ""
var i = 0
//Generate a string by starting from the beginning 
//word of the file but choosing the next word randomly from
//the ArrayBuffer for that word pair, then find this new 
//pair in the map until there are no more pairs that can be made 
//or the max number of words specified by the user have been 
//chosen
while (i < maxOutLen && 
  mappy.contains(prefix1 + " " + prefix2)) {
  val suffixes: ArrayBuffer[String] = mappy(prefix1 + " " + prefix2) 
  val randIndex = rando.nextInt(suffixes.length)
  val suffix: String = suffixes(randIndex)  
  outStr = outStr + " " + suffix
  //insert newline after ten words so output isn't all on one line
  if (i != 0 && i % 10 == 0) outStr = outStr + "\n"
  prefix1 = prefix2
  prefix2 = suffix
  i += 1
}

println(outStr)
