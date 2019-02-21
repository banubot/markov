import scala.io.Source
import scala.io.StdIn
import scala.collection.mutable.Map
import scala.collection.mutable.ArrayBuffer

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

//create markov chain map from all words in file 
val mappy = Map[ArrayBuffer[String], ArrayBuffer[String]]()
var prefix1 = ""
var prefix2 = ""
src.getLines foreach (line => 
    line.split("\\s+") foreach (word => {
      val prefixes: ArrayBuffer[String] = ArrayBuffer(prefix1, prefix2)
      if (mappy.contains(prefixes)) {
        mappy(prefixes).append(word)
      } else {
        mappy += (prefixes -> ArrayBuffer(word))  
      } 
      prefix1 = prefix2
      prefix2 = word
    }))

//TODO generate rando output
println(mappy)
src.close
