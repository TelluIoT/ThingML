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
package org.sintef.thingml;

import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeNode;
import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URI;
import java.util.Arrays;

/**
 * A simple file manager, that can turn a directory tree into a tree of nodes,
 * suitable to be used by a <code>JTree</code>.
 */
public class SimpleFileManager {


    /**
     * How deep to descend into a directory tree at most. This is a safety
     * feature, guarding against loops created by symbolic links as well as
     * a performance tweak.
     */
    public static final int MAXDEPTH = 10;

    /**
     * The root directory to which all pathes are relative
     */
    private File root;

    /**
     * Cache the directory tree
     */
    private DefaultMutableTreeNode dirCache;

    /**
     * A thread, that monitors the directory tree for changes.
     */
    private Thread monitorThread;

    /**
     * The <code>FileMonitor</code> of the
     * <code>monitorThread</code>
     */
    private FileMonitor fileMonitor;

    /**
     * The <code>FileFilter</code>, stating which files will be included in the
     * tree.
     */
    private FileFilter filter;


    /**
     * Construct a new file manager
     * @param root The toplevel directory of the directory tree to manage
     * @param filter a <code>FileFilter</code> for filtering the directory tree.
     * @exception IOException if root is inaccessible.
     */
    public SimpleFileManager(File root, FileFilter filter) throws IOException {
        if (root == null) throw new NullPointerException();
        this.filter = filter;
        if (!root.exists() || !root.canRead()) {
            throw new IOException(root.getAbsolutePath());
        }
        this.root = root;
    }

    /**
     * Query the root of this manager
     */
    public File getRoot() {
        return root;
    }

    /**
     * Construct a tree of nodes, that represents the currently
     * being monitored directory tree and can directly be used by
     * a <code>JTree</code>.
     * @return the top node of the tree or null if construction failed
     * for whatever reason.
     */
    public synchronized DefaultMutableTreeNode getDirectoryTree() {
        if (dirCache == null) {
            dirCache = descend(root, MAXDEPTH);
        }
        return dirCache;
    }

    /**
     * Causes the filemanager to throw away all internal caches and rescan
     * it's directory tree.
     */
    public synchronized void refresh() {
        dirCache = null;
    }

    /**
     * Helper function to recursively travel through the directory tree
     * @param dir the directory to descend into
     * @param depth depth counter. Will bail out, when this reaches 0.
     */
    private DefaultMutableTreeNode descend(File dir, int depth) {
        DefaultMutableTreeNode ret = new DefaultMutableTreeNode(dir.getName());
        File[] lst = dir.listFiles();
        try {
            Arrays.sort(lst);
        } catch (Exception e) {
        }
        for (int i = 0; i < lst.length; i++) {
            if (depth > 0 && lst[i].canRead()) {
                if (lst[i].isDirectory()) {
                    DefaultMutableTreeNode tmp = descend(lst[i], depth - 1);
                    if (tmp != null) {
                        ret.add(tmp);
                    }
                } else {
                    if (filter.accept(lst[i])) {
                        ret.add(new DefaultMutableTreeNode(lst[i].getName()));
                    }
                }
            }
        }
        if (ret.getChildCount() == 0) {
            ret = null;
        }
        return ret;
    }

    /**
     * Stop monitoring the directory tree.
     */
    public void stopMonitoring() {
        try {
            monitorThread.interrupt();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Start directory monitoring
     */
    public void startMonitoring() {
        fileMonitor = new FileMonitor(root, this);
        monitorThread = new Thread(fileMonitor);
        monitorThread.start();
    }

    /**
     * Query the monitor thread of this file manager
     * @return the thread, that is responsible for keeping
     * the filemanager in sync with the directory tree. Or null
     * if we are not monitoring currently.
     */
    public FileMonitor getFileMonitor() {
        return fileMonitor;
    }

    /**
     * Get the URI for a node
     * @param node a node created by this manager
     * @return the URI of the file, corresponding to the node, relative to
     * the root directory, this manager manages.
     */
    public URI getRelativePath(DefaultMutableTreeNode node) {
        File f = root.getParentFile();
        TreeNode[] tmp = node.getPath();
        for (int i = 0; i < tmp.length; i++) {
            f = new File(f, tmp[i].toString());
        }
        URI base = root.toURI();
        return base.relativize(f.toURI());
    }

    /**
     * Find a file
     * @param u a relative URI (relative to this managers root directory).
     * @return the corresponsing file.
     */
    public File getFile(URI u) {
        File f = new File(root.toURI().resolve(u));
        // NOTE: URI.resolve() kills things like "../../..", so we should be safe
        // from a malicious scene hopping around in the file system.
        return f;
    }

    /**
     * Break a relative URI into components
     * @param u An URI as produced by <code>getRelativePath()</code>
     * @return An array of path components, including the root directory
     * as the first one.
     */
    public String[] getComponents(URI u) {
        if (u == null) return new String[0];
        String tmp[] = u.getPath().split("/");
        String ret[] = new String[tmp.length + 1];
        ret[0] = root.getName();
        System.arraycopy(tmp, 0, ret, 1, tmp.length);
        return ret;
    }

}