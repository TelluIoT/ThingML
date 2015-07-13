/**
 * Copyright (C) 2014 SINTEF <franck.fleurey@sintef.no>
 *
 * Licensed under the GNU LESSER GENERAL PUBLIC LICENSE, Version 3, 29 June 2007;
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.gnu.org/licenses/lgpl-3.0.txt
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.nio.file.{Files, Paths}

import scala.collection.mutable.ListBuffer
import scala.io.Source
/**
 * @author ludovic
 */
object AvgBench {
  def process(sourceFile : String, destFile : String) {
    var builder : StringBuilder = new StringBuilder
    builder ++= "TIME; %CPU; %MEM\n"

    val sumCPU : ListBuffer[Int] = new ListBuffer[Int]
    val sumMem : ListBuffer[Float] = new ListBuffer[Float]

    val itLines : Iterator[String] = Source.fromFile(sourceFile).getLines()
    itLines.next() //ignore column titles

    var lineSplit : Array[String] = itLines.next().split(";")
    var currentPID : String = lineSplit(0)
    var previousPID : String = lineSplit(0)

    while(itLines.hasNext && currentPID == previousPID) {
      sumCPU += lineSplit(1).trim().toInt
      sumMem += lineSplit(2).trim().toFloat
      lineSplit = itLines.next().split(";")
      previousPID = currentPID
      currentPID = lineSplit(0)
    }

    sumCPU(0) = sumCPU.head + lineSplit(1).trim().toInt
    sumMem(0) = sumMem.head + lineSplit(2).trim.toFloat
    previousPID = currentPID
    var index : Int = 0
    var nbPID : Int = 2

    itLines.foreach {
      lines =>
        lineSplit = lines.split(";")
        currentPID = lineSplit(0)

        if(currentPID == previousPID) {
          index = index + 1
        } else {
          index = 0
          nbPID = nbPID + 1
        }

        if(index < sumCPU.size) {
          sumCPU(index) = sumCPU(index) + lineSplit(1).trim().toInt
          sumMem(index) = sumMem(index) + lineSplit(2).trim().toFloat
        }

        previousPID = currentPID
    }

    index = 1
    sumCPU.zip(sumMem.toList).foreach {
      case (sum, mem) =>
        builder ++= index + ";" + ( sum / nbPID) + ";" + ( mem / nbPID).toString.replace('.',',') + "\n"
        index = index + 1
    }

    Files.write(Paths.get(destFile),builder.mkString.getBytes)
    println("Done")
  }
  def main(args: Array[String]) {

    if(args.length != 2) {
      Console.err.println("Arguments : <sourceFile> <destFile>")
      Console.err.println("WARNING: <destFile> contents will be deleted")
    } else {
      process(args(0),args(1))
    }

  }

}