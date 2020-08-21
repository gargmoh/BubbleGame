package application;

import javafx.scene.paint.Color;

public class Circle {
	public Circle(){
		
	}
	public Circle(int x2, int y2, int d2,int index2) {
		x2=x;
		y2=y;
		d2=d;
		index2=index;
	}
	public int x;  // the top-left corner x coordinate
	public int y; //the top left corner y coordinate	
    public int d;//diameter
    public int yDirection =1;
    public int xDirection = -1;
    public Color color;
    public int index;
	
}
