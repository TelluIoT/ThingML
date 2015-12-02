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
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.thingml.compilers;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.sintef.thingml.Configuration;
import org.sintef.thingml.Connector;
import org.sintef.thingml.ExternalConnector;
import org.sintef.thingml.Instance;
import org.sintef.thingml.Port;
import org.sintef.thingml.Thing;
import org.sintef.thingml.ThingMLElement;

/**
 *
 * @author sintef
 */
abstract public class Checker {
    private Set<CheckerInfo> Errors;
    private Set<CheckerInfo> Warnings;
    private Set<CheckerInfo> Notices;
    
    private String compiler;
    private String generic;
    
    private Context ctx;
    
    public Checker (String compiler) {
        Errors = new HashSet<CheckerInfo>();
        Warnings = new HashSet<CheckerInfo>();
        Notices = new HashSet<CheckerInfo>();
        
        this.ctx = new Context(null);
        this.compiler = compiler;
        generic = "ThingML";
    }
    
    public void do_generic_check(Configuration cfg) {
        checkThingsUsage(cfg);
        checkPortsUsage(cfg);
        checkCycle(cfg);
    }
    
    // Must be implemented and must contain a call to do_generic_check(cfg)
    abstract public void do_check(Configuration cfg);
    
    
    // ---------------------- Accessors ----------------------
    
    public void addError(String msg, ThingMLElement el) {
        Errors.add(new CheckerInfo(InfoType.ERROR, compiler, msg, el));
    }
    
    private void addGenericError(String msg, ThingMLElement el) {
        Errors.add(new CheckerInfo(InfoType.ERROR, generic, msg, el));
    }
    
    public void addWarning(String msg, ThingMLElement el) {
        Warnings.add(new CheckerInfo(InfoType.WARNING, compiler, msg, el));
    }
    
    private void addGenericWarning(String msg, ThingMLElement el) {
        Warnings.add(new CheckerInfo(InfoType.WARNING, generic, msg, el));
    }
    
    public void addNotice(String msg, ThingMLElement el) {
        Notices.add(new CheckerInfo(InfoType.NOTICE, compiler, msg, el));
    }
    
    private void addGenericNotice(String msg, ThingMLElement el) {
        Notices.add(new CheckerInfo(InfoType.NOTICE, generic, msg, el));
    }
    
    public boolean containsErrors() {
        return Errors.isEmpty();
    }
    
    public boolean containsWarnings() {
        return Warnings.isEmpty();
    }
    
    public boolean containsNotices() {
        return Notices.isEmpty();
    }
    
    public void printErrors() {
        for(CheckerInfo i : Errors) {
            System.out.print(i.toString());
        }
    }
    
    public void printWarnings() {
        for(CheckerInfo i : Warnings) {
            System.out.print(i.toString());
        }
    }
    
    public void printNotices() {
        for(CheckerInfo i : Notices) {
            System.out.print(i.toString());
        }
    }
    
    // ---------------------- Generic checks ----------------------
    
