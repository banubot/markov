import scala.io.Source
import scala.io.StdIn

def filename() = {
  if (args.length < 1) {
    StdIn.readLine("Please enter a file name: ")
  } else {
    args(0)
  }
}

val src = Source.fromFile(filename)

for (line <- src.getLines) {
  //TODO process line
  println(line)
}

src.close
