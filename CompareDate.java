package it.jpm.model;

import java.util.Comparator;

public class CompareDate implements Comparator<DataReport>{

	    
    
	@Override
	public int compare(DataReport p, DataReport q) {
		if (p.getActualSetlDate().before(q.getActualSetlDate())) {
            return -1;
        } else if (p.getActualSetlDate().after(q.getActualSetlDate())) {
            return 1;
        } else {
            return 0;
        }  
	}

	 
}
