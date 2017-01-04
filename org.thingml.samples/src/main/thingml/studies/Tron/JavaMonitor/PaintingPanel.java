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
package JavaMonitor;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JPanel;

public class PaintingPanel extends JPanel{
	
	private ArrayList<SnakeCell> Snakes;
	private Color[] colors = new Color[3];
	public int victory;
	
	public PaintingPanel () {
		super();
		victory = 256;
		colors[0] = new Color(255, 0, 0);
		colors[1] = new Color(0, 0, 255);
		colors[2] = new Color(0, 255, 0);
		Snakes = new ArrayList<SnakeCell>();
	}
	
	public void addCell(int x, int y, int id) {
		this.Snakes.add(new SnakeCell(x,y,id));
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		
		g.setColor(Color.black);
		g.fillRect(0, 0, 424, 524+30);
		g.setColor(Color.white);
		g.drawRect(1, 1, 421, 521);
		
		for (SnakeCell snakeCell : Snakes) {
			g.setColor(colors[snakeCell.id]);
			g.fillRect(snakeCell.getC().x * 10 + 2, snakeCell.getC().y * 10 + 2, 9, 9);
		}
		
		//Qui est en vie?
		g.setColor(colors[0]);
		g.fillRect(3 * 10 + 2, 53 * 10 + 2, 9, 9);
		g.setColor(Color.white);
		g.drawString("MOD", 3 * 10 + 2 + 12, 53 * 10 + 2 + 9);

		g.setColor(colors[1]);
		g.fillRect(20 * 10 + 2, 53 * 10 + 2, 9, 9);
		g.setColor(Color.white);
		g.drawString("HCI", 20 * 10 + 2 + 12, 53 * 10 + 2 + 9);
		
		g.setColor(colors[2]);
		g.fillRect(35 * 10 + 2, 53 * 10 + 2, 9, 9);
		g.setColor(Color.white);
		g.drawString("RST", 35 * 10 + 2 + 12, 53 * 10 + 2 + 9);
		
		if(victory != 256) {
			g.setColor(Color.black);
			g.fillRect(0, 0, 424, 524+30);
			g.setColor(Color.white);
			g.drawRect(1, 1, 421, 521);
			if(victory == 0)
				g.drawString("MOD WON AS EXPECTED", 15 * 10 + 2 + 12, 25 * 10 + 2 + 9);
			if(victory == 1)
				g.drawString("HCI WON EVEN IF MOD PLAYED BETTER", 5 * 10 + 2 + 12, 25 * 10 + 2 + 9);
			if(victory == 2)
				g.drawString("RST WON EVEN IF MOD PLAYED BETTER", 5 * 10 + 2 + 12, 25 * 10 + 2 + 9);
		}
	}
	
	
	

}
