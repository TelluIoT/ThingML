/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.xtext.validation;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.thingml.xtext.helpers.ConfigurationHelper;
import org.thingml.xtext.thingML.Configuration;
import org.thingml.xtext.thingML.Connector;
import org.thingml.xtext.thingML.Instance;
import org.thingml.xtext.thingML.State;
import org.thingml.xtext.thingML.Transition;

/**
 *
 * @author sintef
 */
public class Tarjan<T extends EObject> {
    int index;
    List<Annotated<T>> Stack;
    Set<Annotated<T>> vertices;
    Configuration cfg;
    List<List<T>> SCComponents;
    public Tarjan(Configuration cfg, Set<T> vertices) {
        this.cfg = cfg;
        this.vertices = new HashSet<Annotated<T>>();
        for (T el : vertices) {
            Annotated<T> Ael = new Annotated<T>(el);
            this.vertices.add(Ael);
        }
        index = 0;
        Stack = new LinkedList<Annotated<T>>();
        SCComponents = new LinkedList<List<T>>();
    }

    public Annotated<T> findElement(T el) {
        for (Annotated<T> Ael : vertices) {
            if (EcoreUtil.equals(Ael.el, el)) {
                return Ael;
            }
        }
        return null;
    }

    public List<Annotated<T>> findChildren(T el) {
        List<Annotated<T>> res = new LinkedList<Annotated<T>>();
        if (el instanceof Instance) {
            for (Connector co : ConfigurationHelper.allConnectors(cfg)) {
                if (EcoreUtil.equals(co.getCli(), el)) {
                    res.add(findElement((T) co.getSrv()));
                }
            }
        } else {
            if (el instanceof State) {
                State s = (State) el;
                for (Transition tr : s.getOutgoing()) {
                    if (tr.getEvent() == null) {
                        if (tr.getGuard() == null) {
                            res.add(findElement((T) tr.getTarget()));
                        }
                    }
                }
            }
        }
        return res;
    }

    public void StrongConnect(Annotated<T> v) {
        v.id = index;
        v.lowlink = index;
        v.isVisited = true;
        index++;
        Stack.add(0, v);

        for (Annotated<T> w : findChildren(v.el)) {

            if (!w.isVisited) {
                StrongConnect(w);
                v.lowlink = Math.min(v.lowlink, w.lowlink);
            } else {
                if (Stack.contains(w)) {
                    v.lowlink = Math.min(v.lowlink, w.id);
                }
            }
        }

        if (v.id == v.lowlink) {
            List<T> res = new LinkedList<T>();
            Annotated<T> w;

            do {
                w = Stack.get(0);
                res.add(w.el);
                Stack.remove(0);
            } while (!w.equals(v));
            SCComponents.add(res);
        }
    }

    public List<List<T>> findStronglyConnectedComponents() {
        for (Annotated<T> v : vertices) {
            if (!v.isVisited) {
                StrongConnect(v);
            }
        }

        return SCComponents;
    }

    private class Annotated<T> {
        public T el;
        public int id, lowlink;
        public boolean isVisited;

        public Annotated(T el) {
            this.el = el;
            this.isVisited = false;
        }

        boolean equals(Annotated<T> other) {
            return EcoreUtil.equals( (EObject)this.el, (EObject)other.el);
        }

    }
}
