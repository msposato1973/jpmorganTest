package it.jpm.model;

import java.util.Comparator;

public class CoparetorReport implements Comparator<DataReport> {

	 
	    public int compare(DataReport obj1, DataReport obj2) {
	    	double delta = obj1.getUsdAmount() - obj2.getUsdAmount();
	    	if(delta > 0.00001) return 1;
	    	else if(delta < -0.00001) return -1;
	    	else return 0;
	    }

	 

}
