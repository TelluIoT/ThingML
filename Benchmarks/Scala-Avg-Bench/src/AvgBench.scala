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
    builder ++= "TIME; %CPU; %MEM; #PID\n"

    var mapSum : Map[Int, Tuple3[Int,Float,Int]] = Map() // [Time, [CPU, MEM, nbPID] ]



    val itLines : Iterator[String] = Source.fromFile(sourceFile).getLines()
    itLines.next() //ignore column titles


    var lineSplit : Array[String] = itLines.next().split(";")
    var currentPID : String = lineSplit(0)
    var previousPID : String = lineSplit(0)
    var index : Int = 0

    while(itLines.hasNext && currentPID == previousPID) {
      mapSum += (index -> (lineSplit(1).trim.toInt,lineSplit(2).trim.toFloat,1))

      lineSplit = itLines.next().split(";")
      previousPID = currentPID
      currentPID = lineSplit(0)

      index = index + 1
    }

    var cpu = mapSum(0)._1 + lineSplit(1).trim().toInt
    var mem = mapSum(0)._2 + lineSplit(2).trim().toFloat
    mapSum += (0 -> (cpu,mem,2))
    previousPID = currentPID

    index = 0
    var nbPID : Int = 2

    var indexList = 0
    val bmin = 3400
    val bmax = 4000
    val pas = 10
    var histogram : ListBuffer[Int] = ListBuffer()

    var ii = 0
    for(ii <- 0 until (bmax - bmin) / pas) {
      histogram += 0
    }



    itLines.foreach {
      lines =>
        lineSplit = lines.split(";")
        currentPID = lineSplit(0)


        if (currentPID == previousPID) {
          index = index + 1
        } else {
         /* println(index + " " + bmin + " " + pas)
          println((index - bmin) / pas)*/
          indexList = (index - bmin) / pas
          histogram(indexList) = histogram(indexList) + 1

          index = 0
          nbPID = nbPID + 1
        }

        if (index < mapSum.size) {
          cpu = mapSum(index)._1 + lineSplit(1).trim().toInt
          mem = mapSum(index)._2 + lineSplit(2).trim().toFloat
          mapSum += (index -> (cpu,mem,mapSum(index)._3 + 1))
        } else {
          cpu = lineSplit(1).trim().toInt
          mem = lineSplit(2).trim().toFloat
          mapSum += (index -> (cpu,mem,1))
        }

        previousPID = currentPID
    }

    mapSum.foreach {
      case(i,tuple) =>
        builder ++= (i + 1) + ";" + ( tuple._1 / tuple._3) + ";" + ( tuple._2 / tuple._3).toString.replace('.',',') + ";" + tuple._3 + "\n"
    }

    println(histogram.mkString(";"))

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