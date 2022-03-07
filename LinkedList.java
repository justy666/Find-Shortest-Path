/*
Author: Justin Shen
Student ID: 31819329
 */

public class LinkedList {
	
	public int size;
	public Node head;
	
	public LinkedList() {
		head = new Node();
		size = 0;
	}
	public double findCost(Intersection int2) {
		Edge temp2 = head.edge;
		while(temp2 != null) {		
			if(temp2.road.intersect1.equals(int2.InterID) || temp2.road.intersect2.equals(int2.InterID)) {
				return temp2.road.distance;
			}		
			temp2 = temp2.nxt;
		}	
		return -1;	
	}	
	public int size() {
		return size;
	}
	
	public boolean contains(Intersection i) {
		Node temp = head;
		while(temp != null) {
			if(temp.intersection.equals(i)) {
				return true;
			}	
			temp = temp.next;
		}	
		return false;	
	}
	
	public void insert(Intersection intersect) {
		if(head.intersection == null) {
			head.intersection = intersect;
		}
		size++;
	}
	
	public void insert(Road road) {
		Edge tempEdge = new Edge();
		tempEdge.road = road;
		tempEdge.nxt = head.edge;
		head.edge = tempEdge;
	}
	
	public boolean connected(Intersection int2) {
		Edge temp2 = head.edge;
		while(temp2 != null) {
			if(temp2.road.intersect1.equals(int2.InterID) || temp2.road.intersect2.equals(int2.InterID)) {
				return true;
			}
			temp2 = temp2.nxt;
		}
		return false;
	}
}