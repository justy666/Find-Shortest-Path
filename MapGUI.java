/*
Author: Justin Shen
Student ID: 31819329
 */

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class MapGUI extends JPanel{
		
	public static ArrayList<Road> roads;
	public static HashMap<String, Intersection> intersectionMap;
	public static boolean thickLines = false;
	public static double minLat, minLong, maxLat, maxLong;
	public static double xScale, yScale;
	
	public MapGUI(ArrayList<Road> roads, HashMap<String, Intersection> interMap,double maximumLat, double minimumLat, double maximumLong, double minimumLong) {
		
		MapGUI.roads = roads;
		MapGUI.intersectionMap = interMap;	
		maxLat = maximumLat;
		minLat = minimumLat;
		maxLong = maximumLong;	
		minLong = minimumLong;
		setPreferredSize(new Dimension(800, 800));	
	}
	
	public void paintComponent(Graphics page) {
		Graphics2D page2 = (Graphics2D) page;
		super.paintComponent(page2);
		page2.setColor(Color.BLACK);
		if(thickLines) {
			page2.setStroke(new BasicStroke(3));
		}
		
		xScale = this.getWidth() / (maxLong - minLong);
		yScale = this.getHeight() / (maxLat - minLat);
		
		Intersection int1, int2;	
		double x1, x2, y1, y2;	
		for(Road r : roads) {
			scale();
			int1 = intersectionMap.get(r.intersect1);
			int2 = intersectionMap.get(r.intersect2);
			x1 = int1.lon;
			y1 = int1.lat;
			x2 = int2.lon;
			y2 = int2.lat;
		
			page2.draw(new Line2D.Double((x1-minLong) * xScale, getHeight() - ((y1 - minLat) * yScale), (x2-minLong) * xScale, getHeight() - ((y2 - minLat) * yScale)));
		}
		
		if(Map.dijkstraPath != null) {
			page2.setColor(Color.RED);
			for(int i = 0; i < Map.dijkstraPath.length - 1; i++) {
				
				x1 = Map.dijkstraPath[i].lon;
				y1 = Map.dijkstraPath[i].lat;
				x2 = Map.dijkstraPath[i+1].lon;
				y2 = Map.dijkstraPath[i+1].lat;
				page2.draw(new Line2D.Double((x1-minLong) * xScale, getHeight() - ((y1 - minLat) * yScale), (x2-minLong) * xScale, getHeight() - ((y2 - minLat) * yScale)));
			}		
		}
	
		if(Map.minWeightSpanTree != null) {
			for(Road r : Map.minWeightSpanTree) {
				page2.setColor(Color.BLUE);
				int1 = intersectionMap.get(r.intersect1);
				int2 = intersectionMap.get(r.intersect2);
				x1 = int1.lon;
				x2 = int2.lon;
				y1 = int1.lat;
				y2 = int2.lat;
			
				page2.draw(new Line2D.Double((x1-minLong) * xScale, getHeight() - ((y1 - minLat) * yScale), (x2-minLong) * xScale, getHeight() - ((y2 - minLat) * yScale)));	
			}
		}
	}
	
	public void scale() {
		xScale = this.getWidth() / (maxLong - minLong);
		yScale = this.getHeight() / (maxLat - minLat);
	}

}