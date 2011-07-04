/**
 * Copyright (C) 2011 SINTEF <franck.fleurey@sintef.no>
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
package org.thingml.graphexport

import org.sintef.thingml._
import scala.collection.JavaConversions._
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import org.thingml.graphexport.ThingMLGraphExport._
import java.lang.StringBuilder
import java.util.Hashtable

/**
 * Created by IntelliJ IDEA.
 * User: ffl
 * Date: 04.07.11
 * Time: 12:59
 * To change this template use File | Settings | File Templates.
 */


/*
digraph finite_state_machine {
  rankdir=LR;
	INIT_STATE [shape=circle label = "", style = filled, color = "black"];
  S1 [label="SimpleState{>}{<}\n?Evt1\n?Evt2{}", shape = ellipse];
  S2 [label="CompositeSate (3)\np1:String\n?Evt3", shape = ellipse]
  INIT_STATE -> S1 [label="init"]
  S1 -> S2 [label="transition1\n?Evt23()"]
  S2 -> S1 [label="myTransition\n?message78(){}"]
  S2 -> S2 [label="t\n?tick(){}"]
}
 */




case class RegionGraphExport(self : Region) {

  def graphviz() = {
   var result : StringBuilder = new StringBuilder
    result.append("digraph finite_state_machine {\n")
    result.append("\trankdir=LR;\n")
    result.append("\tINIT_STATE [shape=circle label = \"\", style = filled, color = \"black\"];\n")
    result.append("\t\tINIT_STATE -> "+ self.getInitial.nodeID +" [label=\"init\"];\n")
    self.getSubstate.foreach { s => s.graphviz(result) }
    result.append("}")
    result.toString
  }
}

case class StateGraphExport(self : State) {

  def nodeID = { self.getName }
  def nodeText  = { self.getName }

  def graphviz(result : StringBuilder) = {
    result.append("\t" + nodeID + " [label=\"" + nodeText)
    if (self.isInstanceOf[CompositeState]) result.append("(" + self.asInstanceOf[CompositeState].getSubstate.size() + ")")
    if (self.getEntry != null) result.append("{i}")
    if (self.getExit != null) result.append("{o}")
    if (self.isInstanceOf[CompositeState]) result.append(" (" + self.asInstanceOf[CompositeState].getSubstate.size() + ")")
    self.getProperties.foreach { p => result.append("\\n" + p.getName + " : " + p.getType.getName) }
    self.getInternal.foreach { i => result.append("\\n" + i.edgeText) }
    result.append("\", shape = ellipse];\n")
    self.getOutgoing.foreach{ t=>
      result.append{"\t\t" + t.getSource.nodeID + " -> " + t.getTarget.nodeID + " [label=\"" + t.edgeText + "\"];\n"}
    }
  }
}

case class HandlerGraphExport(self : Handler) {
  def edgeText = {
    var result : StringBuilder = new StringBuilder
    if (self.getName != null && self.getName.length() > 0)
      result.append(self.getName+":")
    self.getEvent.foreach{ e =>
      if (e.isInstanceOf[ReceiveMessage])
        result.append(e.asInstanceOf[ReceiveMessage].getPort.getName + "?" + e.asInstanceOf[ReceiveMessage].getMessage.getName)
    }
    if (self.getGuard != null) result.append("(c)")
    if (self.getAction != null) result.append("{a}")
    result.toString
  }
}

object ThingMLGraphExport {
  implicit def graphExportAspect(self:Region) : RegionGraphExport = RegionGraphExport(self)
  implicit def graphExportAspect(self:State) : StateGraphExport = StateGraphExport(self)
  implicit def graphExportAspect(self:Handler):  HandlerGraphExport = HandlerGraphExport(self)

  def graphviz( region : Region ) = {
    region.graphviz
  }

  def allGraphviz(model : ThingMLModel ) = {
    var result : Hashtable[String, String] = new java.util.Hashtable[String, String]()
    model.allThings.foreach{ t => t.allStateMachines.foreach { tr => tr.allContainedRegions().foreach{ r =>
      result.put(r.qname("_"), r.graphviz())
    }}}
    result
  }
}