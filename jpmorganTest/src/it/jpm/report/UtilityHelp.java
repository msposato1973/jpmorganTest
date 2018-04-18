package it.jpm.report;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import it.jpm.model.Constant;
import it.jpm.model.DataReport;

/***+
 * 
 * @author User
 *
 */
abstract class UtilityHelp {

	
	
	/***
	 * 
	 * @param settingdate
	 * @return
	 * @throws ParseException
	 */
	protected Date convertStringInToDate(String settingdate) throws ParseException {
		DateFormat format = new SimpleDateFormat(Constant.PATTERN,Locale.ENGLISH);
	    String dateInString  = settingdate.replace(" ","-");
	    Date d1 = format.parse(dateInString);
	    Calendar c1 = Calendar.getInstance();
	    c1.setTime(d1);
	    return c1.getTime();
		
	}
	
	protected String convertDateInToString(Date settingdate)  {
		DateFormat format = new SimpleDateFormat(Constant.PATTERN,Locale.ENGLISH);
		 String sDate = "";
		 
	    sDate = format.format(settingdate);
	    
	    return sDate;
		
	}
	
	
	/***
	 * 
	 * @param pricePerUnit
	 * @param units
	 * @param agreedFx
	 * @return
	 * @throws NumberFormatException
	 */
	protected double calculateAmount (String pricePerUnit, String units, String agreedFx) 
		    throws NumberFormatException {
		    Double db = new Double(setFormatAmount(pricePerUnit)).doubleValue() * new Double(units).doubleValue() * new Double(agreedFx).doubleValue();
		    return   Double.valueOf(db);
	}
	
	/***
	 * 
	 * @param client
	 */
	public static void orderRank(List<DataReport> client) {
		Collections.sort(client, new Comparator<DataReport>(){
		    public int compare(DataReport s1, DataReport s2) {
		        return new Double(s1.getUsdAmount()).compareTo(new Double(s2.getUsdAmount()));
		    }
		});
    }
	
	//1) Amount in USD settled incoming everyday
	//2) Amount in USD settled outgoing everyday
	/***
	 * 
	 * @param client
	 */
	public static void sumInccoming(List<DataReport> client) {
	 	//Collections.sort(client, new CoparetorReport()); 
		client = sortByDouble(client);
	}
	
	/***
	 * 
	 * @param modelList
	 * @return
	 */
	public static List<DataReport> sortByDouble(List<DataReport> modelList) {
        Collections.sort(modelList, new Comparator<DataReport>() {
            @Override
            public int compare( DataReport s1, DataReport s2) {
                double s1Distance = Double.valueOf(s1!=null ? s1.getUsdAmount() : Double.valueOf("0"));
                double s2Distance = Double.valueOf(s2!=null ? s2.getUsdAmount() : Double.valueOf("0"));
                return Double.compare(s1Distance, s2Distance);
            }
        });
        return modelList;
    }
	
	
	
	/**
	 * 
	 * @param modelList
	 * @return
	 */
	public static List<DataReport> sortByDoubleDate(List<DataReport> modelList) {
        Collections.sort(modelList, new Comparator<DataReport>() {
            @Override
            public int compare( DataReport s1, DataReport s2) {
                long s1Distance = Long.valueOf(s1!=null ? s1.getActualSetlDate().getTime() : Long.valueOf("0"));
                long s2Distance = Long.valueOf(s2!=null ? s2.getActualSetlDate().getTime()  : Long.valueOf("0"));
                return Long.compare(s1Distance, s2Distance);
            }
        });
        return modelList;
    }
	
