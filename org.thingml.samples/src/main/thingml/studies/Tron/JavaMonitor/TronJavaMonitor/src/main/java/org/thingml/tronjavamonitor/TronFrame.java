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
package org.thingml.tronjavamonitor;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JFrame;
import javax.swing.JPanel;
 
public class TronFrame extends JFrame{
 
	//public Graphics g;
	public PaintingPanel paintingPanel;
	
	public TronFrame(){
		super();
 
		build();//On initialise notre fenêtre
	}
 
	private void build(){
		setTitle("TronFrame"); //On donne un titre à l'application
		setSize(424+4,524+30+15); //On donne une taille à notre fenêtre
		setLocationRelativeTo(null); //On centre la fenêtre sur l'écran
		//setResizable(false); //On interdit la redimensionnement de la fenêtre
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //On dit à l'application de se fermer lors du clic sur la croix
		
		paintingPanel = new PaintingPanel();
		

		
		this.add(paintingPanel);
		
		//paintingPanel.
		//this.g = this.getGraphics();
		//g.setColor(new Color(255, 0, 0));
		//g.drawRect(10, 10, 100, 100);
		//this.repaint(0, 0, 200, 200);
		

		//g.setColor(Color.white);
		//g.fillRect(1, 1, 10, 10);
	}

	public void paintSquare(int x, int y, int id) {
		paintingPanel.addCell(x, y, id);
	}
}
