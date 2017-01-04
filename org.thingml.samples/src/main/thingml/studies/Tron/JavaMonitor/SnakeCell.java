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

import java.awt.Point;

public class SnakeCell {
	public Point C;
	public int id;
	
	public SnakeCell(int x, int y, int id) {
		this.C = new Point(x,y);
		this.id = id;
	}
	
	public Point getC() {
		return C;
	}
	public void setC(Point c) {
		C = c;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	

}