	/***
	 * 
	 * @param givenSettlementDate
	 * @param currencyCode
	 * @return
	 * @throws ParseException
	 */
	protected Date selectSettlementDate (String givenSettlementDate, String currencyCode) 
		    throws ParseException {
		    String pattern = "dd-MMM-yyyy";
		    DateFormat format = new SimpleDateFormat(pattern,Locale.ENGLISH);
		    String dateInString  = givenSettlementDate.replace(" ","-").trim();
		    Date d1 = format.parse(dateInString);
		    Calendar c1 = Calendar.getInstance();
		    c1.setTime(d1);
		   
		    if (currencyCode.equalsIgnoreCase("AED") || currencyCode.equalsIgnoreCase("SAR")) {
			      switch(c1.get(Calendar.DAY_OF_WEEK)) {
			        case Calendar.FRIDAY: 
			          c1.add(Calendar.DATE, 2);
			          break;
			        case Calendar.SATURDAY:
			          c1.add(Calendar.DATE, 1);
			          break;
			        default:
			          c1.add(Calendar.DATE, 0);
			      }
		    } else {
			      switch(c1.get(Calendar.DAY_OF_WEEK)) {
			        case Calendar.SATURDAY: 
			          c1.add(Calendar.DATE, 2);
			          break;
			        case Calendar.SUNDAY:
			          c1.add(Calendar.DATE, 1);
			          break;
			        default:
			          c1.add(Calendar.DATE, 0);
			      }
		    }
		    
		    return c1.getTime();
		  }
	
	
	/***
	 * 
	 * @param sortedIOByEntity
	 */
	protected void printRanking(List<DataReport> sortedIOByEntity) {
		int rank = 0;
		System.out.println();
		List<DataReport> lisRanking = sortByDouble( sortedIOByEntity);
		
	    for (DataReport report : lisRanking) {
	      System.out.print(++rank + " ) ");
	      System.out.println(report);
	    }
	}
	
	
	/***
	 * 
	 * @param studlist
	 * @param buySell
	 * @return
	 */
	public Map<String, List<DataReport>> groupByEntity(List<DataReport> studlist, String buySell) {
		Map<String, List<DataReport>> groupedStudents = new HashMap<String, List<DataReport>>();
		for (DataReport report: studlist) {
			if(report.getBuySellIndicator().equalsIgnoreCase(buySell)) {
				String key = report.getEntity();
			    if (groupedStudents.get(key) == null) {
			        groupedStudents.put(key, new ArrayList<DataReport>());
			    }
			    groupedStudents.get(key).add(report);
			}
		}
		
		return groupedStudents;
	}
	
	// Generate outgoing settlement report
	//	  Map<Date, Double> sumOutgoingByDate =
	public Map<Date, Double> groupBuyDate(List<DataReport> studlist) {
		Map<Date, Double> map = new HashMap<Date, Double>();

		for (DataReport student : studlist) {
			Date key  = student.getInstrDate();
		    if(map.containsKey(key)){
		        Double db = map.get(key);
		        double total = db.doubleValue() + student.getUsdAmount();
		        map.put(key, total);
		    }else{
		    	Double db = new Double("0.0");
		    	double total = db.doubleValue() + student.getUsdAmount();
		        map.put(key, total);
		    }

		}
		return map;
	}
	
	public Map<Date, Double> groupSelDate(List<DataReport> studlist) {
		Map<Date, Double> map = new HashMap<Date, Double>();

		for (DataReport student : studlist) {
			Date key  = student.getSetlDate();
		    if(map.containsKey(key)){
		    	Double db = map.get(key);
		        double total = db.doubleValue() + student.getUsdAmount();
		        map.put(key, total);
		    }else{
		    	Double db = new Double("0.0");
		    	double total = db.doubleValue() + student.getUsdAmount();
		        map.put(key, total);
		    }

		}
		return map;
	}
	
	protected Double setFormatAmount(String sValue) {
		String newVar = sValue.replace(",",".");
		Double value = Double.parseDouble(newVar);
		
//		NumberFormat nf = NumberFormat.getInstance();
//		nf.setMinimumFractionDigits(2);

		DecimalFormat nf = (DecimalFormat) DecimalFormat.getInstance(Locale.ENGLISH);
		Double db = new Double( nf.format(value));
		
		return db.doubleValue();
		
	}

}
