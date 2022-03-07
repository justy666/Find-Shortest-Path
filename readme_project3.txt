Assignment: Project 3

Name: Justin Shen


Email: jshen37@u.rochester.edu


obstacles:
1. Not familiar with Joption pane syntax, so i spend some time to familiarized through reading can coding few samples.
2. for Dijkstra's and Kruskal's Algorithm. Instead of iterating through all the remaining unknown intersections to find the smallest 
one, I decided on a PriorityQueue which would be much faster.

Runtime:
Due to my calculation, I believe the runtime for displaying the regular graph is O(E) where is E is the number of roads in the graph.

For the shortest path between two intersections, finding the smallest unknown vertex is a constant time operation. I use a counter for the number of unknown vertices instead of scanning through 
all the vertices, so that check is a constant time operation.

The two HashSets corresponding to the end of the Road is a constant time operation, the runtime for Kruskal's algorithm is O(E) where E is the number of Roads in the graph, because every Road is checked in my 
implementation of the algorithm.

The runtime for displaying the Meridian map is O(E) . All of the edges are added to the arrayList, so every Road in the List is displayed.

The runtime for entire program will be linearly growth.


Files:
1. Edge.java
2. Intersection.java
3. Map.java
4. MapGUI.java
5. Road.java
6. Test.java (main method)
7. Node.java
8. LinkedList.java
9. Output.txt
10. readme_project3.txt


Reference:
1. A collection of joptionpane examples and tutorials. (n.d.). Retrieved May 01, 2021, from https://alvinalexander.com/java/java-joptionpane-examples-tutorials-dialogs/
2. Baeldung. (2021, January 09). Dijkstra algorithm in Java. Retrieved May 01, 2021, from https://www.baeldung.com/java-dijkstra
3. Edgettek. (n.d.). Edgettek/street-mapping. Retrieved May 01, 2021, from https://github.com/edgettek/Street-Mapping