    public void checkThingsUsage(Configuration cfg) {
        
        for(Thing t : cfg.findContainingModel().allThings()) {
            if(!t.isFragment()) {
                boolean found = false;
                for(Instance i : cfg.allInstances()) {
                    if(i.getType().equals(t)) {
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    this.addGenericNotice("Thing " + t.getName() + " is declared but never instanciated.", cfg);
                }
            }
        }
    }
    
    public void checkPortsUsage(Configuration cfg) {
        for(Map.Entry<Instance, List<Port>> entry: cfg.danglingPorts().entrySet()) {
            boolean found = false;
            for(Port p : entry.getValue()) {
                for(ExternalConnector eco : cfg.getExternalConnectors()) {
                    if(EcoreUtil.equals(eco.getInst().getInstance(), entry.getKey()) && EcoreUtil.equals(eco.getPort(), p)) {
                        found = true;
                        break;
                    }
                }
                if(!found) {
                    this.addGenericNotice("Port " + p.getName() + " is not connected.", entry.getKey());
                }
            }
        }
    }
    
    public void checkCycle(Configuration cfg) {
        Tarjan<Instance> t = new Tarjan(cfg, cfg.allInstances());
        List<List<Instance>> cycles = t.findStronglyConnectedComponents();
        
        for(List<Instance> cycle : cycles) {
            if(cycle != null) {
                if(cycle.size() != 1) {
                    String msg = "Dependancies cycle: (";
                    boolean first = true;
                    for(Instance j : cycle) {
                        if(first) {
                            first = false;
                        } else {
                            msg += ", ";
                        }
                        msg += j.getName();
                    }

                    this.addGenericNotice(msg + ")", cfg);
                } else {
                    //System.out.println("Mono state: " + cycle.get(0).getName());
                }
            }
        }
    }
    
    // ---------------------- Structures ----------------------
    
    public enum InfoType {ERROR, WARNING, NOTICE};
    
    public class CheckerInfo {
        public InfoType type;
        public String source;
        public String message;
        public ThingMLElement element;
        
        public CheckerInfo(InfoType type, String source, String message, ThingMLElement element) {
            this.type = type;
            this.source = source;
            this.message = message;
            this.element = element;
        }
        
        public String print(ThingMLElement el) {
            if(el.getName() != null) {
                return el.getName();
            } else {
                return "";
            }
        }
        
        public String toString() {
            String t;
            switch(type) {
                case NOTICE:
                    t = "NOTICE";
                    break;
                    
                case WARNING:
                    t = "WARNING";
                    break;
                    
                case ERROR:
                    t = "ERROR";
                    break;
                default:
                    t = "";
            }
            
            
            return "[" + t + "] " + source + ": " + message + " (in " + print(element) + ")\n";
        }
    }
    
    private class Tarjan<T extends ThingMLElement> {
        private class Annotated<T> {
            public T el;
            public int id, lowlink;
            public boolean isVisited;
            
            public Annotated(T el) {
                this.el = el;
                this.isVisited = false;
            }
            
            boolean equals(Annotated<T> other) {
                return EcoreUtil.equals((ThingMLElement) this.el, (ThingMLElement) other.el);
            }
            
        }
        
        int index;
        List<Annotated<T>> Stack;
        Set<Annotated<T>> vertices;
        Configuration cfg;
        List<List<T>> SCComponents;
        
        public Tarjan(Configuration cfg, Set<T> vertices) {
            this.cfg = cfg;
            this.vertices = new HashSet<Annotated<T>>();
            for(T el : vertices) {
                Annotated<T> Ael = new Annotated<T>(el);
                this.vertices.add(Ael);
            }
            index = 0;
            Stack = new LinkedList<Annotated<T>>();
            SCComponents = new LinkedList<List<T>>();
        }
        
        public Annotated<T> findElement(T el) {
            for(Annotated<T> Ael : vertices) {
                if(EcoreUtil.equals(Ael.el, el)) {
                    return Ael;
                }
            }
            return null;
        }
        
        public List<Annotated<T>> findChildren(T el) {
            List<Annotated<T>> res = new LinkedList<Annotated<T>>();
            if(el instanceof Instance) {
                for(Connector co : cfg.allConnectors()) {
                    if(EcoreUtil.equals(co.getCli().getInstance(), el)) {
                        res.add(findElement((T) co.getSrv().getInstance()));
                    }
                }
            } else {
                
            }
            return res;
        }
        
        public void StrongConnect(Annotated<T> v) {
            v.id = index;
            v.lowlink = index;
            v.isVisited = true;
            index++;
            Stack.add(0, v);
            
            for(Annotated<T> w : findChildren(v.el)) {
                    
                if(!w.isVisited) {
                    StrongConnect(w);
                    v.lowlink = Math.min(v.lowlink, w.lowlink);
                } else {
                    if(Stack.contains(w)) {
                        v.lowlink = Math.min(v.lowlink, w.id);
                    }
                }
            }
            
            if(v.id == v.lowlink) {
                List<T> res = new LinkedList<T>();
                Annotated<T> w;
                
                do {
                    w = Stack.get(0);
                    res.add(w.el);
                    Stack.remove(0);
                } while(!w.equals(v));
                SCComponents.add(res);
            }
        }
        
        public List<List<T>> findStronglyConnectedComponents() {
            for(Annotated<T> v : vertices) {
                if(!v.isVisited) {
                    StrongConnect(v);
                }
            }
            
            return SCComponents;
        }
    }
    
}
