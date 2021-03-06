/*
Author: Justin Shen
Student ID: 31819329
 */

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.PriorityQueue;


public class Map {
	
	public HashMap<String, LinkedList> graph;
	public static int numIntersections;
	
	public static ArrayList<Road> roads;
	public static HashMap<String, Intersection> intersectionMap;
	public static PriorityQueue<Intersection> unknownIntersectionsHeap;
	public static PriorityQueue<Road> kruskalsRoads;
	public static HashMap<String, HashSet<String>> intersectionSets;
	public static ArrayList<Road> minWeightSpanTree;
	public static Intersection [] dijkstraPath;

	public static double minLat, maxLat, minLong, maxLong;
	
	public Map(int numVertices) {
		graph = new HashMap<String, LinkedList>();

		numIntersections = numVertices;
		roads = new ArrayList<Road>();
		intersectionMap = new HashMap<String, Intersection>();
		Comparator<Intersection> comparator = new Comparator<Intersection>() {

        	@Override
        	public int compare(Intersection i1, Intersection i2) {
            	
            	if(i1.dis < i2.dis) {
            		return -1;
            	}
            	else {
            		return 1;
            	}
            }
		};
		
		unknownIntersectionsHeap = new PriorityQueue<Intersection>(numVertices, comparator);
		Comparator<Road> comparator2 = new Comparator<Road>() {

        	@Override
        	public int compare(Road r1, Road r2) {	
            	if(r1.distance < r2.distance) {
            		return -1;
            	}
            	else {
            		return 1;
            	}
            }
		};
		kruskalsRoads = new PriorityQueue<Road>(numVertices*3, comparator2);
		minLat = minLong = Integer.MAX_VALUE;
		maxLat = maxLong = Integer.MIN_VALUE;
	}
	
	public int size() {
		return graph.size();
	}

	public static String formPath(String endID) {
		Intersection temp = intersectionMap.get(endID);
		String [] path = new String[intersectionMap.size()];
		int counter = 0;
		while(temp.path != null) {
			path[counter] = temp.InterID;
			temp = temp.path;
			counter++;
		}
		
		path[counter] = temp.InterID;
		int totalPath = 0;
		
		for(int i = 0; i < path.length; i++) {
			if(path[i] == null) {
				totalPath = i;
				break;
			}
		}
		
		dijkstraPath = new Intersection [totalPath];
		
		for(int i = 0; i < totalPath; i++) {
			dijkstraPath[i] = intersectionMap.get(path[i]);
		}
		
		String finalPath = "";
		for(int i = counter ; i > -1; i--) {
			finalPath = finalPath + path[i] + "\n";
		}
		return finalPath;
	}
	
	public static double dijkstraPathLength() {
		return dijkstraPath[0].dis * 0.000621371;
	}
	
	public static Intersection smallestUnknownVertex() {
		Intersection temp = unknownIntersectionsHeap.remove();
		return intersectionMap.get(temp.InterID);
	}
	
	public void createSet() {
		
		//instantiate the HashMap that maps IntersectionID to HashSet of vertices connected to that intersection
		intersectionSets = new HashMap<String, HashSet<String>>();
		
		HashSet<String> intersections;
		
		//iterate over all the entries in the graph
		Iterator<Entry<String, LinkedList>> iterator = graph.entrySet().iterator();
		
		while (iterator.hasNext()) {
	        HashMap.Entry<String, LinkedList> pair = (HashMap.Entry<String, LinkedList>) iterator.next();	        
	        intersections = new HashSet<String>();	        
	        intersections.add(pair.getKey());
	        intersectionSets.put(pair.getKey(), intersections);
	        
		}		
	}
 	
	public void kruskals() {
		createSet();
		minWeightSpanTree = new ArrayList<Road>();		
		Road currentRoad;
		HashSet<String> u;
		HashSet<String> v;
		while(kruskalsRoads.size() > 0) {
			currentRoad = kruskalsRoads.remove();			
			u = intersectionSets.get(currentRoad.intersect1);
			v = intersectionSets.get(currentRoad.intersect2);			
			if(!u.equals(v)) {				
				minWeightSpanTree.add(currentRoad);
				u.addAll(v);
				for(String intersectionID: u) {
					intersectionSets.put(intersectionID, u);
				}
			}
		}
	}
	
	public void dijkstra(String intersectionID) {
		
		Intersection start = intersectionMap.get(intersectionID);		
		unknownIntersectionsHeap.remove(start);		
		start.dis = 0;
		unknownIntersectionsHeap.add(start);
		double cost;		
		int numUnknownVertices = intersectionMap.size();
		while(numUnknownVertices > 0) {
			
			Intersection temp = smallestUnknownVertex();
			temp.known = true;
			numUnknownVertices--;
			LinkedList currentVertex = graph.get(temp.InterID);
			Edge currentRoad = currentVertex.head.edge;
			Intersection currentIntersection;
			while(currentRoad != null) {

				if(currentRoad.road.intersect1.equals(temp.InterID)) {
					currentIntersection = intersectionMap.get(currentRoad.road.intersect2);
				}
				else {
					currentIntersection = intersectionMap.get(currentRoad.road.intersect1);
				}				
				if(currentIntersection.known == false) {
					cost = findCost(temp, currentIntersection);
					
					if(temp.dis + cost < currentIntersection.dis) {
						unknownIntersectionsHeap.remove(currentIntersection);						
						currentIntersection.dis = temp.dis + cost;
						currentIntersection.path = temp;
						
						unknownIntersectionsHeap.add(currentIntersection);
					}
				}
				
				currentRoad = currentRoad.nxt;
			}
		}
	}
	
	public double findCost(Intersection int1, Intersection int2) {
		LinkedList temp = graph.get(int1.InterID);	
		return temp.findCost(int2);
	}
	
	public boolean connected(Intersection int1, Intersection int2) {
		LinkedList temp = graph.get(int1.InterID);
		return temp.connected(int2);
	}
	
	public void insert(Intersection i) {		
		if(i.lat > maxLat) {
			maxLat = i.lat;
		}
		if(i.lat < minLat) {
			minLat = i.lat;
		}
		if(i.lon > maxLong) {
			maxLong = i.lon;
		}
		if(i.lon < minLong) {
			minLong = i.lon;
		}
		intersectionMap.put(i.InterID, i);
		unknownIntersectionsHeap.add(i);
		LinkedList temp = new LinkedList();
		temp.insert(i);
		graph.put(i.InterID, temp);
	}
	
	public void insert(Road e) {
		LinkedList int1 = graph.get(e.intersect1);
		LinkedList int2 = graph.get(e.intersect2);
		int1.insert(e);
		int2.insert(e);
		kruskalsRoads.add(e);
		
		roads.add(e);
	}
	
	public static Intersection intersectLookup(String intersectID) {
		return intersectionMap.get(intersectID);
	}
	
	public static double roadDist(Intersection int1, Intersection int2) {	
		return calcDist(int1.lat, int1.lon, int2.lat, int2.lon);	
	}
	
	public static double calcDist(double lat1, double long1, double lat2, double long2) {
		
		int earthRadius = 6371000;
		lat1 = Math.toRadians(lat1);
		long1 = Math.toRadians(long1);
		lat2 = Math.toRadians(lat2);
		long2 = Math.toRadians(long2);
		
		double changeLat = lat2-lat1;
		double changeLong = long2-long1;
		double a = (Math.sin(changeLat/2) * Math.sin(changeLat/2)) + (Math.cos(lat1) * Math.cos(lat2) * Math.sin(changeLong/2) * Math.sin(changeLong/2));
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		return earthRadius * c;
	}
}