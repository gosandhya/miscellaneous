
public class multiplication {
	//0 ka masla ha.. parse int ignore karaha ha 0 ko. check it out.
	
	public double karatsuba(String a, String b)
	{
		int mid1 = a.length()/2;
		int mid2 = b.length()/2;
		//System.out.println(mid1);
		
		
		 String xx1=  a.substring(0, mid1);
		 String xx2 = a.substring(mid1, a.length());
		
		 String yy1=  b.substring(0, mid2);
		 String yy2 = b.substring(mid2, b.length());
		
		 int x1 = Integer.parseInt(xx1);
		 int x2 = Integer.parseInt(xx2);
		 int y1 = Integer.parseInt(yy1);
		 int y2 = Integer.parseInt(yy2);
		
		System.out.println("x1 "+x1 + " "+ "x2 " +x2)	;
	    System.out.println("y1 "+y1 + " "+ "y2 " +y2)	;
		
		int firstMul  = x1*y1;
		int secondMul = x2*y2;
		int thirdMul  = ((x1+x2)*(y1+y2));//-secondMul - firstMul);
		
		//double 
		System.out.println("firstMult "+firstMul + " "+ "secondMult " +secondMul + " "+ "thirdMult " +thirdMul)	;
		double one = Math.pow(10,a.length())*firstMul;
		double two = Math.pow(10,mid1)*(thirdMul-secondMul-firstMul);
		double three = secondMul;
		System.out.println("one"+" "+one);
		System.out.println("two"+" "+two);
		System.out.println("three"+" "+three);
		double multiply = (one+two+three);
		System.out.println("Multiply "+multiply)	;

		return multiply;
	}
	
	
	public double karatsubaa(String a, String b)
	{
		int mid1 = a.length()/2;
		int mid2 = b.length()/2;
		
		 if(a.length() ==1 || b.length()==1)
		 {
			 int x1 = Integer.parseInt(a);
			 int y1 = Integer.parseInt(b);
			 return x1*y1;
		 }
		
		 String xx1=  a.substring(0, mid1);
		 String xx2 = a.substring(mid1, a.length());
		
		 String yy1=  b.substring(0, mid2);
		 String yy2 = b.substring(mid2, b.length());
		 
		
		 //convert from string to int
		 int x1 = Integer.parseInt(xx1);
		 int x2 = Integer.parseInt(xx2);
		 int y1 = Integer.parseInt(yy1);
		 int y2 = Integer.parseInt(yy2);
		 
		 int xxx1 = x1+x2;
		 int yyy1 = y1+y2;
		 
		 System.out.println("xxx1 "+""+xxx1 + " yyy1 "+ ""+yyy1);
		 
		String c = String.valueOf(xxx1);
		String d = String.valueOf(yyy1);
		 
		double firstMul  = karatsubaa(xx1,yy1);
		double secondMul = karatsubaa(xx2,yy2);
		double thirdMul = karatsubaa(c,d)-secondMul-firstMul;
		//double thirdMul = third - firstMul-secondMul;
	//	double secondMul = x2*y2;
		//double thirdMul  = (((x1+x2)*(y1+y2))-firstMul - secondMul);
		
	
		System.out.println("firstMult "+firstMul + " "+ "secondMult " +secondMul + " "+ "thirdMult " +thirdMul)	;
		double multiply = (Math.pow(10,a.length())*firstMul + Math.pow(10,mid1)*thirdMul + secondMul);
		System.out.println("Multiply "+multiply)	;

		return multiply;
	}
}
