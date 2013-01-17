// Program:     PriorityQueue Class
// Date:	4/27/2002, 11/14/02
//
// PriorityQueue.java
//		Let A be PriorityQueue with n nodes
//		stored in Array of items A[0..n-1]
//		left child of A[i] stored at A[2i+1]
//		right child of A[i] stored at A[2i+2]
// Note: 	The PriorityQueue class takes an array as an input parameter.
// to run this program: > Call the class from another program.
//			  Example:  PriorityQueue a = new PriorityQueue(array);
import java.io.*;	//for I/O
///////////////////////////////////////////////////////////////////////////
public class PriorityQueue
{
   class item
   {
      int index;				// vertex index
      int value;				// distance

      public item()
      {

         index = 0;			// initializations
         value = 0;

      }

   }// end class item


   public item [] A;			// array A used as a heap
   public int n;			// number of nodes
   public int [] Ad;  			// Ad[i] stores location in A
					// of vertex numbered i


   public PriorityQueue(int B[]) throws IOException	// constructor
   {
 	A = new item[B.length];			// allocate array A of items
     
      	n = B.length;				// get the number of nodes
   	Ad = new int[n]; 

	for(int z=0; z<n; z++)
	{
             A[z] = new item();
             A[z].index = z;
             A[z].value = B[z];
	       Ad[z]=z;	 

	}// end for

	for(int i=n/2-1; i>=0; i--) 		// ignore leaves
	{
   		Heapify(i);			// call Heapify method

	}// end for

   }// end constructor

//------------------------------------------------------------------------
   public void Heapify(int i)  	// routine to percolate down from index i
   {
	int left, r, min, tmp;			// declare variables

	left = 2 * i + 1;     			// left child
	r = 2 * i + 2;       			// right child

  	if(left < n && A[left].value < A[i].value)	// find smallest child, if any less than A[i]
     		min = left;             	// save index of smaller child
  	else
  	   	min = i;

  	if(r < n && A[r].value < A[min].value)
     		min = r;           		// save index of smaller child

  	if(min != i)	 			// swap and percolate, if necessary
  	{

		tmp = Ad[A[i].index];
          	Ad[A[i].index] = Ad[A[min].index];
         	Ad[A[min].index] = tmp;

		tmp = A[i].value;      		// exchange values at two indices
      		A[i].value = A[min].value;
      		A[min].value = tmp;
      		tmp = A[i].index;      		// exchange values at two indices
      		A[i].index = A[min].index;
      		A[min].index = tmp;

               
      		Heapify(min); 			// call Heapify

     	}// end if

   }// end method Heapify

//------------------------------------------------------------------------
   public int Delete_root()			// remove node with minimum value
   {
	int min;				

	if(n < 1) 				
	{
		System.out.println("error");	// display error
		return -1; 				// return -1
	}
	else
	{
   		min = A[0].index;
   		A[0].value = A[n-1].value;    // replace root with last element in heap
   		A[0].index = A[n-1].index;    // replace root with last element in heap

		Ad[A[n-1].index]=0;

   		n--;            			// reduce heap size
   		Heapify(0);  			// percolate new root downwards
   		return min;  			// return min

	}// end if

   }// end method Delete_root()

//------------------------------------------------------------------------
   public void Update(int i, int new_key)  // decrease value of key at index i
   {
	int tmp;					// declare variables
   
      	i = Ad[i];					// find location in heap of vertex i

	A[i].value = new_key;   		// assuming this is less than old key value
	while(i > 0 && A[(i+1)/2-1].value > new_key)  	// percolate up
  	{
		tmp = Ad[A[i].index];
          	Ad[A[i].index] = Ad[A[(i+1)/2-1].index];
         	Ad[A[(i+1)/2-1].index] = tmp;

  		tmp = A[i].value;   			// swap A[i] and A[i/2]
  		A[i].value = A[(i+1)/2-1].value;
  		A[(i+1)/2-1].value = tmp;
  		tmp = A[i].index;   			// swap A[i] and A[i/2]
  		A[i].index = A[(i+1)/2-1].index;
  		A[(i+1)/2-1].index = tmp;
  		i = (i+1)/2-1;               		

  	}// end while

   }// end method Update()

   //------------------------------------------------------------------------
   public int Empty()  				// return if heap is empty or not
   {
      if(n == 0) 					// if it's empty
      	return 1;
      else
      	return 0;    			// not empty

   }// end method Update()

  //----------------------------------------------------------------------
   public void display()
   {
   for(int z=0; z<n; z++){
      System.out.println(A[z].index);
      System.out.println(A[z].value);
      System.out.println("");
   }
 
   }

   //------------------------------------------------------------------------
}// end PriorityQueue class
////////////////////////////////////////////////////////////////////////////

