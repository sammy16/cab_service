// Author:	Renee L. Ramsey, W. Klostermeyer
// Algorithms: An Undergraduate Course with Programming
// Program:     dijkstra.java
// Date:	5/12/2002, 11/14/02
//
// dijkstra.java
//
// to run this program: >java dijkstra
// note the PriorityQueue class must reside in the same directory.
import java.io.*;	//for I/O
import java.util.Stack;
// end main
//--------------------------------------------------------------

// If you want to use a Linked List graph, 
// you must change the datatype to be graphLinkList
// If you want to use randomgraph, you must change datatype to be randomgraph
// You could probably re-write graphLinkList and randomgraph to return a graph object,
// but I am too lazy.

   public class dijkstra
   {						// s is the index of the starting vertex
	   							
	    							// declare variables
	public static int s;
	public static int no;
	
		public dijkstra(int s,int no) throws IOException{	
			this.s=s;
			this.no=no;
			graph G = new graph("graph.txt");
			 dijkstra_function(G,s,no);
		}
		public static void dijkstra_function(graph G, int s,int no) throws IOException
		   {                  					// s is the index of the starting vertex
		   	// declare variables
			int nVerts, u, v;
			int [] dist;
			

		        nVerts = G.vertices(); 			// get number of vertices in the graph class

		        // initialize array
		        dist = new int[nVerts];
		        Stack[] path = new Stack[nVerts];
		        for(v=0; v<nVerts; v++) 			// initializations
		        {
		        	dist[v] = 99999; 				// 99999 represents infinity
		        	path[v]=new Stack<Integer>();
		        	path[v].push(s);
		        }// end for

		        dist[s] = 0;

		        PriorityQueue Q = new PriorityQueue(dist);  		

		        while(Q.Empty() == 0) 			// if heap is not empty
		        {

		                u = Q.Delete_root();
		                v = G.nextneighbor(u);

		                while(v != -1)  			// for each neighbor of u
		                {
		                	if(dist[v] > dist[u] + G.edgeLength(u,v)) {
		                		dist[v] = dist[u] + G.edgeLength(u,v);
		                		path[v].pop();
		                		path[v].push(u);
		                		Q.Update(v, dist[v]);
		                         }

		                	v = G.nextneighbor(u);  	// get the next neighbor of u

		                }// end while

		        }// end while
		        for(v=0; v<nVerts; v++) 			// initializations
		        {	while((Integer)path[v].peek()!=s){
		        		int top=(Integer) path[v].peek();
		        		for(int i=0;i<path[top].size();i++){
		        			path[v].push(path[top].get(i));
		        		}
		        	}
		        	
		        }
		        FileWriter fw = new FileWriter("path"+no+".txt");
		        for(int i=0; i<nVerts; i++)
		      	{	
		      		fw.write(dist[i] + " ");
		      		while(!path[i].empty())
		      			fw.write((Integer)path[i].pop() + " ");
		      		fw.write(i+"\n");
		      	}// end for
		        fw.close();

		   }// end bfs_function
   }//end class dijkstra
///////////////////////////////////////////////////////////////////////////
