import java.util.ArrayList;
import java.util.Comparator;
public class distance
{
	
	
	 public void test(Point[] arrOfPoints)
	 {
		 for(int i = 0; i < arrOfPoints.length; i++) 
		 {
			 arrOfPoints[i] = new Point();
		 }
		 
	   	 arrOfPoints[0].x = 4;
		 arrOfPoints[0].y = 3;
		 
		 arrOfPoints[1].x = 8;
		 arrOfPoints[1].y = 6;
		 
		 arrOfPoints[2].x = 3;
		 arrOfPoints[2].y = 7;
		 
		 arrOfPoints[3].x = 6;
		 arrOfPoints[3].y = 8;
		 
		 arrOfPoints[4].x = 9;
		 arrOfPoints[4].y = 4;
		 
		 arrOfPoints[5].x = 1;
		 arrOfPoints[5].y = 2;
		 
		 arrOfPoints[6].x = 2;
		 arrOfPoints[6].y = 5;
		 
		 arrOfPoints[7].x = 5;
		 arrOfPoints[7].y = 6;
		 
		 arrOfPoints[8].x = 7;
		 arrOfPoints[8].y = 5;
		 
		 arrOfPoints[9].x = 7;
		 arrOfPoints[9].y = 5;
		 
		 arrOfPoints[10].x = 7;
		 arrOfPoints[10].y = 5;
		 
		 arrOfPoints[11].x = 7;
		 arrOfPoints[11].y = 5;
		 
		 arrOfPoints[12].x = 7;
		 arrOfPoints[12].y = 5;
		 
		 arrOfPoints[13].x = 7;
		 arrOfPoints[13].y = 5;
		 
		 arrOfPoints[14].x = 7;
		 arrOfPoints[14].y = 5;
		 
		 arrOfPoints[15].x = 7;
		 arrOfPoints[15].y = 5;
		 
		
		 	
	 }
	 
	 public void print(Point[] arr)
	 {
		
		 for(int i=0; i<arr.length; i++)
		 {
			 System.out.print(arr[i].x + " ");
			 System.out.print(arr[i].y);
			 System.out.print("\n");
		 }
	 }
	 
	 
 
	 
	 double dist(Point p1, Point p2)
	 {
	     return Math.sqrt( (p1.x - p2.x)*(p1.x - p2.x) +
	                  (p1.y - p2.y)*(p1.y - p2.y) );
	 }
	 
	 
	
	 
	 public double bruteForceSearch(ArrayList<Point> arr,int begin, int end)
	 {
		 double min = 10000;
		 
		 for(int i=begin; i<end; i++)
		 {
			 for(int j=i+1; j<end; j++)
			 {
				 if(dist(arr.get(i),arr.get(j)) < min)	 
				 {
					 min = dist(arr.get(i),arr.get(j));
				 }
			 }
		 } 
		 System.out.println("min" + " "+ min);
		 return min; 
	 }
	 
	 
	 
	 public double recursive(Point[] arr, int begin, int end)
	 {
		 int mid = (begin+end)/2;
		 System.out.println(begin + " "+ mid + " "+ end);
		 double alpha;
		 

		 if((end-begin+1) == 2)
		 {
			 return dist(arr[begin],arr[end]);
		 }
		 if((end-begin+1) == 1)
		 {
			 
			 return  Double.POSITIVE_INFINITY;
		 }

		 double firstHalf  = recursive(arr, begin, mid);

		 double secondHalf = recursive(arr, mid+1,end);

		// System.out.println(firstHalf + " "+secondHalf);

		 if(firstHalf < secondHalf)
		 {
			 alpha = firstHalf;
		 }
		 else 
		 {
			 alpha = secondHalf;
		 }

		 System.out.println("alphaaa" + " "+ alpha);
		 
	
		 return alpha;
		 
	 }
	 
	 public double minPoint(Point[] arr, int begin, int end)
	 {
		 int mid = end/2;
		// System.out.println(begin + " "+ mid + " "+ end);

		 double midX = arr[mid].x;
		 double midY = arr[mid].y;
		 
		 double alpha = recursive(arr,begin, end);
		 
		 //Point newArr = new Point[end];
		 ArrayList<Point> newArr = new ArrayList<Point> ();
		 
		 for(int i=begin; i<=end; i++)
		 {
			if( arr[i].x >= midX-alpha && arr[i].x <= midX+alpha)
			{
				newArr.add(arr[i]);	
				
			}
			
		 }
		 
		 
		// System.out.println("array list"+ " "+newArr);
		 newArr.sort( new Comparator<Point>() {

			    @Override
			    public int compare(Point arg0, Point arg1) {
			        return Double.compare(arg0.y, arg1.y);
			    }

			});
		// System.out.println("array list"+ " "+newArr);

		 double lastMin =  bruteForceSearch(newArr,0, newArr.size());

		 if(alpha >  lastMin)
		 {
			 alpha = lastMin;
		 }
		 
		 return alpha;
	 }
	 
	 



}


