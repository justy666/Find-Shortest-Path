/*
Author: Justin Shen
Student ID: 31819329
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import javax.swing.JFrame;


public class Test {
	
	public static void main(String [] args) throws FileNotFoundException {
		
		long startT = System.currentTimeMillis();		
		File mapData = new File(args[0]);
		if(args[0].equals("ur_campus.txt")) {
			MapGUI.thickLines = true;
		}
		
		int numInter = 0;
		Scanner scan = new Scanner(mapData);
		while(scan.nextLine().startsWith("i")) {
			numInter++;
		}

		scan.close();
		String intersectionID;
		double lat, longT;
		Intersection v;
		Scanner scan2 = new Scanner(mapData);
		Map map = new Map(numInter);
		String crtline = scan2.nextLine();
		String [] info;

		while(crtline.startsWith("i")) {
			
			info = crtline.split("\t");
			intersectionID = info[1];
			lat = Double.parseDouble(info[2]);
			longT = Double.parseDouble(info[3]);
			
			v = new Intersection();
			v.dis = Integer.MAX_VALUE;
			v.InterID = intersectionID;
			v.lon = longT;
			v.lat = lat;
			v.known = false;	
			crtline = scan2.nextLine();
			map.insert(v);
		}
		
		String roadID, int1, int2;
		Intersection w, x;
		double dist;
		
		while(crtline.startsWith("r")) {	
			info = crtline.split("\t");	
			roadID = info[1];	
			int1 = info[2];
			int2 = info[3];
			x = Map.intersectLookup(int2);
			w = Map.intersectLookup(int1);
			dist = Map.roadDist(w, x);	
			map.insert(new Road(roadID, int1, int2, dist));	
			if(scan2.hasNextLine() == false) {
				break;
			}
			crtline = scan2.nextLine();	
		}
		
		String fileName;
				if(args[0].equals("ur.txt")) {
			fileName = "UR Campus";
		}
		else {
			if(args[0].equals("monroe.txt")) {
				fileName = "Monroe County";
			}
			else {
				if(args[0].equals("nys.txt")) {
					fileName = "NY State";
				}
				else {
					fileName = "Map";
				}
			}
		}
		boolean showMap = false;
		boolean dij = false;
		boolean mwst = false;
		
		String directionsStart = "i0";
		String directionsEnd = "i1";
		for(int i = 0; i < args.length; i++) {
			if(args[i].equals("-show")) {
				showMap = true;
			}
			if(args[i].equals("-directions")) {
				dij = true;	
				directionsStart = args[i+1];
				directionsEnd = args[i+2];
			}
			if(args[i].equals("-meridianmap")) {
				mwst = true;
			}
		}
		if(dij == true) {
			map.dijkstra(directionsStart);
			System.out.println("\nshortest path from " + directionsStart + " to " + directionsEnd + " is: ");
			System.out.println(Map.formPath(directionsEnd));
			
			System.out.println("Length from " + directionsStart + " to " + directionsEnd + " is: " + Map.dijkstraPathLength() + " miles.");
		}
		if(mwst == true) {
			map.kruskals();
			System.out.println("\nRoads Taken for" + fileName + ":\n");
			for(Road r : Map.minWeightSpanTree) {
				System.out.println(r.roadID);
			}
		}
		
		if(showMap == true) {
			JFrame frame = new JFrame("Map");
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().add(new MapGUI(Map.roads, Map.intersectionMap, Map.minLat, Map.maxLat, Map.minLong, Map.maxLong));
			frame.pack();
			frame.setVisible(true);
		}
		long etime = System.currentTimeMillis();
		long elatime = etime-startT;
		System.out.println("\n\nTime required: " + elatime/1000 + " seconds.");	
		scan2.close();
	}

}