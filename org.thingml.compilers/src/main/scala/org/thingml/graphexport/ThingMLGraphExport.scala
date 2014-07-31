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
package org.thingml.graphexport

import org.sintef.thingml._
import scala.collection.JavaConversions._
import org.thingml.model.scalaimpl.ThingMLScalaImpl._
import org.thingml.graphexport.ThingMLGraphExport._
import java.lang.StringBuilder
import java.util.Hashtable
import javax.xml.transform.Result

/**
 * Created by IntelliJ IDEA.
 * User: ffl
 * Date: 04.07.11
 * Time: 12:59
 * To change this template use File | Settings | File Templates.
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

  def nodeFullText ( cr : String = "\\n" )= {
    var result = new StringBuilder
    result.append(nodeText)
    if (self.isInstanceOf[CompositeState]) result.append("(" + self.asInstanceOf[CompositeState].getSubstate.size() + ")")
    if (self.getEntry != null) result.append("{i}")
    if (self.getExit != null) result.append("{o}")
    if (self.isInstanceOf[CompositeState]) result.append(" (" + self.asInstanceOf[CompositeState].getSubstate.size() + ")")
    self.getProperties.foreach { p => result.append(cr + p.getName + " : " + p.getType.getName) }
    self.getInternal.foreach { i => result.append(cr + i.edgeText) }
    result.toString
  }

  def graphviz(result : StringBuilder) = {
    result.append("\t" + nodeID + " [label=\"" + nodeFullText() + "\", shape = ellipse];\n")
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

  def allGraphML(model : ThingMLModel ) = {
    var result : Hashtable[String, String] = new java.util.Hashtable[String, String]()
    model.allThings.foreach{ t => t.allStateMachines.foreach { sm =>
      result.put(sm.qname("_"), graphml(sm).toString)
    }}
    result
  }

  def graphml( sm : StateMachine ) : scala.xml.Elem = {
    var result =
    <graphml xmlns="http://graphml.graphdrawing.org/xmlns" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:y="http://www.yworks.com/xml/graphml" xmlns:yed="http://www.yworks.com/xml/yed/3" xsi:schemaLocation="http://graphml.graphdrawing.org/xmlns http://www.yworks.com/xml/schema/graphml/1.1/ygraphml.xsd">
      <key for="node" id="d6" yfiles.type="nodegraphics"/>
      <key for="edge" id="d10" yfiles.type="edgegraphics"/>
      <graph edgedefault="directed" id="G">
          {
            val nodes : scala.collection.mutable.ArrayBuffer[scala.xml.Elem] = new scala.collection.mutable.ArrayBuffer[scala.xml.Elem]
            if (sm.getRegion.size > 0) {
              sm.getRegion.foreach{ r =>
                nodes.add(graphmlParallelRegion(r))
              }
            }
            else {
               sm.getSubstate.foreach{ s => nodes.add(graphmlstate(s))
                 s.getOutgoing.foreach{ t =>
                 nodes.add(graphmlTransition(t))
               }}
            }
            nodes.add(graphmlInitState(sm))
            nodes.add(graphmlInitTransition(sm))
            nodes
          }
        </graph>
    </graphml>
    result
  }

  def graphmlCompositeState( cs : CompositeState ) : scala.xml.Elem = {
    var result =

      <node id={ cs.nodeID } yfiles.foldertype="group">
        <data key="d6">
          <y:ProxyAutoBoundsNode>
            <y:Realizers active="0">
              <y:GroupNode>
                <y:Fill color="#FFFFFF" transparent="false"/>
                <y:BorderStyle color="#000000" type="line" width="1.0"/>
                <y:NodeLabel alignment="center" autoSizePolicy="node_width" backgroundColor="#FFCC66" borderDistance="0.0" fontFamily="Dialog" fontSize="15" fontStyle="plain" hasLineColor="false" modelName="internal" modelPosition="t" textColor="#000000" visible="true">{ cs.nodeFullText("\n") }</y:NodeLabel>
                <y:Shape type="roundrectangle"/>
                <y:NodeBounds considerNodeLabelSize="true"/>
                <y:Insets bottom="15" bottomF="15.0" left="15" leftF="15.0" right="15" rightF="15.0" top="15" topF="15.0"/>
                <y:BorderInsets bottom="1" bottomF="1" left="15" leftF="15.0" right="15" rightF="15.0" top="1" topF="1"/>
              </y:GroupNode>
            </y:Realizers>
          </y:ProxyAutoBoundsNode>
        </data>
        <graph edgedefault="directed" id={ cs.qname(null) + "_Graph" }>
          {
            val nodes : scala.collection.mutable.ArrayBuffer[scala.xml.Elem] = new scala.collection.mutable.ArrayBuffer[scala.xml.Elem]
            if (cs.getRegion.size > 0) {
              cs.getRegion.foreach{ r =>
                nodes.add(graphmlParallelRegion(r))
              }
            }
            else {
               cs.getSubstate.foreach{ s => nodes.add(graphmlstate(s))
                 s.getOutgoing.foreach{ t =>
                 nodes.add(graphmlTransition(t))
               }}
            }
            nodes.add(graphmlInitState(cs))
            nodes.add(graphmlInitTransition(cs))
            nodes
          }
        </graph>
      </node>
    result
  }

  def graphmlParallelRegion( pr : Region ) : scala.xml.Elem = {
     var result : scala.xml.Elem =
        <node id={ pr.qname(null) } yfiles.foldertype="group">
          <data key="d6">
            <y:ProxyAutoBoundsNode>
              <y:Realizers active="0">
                <y:GroupNode>
                  <y:Fill hasColor="false" transparent="false"/>
                  <y:BorderStyle color="#000000" type="dashed" width="1.0"/>
                  <y:NodeLabel alignment="center" autoSizePolicy="node_width" borderDistance="0.0" fontFamily="Dialog" fontSize="15" fontStyle="plain" hasBackgroundColor="false" hasLineColor="false" modelName="internal" modelPosition="t" textColor="#000000" visible="true">{ pr.qname(null) }</y:NodeLabel>
                  <y:Shape type="roundrectangle"/>
                  <y:State closed="false" innerGraphDisplayEnabled="false"/>
                  <y:Insets bottom="15" bottomF="15.0" left="15" leftF="15.0" right="15" rightF="15.0" top="15" topF="15.0"/>
                  <y:BorderInsets bottom="1" bottomF="1" left="0" leftF="0.0" right="0" rightF="0.0" top="1" topF="1"/>
                </y:GroupNode>
              </y:Realizers>
            </y:ProxyAutoBoundsNode>
          </data>
          <graph edgedefault="directed" id={ pr.qname(null) + "_Graph" }>
         {
            val nodes : scala.collection.mutable.ArrayBuffer[scala.xml.Elem] = new scala.collection.mutable.ArrayBuffer[scala.xml.Elem]
            pr.getSubstate.foreach{
              s => nodes.add(graphmlstate(s))
              s.getOutgoing.foreach{ t =>
                nodes.add(graphmlTransition(t))
              }
            }
            nodes.add(graphmlInitState(pr))
            nodes.add(graphmlInitTransition(pr))
            nodes
         }
        </graph>
        </node>
    result
  }

  def graphmlstate( s : State ) : scala.xml.Elem = {

    if (s.isInstanceOf[CompositeState]) graphmlCompositeState(s.asInstanceOf[CompositeState])
    else {
      var result  =
        <node id={ s.nodeID }>
        <data key="d6">
          <y:ShapeNode>
            <y:Geometry height="70.0" width="200.0"/>
            <y:Fill color="#FFCC66" transparent="false"/>
            <y:BorderStyle color="#000000" type="line" width="1.0"/>
            <y:NodeLabel alignment="center" autoSizePolicy="content" fontFamily="Dialog" fontSize="12" fontStyle="plain" hasBackgroundColor="false" hasLineColor="false" modelName="internal" modelPosition="c" textColor="#000000" visible="true">{ s.nodeFullText("\n") }</y:NodeLabel>
            <y:Shape type="roundrectangle"/>
          </y:ShapeNode>
        </data>
      </node>
      result
    }
  }

  def graphmlTransition( t : Transition ) : scala.xml.Elem = {
    var result =
     <edge id={ t.getName } source={ t.getSource.nodeID } target={ t.getTarget.nodeID }>
      <data key="d10">
        <y:PolyLineEdge>
          <y:LineStyle color="#000000" type="line" width="1.0"/>
          <y:Arrows source="none" target="standard"/>
          <y:EdgeLabel alignment="center" distance="2.0" fontFamily="Dialog" fontSize="12" fontStyle="plain" hasBackgroundColor="false" hasLineColor="false" modelName="side_slider" preferredPlacement="right" ratio="0.0" textColor="#000000" visible="true">{ t.edgeText }</y:EdgeLabel>
        </y:PolyLineEdge>
      </data>
    </edge>
    result
  }

  def graphmlInitState( r : Region )  : scala.xml.Elem = {
    var result =
    <node id={ r.qname(null) + "_INIT" }>
      <data key="d6">
        <y:ShapeNode>
          <y:Fill color="#000000" transparent="false"/>
          <y:BorderStyle color="#000000" type="line" width="1.0"/>
          <y:NodeLabel alignment="center" autoSizePolicy="content" fontFamily="Dialog" fontSize="12" fontStyle="bold" hasBackgroundColor="false" hasLineColor="false" modelName="internal" modelPosition="c" textColor="#FFFFFF" visible="true">{ if (r.isHistory) "H" else "I" }</y:NodeLabel>
          <y:Shape type="ellipse"/>
        </y:ShapeNode>
      </data>
    </node>
    result
  }

  def graphmlInitTransition( r : Region ) : scala.xml.Elem = {
    var result =
    <edge id={ r.qname(null) + "_INITT" } source={ r.qname(null) + "_INIT" } target={ r.getInitial.nodeID }>
      <data key="d10">
        <y:PolyLineEdge>
          <y:LineStyle color="#000000" type="line" width="1.0"/>
          <y:Arrows source="none" target="standard"/>
        </y:PolyLineEdge>
      </data>
    </edge>
    result
  }
}