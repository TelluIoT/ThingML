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
package JavaMonitor;


import javax.swing.SwingUtilities;

public class TronMonitor {
	public static TronFrame fenetre;
	
	public static void main(String[] args){
		
		fenetre = new TronFrame();
		fenetre.setVisible(true);
		fenetre.paintSquare(0, 0, 0);
		fenetre.paintSquare(1, 0, 1);
		fenetre.paintSquare(0, 1, 2);
		fenetre.paintSquare(1, 1, 0);
		
	}
	
	public TronMonitor() {
		fenetre = new TronFrame();
		fenetre.setVisible(true);
	}
	
	public void paintSquare(int x, int y, int id) {
		fenetre.paintSquare(x, y, id);
		fenetre.repaint();
	}
	
}