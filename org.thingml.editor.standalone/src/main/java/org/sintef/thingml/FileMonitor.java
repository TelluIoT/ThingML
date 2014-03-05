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
package org.sintef.thingml;

import java.io.*;
import java.util.*;
import java.awt.*;
import java.lang.reflect.*;

/**
 * A very simple file monitor, that will monitor a directory tree and trigger
 * actions, when it detects changes. Only directory trees, that actually
 * contain files matching a filter criteria are included.
 */
public class FileMonitor implements Runnable {

  private File root;
  private SimpleFileManager simpleFileManager;
  private Vector<Runnable>clients = new Vector<Runnable>();

  /**
   * Construct a new monitor, monitoring a specific directory tree
   * @param root root of the directory tree to monitor
   * @param smf the <code>SimpleFileManager</code> to notify
   */
  public FileMonitor(File root, SimpleFileManager smf) {
    this.root=root;
    simpleFileManager = smf;
  }

  /**
   * Descend recursively into a directory tree and return the most recent
   * modifaction date.
   * @param dir the directory to descend into
   * @returnThe most recent timestamp found.
   */
  private long descend(File dir, int depth) {
    File[] lst =  dir.listFiles();
    long lastMod = dir.lastModified();
    long test= lastMod;

    for (int i=0;i<lst.length;i++) {
      if (depth>0 &&lst[i].canRead()) {
        if (lst[i].isDirectory()) {
          test = descend(lst[i],depth-1);
        }
        else {
          test = lst[i].lastModified();
        }
      }
      if (test>lastMod) lastMod=test;
    }
    return lastMod;
  }

  /**
   * Add a client to the notification list
   * @param r a Runnable, that will be submitted to
   * <code>EventQueue.invokeAndWait()</code> once a change in the
   * directory tree is detected.
   */
  public synchronized void addClient(Runnable r) {
    clients.add(r);
  }

  // Interface implementation: Runnable

  /** {inheritdoc} */
  public void run() {
    long lastMod=0;
    while(true) {
      try {
        long tmp = descend(root,SimpleFileManager.MAXDEPTH);
        if (tmp>lastMod) {
          simpleFileManager.refresh();
          Runnable[] r = clients.toArray(new Runnable[0]);
          for (int i=0;i<r.length;i++) {
            EventQueue.invokeAndWait(r[i]);
          }
          lastMod=tmp;
        }
        Thread.sleep(1000);
      }
      catch (InterruptedException e) {
        // Goodbye
        return;
      }
      catch (InvocationTargetException e) {
        //?!
        return;
      }
    }
  }

}