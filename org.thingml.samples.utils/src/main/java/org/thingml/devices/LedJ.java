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
package org.thingml.devices;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;

public class LedJ implements Device {
    //TODO chose color of LED

    private final JFrame frame = new JFrame();
    private LedComponent led;
    private boolean status = false;//false means off

    public LedJ() {
        try {
            led = new LedComponent();

        frame.getContentPane().add(led);
        frame.pack();
        frame.setSize(new Dimension(90, 90));
        frame.setVisible(true);
        frame.repaint();
        /*
         frame.location_=(new Point(Helper.randomX, Helper.randomY));
         */
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public void turnOn() {
        status = true;
        led.repaint();
    }

    public void turnOff() {
        status = false;
        led.repaint();
    }

    public void toggle() {
        status = !status;
        led.repaint();
    }

    public static void main(String args[]) {
        try {
            final LedJ led = new LedJ();
            led.toggle();
            System.out.println("tick");
            Thread.currentThread().sleep(1000);
            led.toggle();
            System.out.println("tick");
            Thread.currentThread().sleep(1000);
            led.toggle();
            System.out.println("tick");
            Thread.currentThread().sleep(1000);
            led.toggle();
            System.out.println("tick");
            Thread.currentThread().sleep(1000);
            led.toggle();
            System.out.println("tick");
            Thread.currentThread().sleep(1000);
            led.toggle();
            System.out.println("tick");
            Thread.currentThread().sleep(1000);
            led.toggle();
            System.out.println("tick");
            Thread.currentThread().sleep(1000);
            led.toggle();
            System.out.println("tick");
            Thread.currentThread().sleep(1000);
            led.toggle();
            System.out.println("tick");

        }  catch (InterruptedException ex) {
            Logger.getLogger(Led.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private class LedComponent extends JComponent {

        private BufferedImage imageOn;
        private BufferedImage imageOff;

        public LedComponent() throws IOException {
            try {

                imageOn = ImageIO.read(new File("src/main/resources/led/red_led_on_40px.png"));
                imageOff = ImageIO.read(new File("src/main/resources/led/red_led_off_40px.png"));
            } catch (IOException e)   {
                imageOn = ImageIO.read(new File("led/red_led_on_40px.png"));
                imageOff = ImageIO.read(new File("led/red_led_off_40px.png"));
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g); //To change body of generated methods, choose Tools | Templates.
            if (status) {
                g.drawImage(imageOn, 0, 0, null);
            } else {
                g.drawImage(imageOff, 0, 0, null);
            }
        }
    }

}
