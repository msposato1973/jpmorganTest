package it.jpm.report;

/***
 * 
 * @author User
 *
 */
public class ExecuteMain {

	 
	/***
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JPExecutionReport excute=new JPExecutionReport();
		
		System.out.println( "I will use a default file in C:\\Data.csv ");
		excute.loadData();	
		
		
		

	}

}
