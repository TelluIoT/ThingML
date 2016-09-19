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
