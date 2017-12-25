import java.util.*;
import java.io.*;

public class GPACalculator {
	private static double currentunits;
	private static double currentGPA;
	private static double futureunits;
	private static double desiredGPA;
	private static double futureGPA;
	private static double[] grades = {0.0,0.7,1.0,1.3,1.7,2.0,
									  2.3,2.7,3.0,3.3,3.7,4.0};
	private static int numfiveunits;
	private static int numtwounits;
	private static final Hashtable<Double,String> lettergrade = new Hashtable<Double,String>() {{
	    put(0.0,"F");
	    put(0.7,"D-");
	    put(1.0,"D");
	    put(1.3,"D+");
	    put(1.7,"C-");
	    put(2.3,"C+");
	    put(2.7,"B-");
	    put(3.0,"B");
	    put(3.3,"B+");
	    put(3.7,"A-");
	    put(4.0,"A");
	 }};
	
	
	//methods
		//constructor function
		public GPACalculator(double currunit, double currGPA, double funits,double desGPA){
				if(currunit <= 0){
					System.out.println("Unvalid number of credits");
					System.exit(1);
				}
				currentunits = currunit;
				currentGPA= currGPA;
				futureunits = funits;
				desiredGPA = desGPA;
				
		}
		
		public static void main(String[]args){
			System.out.print("Select what you want to calculate: additional units needed(1), average GPA needed(2),");
			System.out.println(" grades needed next quarter(3). Type the number.");
			Scanner in = new Scanner(System.in);
			String s = in.nextLine();
			if(s.equals("1")){
				
				System.out.print("Enter your current GPA:");
				currentGPA = in.nextDouble();
				System.out.print("Enter GPA you want:");
				desiredGPA = in.nextDouble();
				System.out.print("Enter the number of units you currently have:");
				currentunits = in.nextDouble();
				System.out.print("Enter your average GPA you think you will obtain:");
				futureGPA = in.nextDouble();
				
				doAdditionalUnits(currentGPA,desiredGPA,currentunits,futureGPA);				
			
			}else if(s.equals("2")){
				System.out.print("Enter your current GPA:");
				currentGPA = in.nextDouble();
				System.out.print("Enter GPA you want:");
				desiredGPA = in.nextDouble();
				System.out.print("Enter the number of units you currently have:");
				currentunits = in.nextDouble();
				System.out.print("Enter the number of units you are going to take:");
				futureunits = in.nextDouble();
				
				doFutureGPA(currentGPA,desiredGPA,currentunits,futureunits);
				
			
			}else if(s.equals("3")){
				System.out.print("Enter your current GPA:");
				currentGPA = in.nextDouble();
				System.out.print("Enter GPA you want:");
				desiredGPA = in.nextDouble();
				System.out.print("Enter the number of units you currently have:");
				currentunits = in.nextDouble();
				System.out.print("Enter the number of 5-unit classes you are taking next quarter:");
				numfiveunits = in.nextInt();
				System.out.print("Enter the number of 2-unit classes you are taking next quarter:");
				numtwounits =in.nextInt();
				
				doGradesNeeded(currentGPA, desiredGPA, currentunits, numfiveunits,numtwounits);
				
			}else{
				System.out.print("Invalid input.");
			}
		}
		
		public static void doAdditionalUnits(double currentGPA,double desiredGPA,double currentunits,double futureGPA) {
			double diffGPA = desiredGPA - currentGPA;
			double futureunits = Math.ceil( (diffGPA* currentunits)/(futureGPA - desiredGPA));
			System.out.println("In order to obtain a "+desiredGPA+" GPA you need to take "+ futureunits+" units.");
		}
		
		public static void doFutureGPA(double currentGPA,double desiredGPA,double currentunits,double futureunits) {
			double totalunits= currentunits + futureunits;
			double desiredunits= (desiredGPA * totalunits) - (currentGPA * currentunits);
			double futureGPA = desiredunits / futureunits;
			futureGPA = Math.round(futureGPA*100.0)/100.0;
			System.out.print("In order to obtain a "+desiredGPA+" GPA in "+ futureunits+ " units");
			System.out.print(" you need a "+futureGPA+ " GPA.");
		}
		
		public static void doGradesNeeded(double currentGPA, double desiredGPA,double currentunits,
										  double numfiveunits,double numtwounits) {
			//calculating future GPA
			futureunits = (5*numfiveunits)+(2*numtwounits);
			double totalunits= currentunits + futureunits;
			double desiredunits= (desiredGPA * totalunits) - (currentGPA * currentunits);
			double futureGPA = desiredunits / futureunits;
			
			// 2 arrays->one for 5 unit classes and one for 2 unit classes that have all grade points
			double[] fiveunitarr = new double[12];
			double[] twounitarr = new double[12];
			futureGPA = futureGPA * futureunits;
			for(int i= 0; i< grades.length; i++){
				fiveunitarr[i] = 5*numfiveunits*grades[i];
				twounitarr[i] = 2*numtwounits*grades[i];
			}
			
			//iterating over both arrays and breaks when it finds a sum of the two array indices
			//that is higher than the futureGPA
			double result = 0.00;
			double fiveresult = 0.00;
			double tworesult =0.00;
			boolean resultfound;
			for(int i = 0;i<grades.length;i++){
             resultfound = false;
				for(int j = 0; j<grades.length;j++){
					double sum = fiveunitarr[i]+twounitarr[j];
					if(  sum >=futureGPA){
						result = sum;
						fiveresult = grades[i];
						tworesult = grades[j];
						resultfound = true;
						break;
					}
				}
				if(resultfound) break;
			}
			System.out.println("To obtain a "+ desiredGPA + " GPA by next quarter:");
			if(numfiveunits>0){
				System.out.println("For every 5 unit class, you must aim for a "+ lettergrade.get(fiveresult)+".");
			}
			if(numtwounits>0){
				System.out.println("For every 2 unit class, you must aim for a "+ lettergrade.get(tworesult)+".");
			}
		}
		
}
