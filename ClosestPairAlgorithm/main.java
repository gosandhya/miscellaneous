import java.util.Arrays;
import java.util.Comparator;
import java.util.ArrayList;


public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		distance d1 = new distance();
		Point[] arrOfPoints = new Point[16];
		
		d1.test(arrOfPoints);
		//d1.Sort(arrOfPoints);
		
		Arrays.sort(arrOfPoints, new Comparator<Point>() {

		    @Override
		    public int compare(Point arg0, Point arg1) {
		        return Double.compare(arg0.x, arg1.x);
		    }
		});
		
		
		
		double minPoint = d1.minPoint(arrOfPoints, 0,15);
		
		System.out.println(minPoint + " is the minimum distance");
	}

}
