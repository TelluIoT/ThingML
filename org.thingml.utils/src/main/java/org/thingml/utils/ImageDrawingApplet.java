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
package org.thingml.utils;
import java.io.*;
import java.net.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

class ImageDrawingComponent extends Component {

    private BufferedImage bi;
    int w, h;

    public ImageDrawingComponent(URL imageSrc) {
        try {
            bi = ImageIO.read(imageSrc);
            w = bi.getWidth(null);
            h = bi.getHeight(null);
            if (bi.getType() != BufferedImage.TYPE_INT_RGB) {
                BufferedImage bi2 =
                        new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
                Graphics big = bi2.getGraphics();
                big.drawImage(bi, 0, 0, null);
                bi = bi2;
            }
        } catch (IOException e) {
            System.out.println("Image could not be read");
            System.exit(1);
        }
    }

    public Dimension getPreferredSize() {
        return new Dimension(w, h);
    }
    public void paint(Graphics g) {
        g.drawImage(bi, 0, 0, null);
    }
}

public class ImageDrawingApplet extends JApplet {
    String imageFileName;
    private URL imageSrc;
    public ImageDrawingApplet(URL imageSrc) {
        this.imageSrc = imageSrc;
    }

    public void buildUI() {
        final ImageDrawingComponent id = new ImageDrawingComponent(imageSrc);
        JScrollPane jsp = new JScrollPane(id);
        add(jsp);
        id.repaint();
    }

    public static void main(String s[]) {
        popupImage("bld.jpg");
    }

    public static void popupImage(String filename) {
        JFrame f = new JFrame("ImageDrawing");
        f.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        
        URL imageSrc = null;
        try {
            imageSrc = ((new File(filename)).toURI()).toURL();
            System.out.println("__"+filename);
        } catch (MalformedURLException e) {
        }
        ImageDrawingApplet id = new ImageDrawingApplet(imageSrc);
        id.buildUI();

       f.add("Center", id);
        f.pack();
        f.setVisible(true);
    }
}