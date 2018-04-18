package it.jpm.report;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import it.jpm.model.CompareDate;
import it.jpm.model.Constant;
import it.jpm.model.CoparetorReport;
import it.jpm.model.DataReport;

/***
 * 
 * @author User
 *
 */
public class JPExecutionReport extends UtilityHelp {

	private List<DataReport> dataListBuy;
	private List<DataReport> dataListSell;

	
	public JPExecutionReport() {}

	/***
	 * 
	 */
	public void loadData() {
		System.out.println( " loadData - I will use a default file in C:\\Data.csv ");
		try {
		
			loadFromCSV() ;
			printIncoming();
			printOutgoing();
			
		
		} catch (ParseException e) {
		 
			e.printStackTrace();
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
		
	}
	
	/***
	 * 
	 * @param fileData
	 */
public void loadData(String fileData) {
        
		try {
		
			loadFromCSV(fileData) ;
			printIncoming();
			printOutgoing();
			
		
		} catch (ParseException e) {
		 
			e.printStackTrace();
		} catch (IOException e) {
			 
			e.printStackTrace();
		}
		
	}
	
	/***
	 * 
	 * @param bs
	 */
	public void printIncoming() {
		
			
			System.out.println(UtilityPrint.generateTitelReport(new StringBuilder()));
			
			//Collections.sort(getDataListBuy() , new CoparetorReport() ); 
			for(DataReport report:getDataListBuy()) {
				System.out.println(UtilityPrint.generateRowReport(new StringBuilder(), report));
			}
		
			System.out.println("1) Amount in USD settled incoming everyday");
			Map<Date, Double> mapSel=groupBuyDate(getDataListBuy());
			printReport(mapSel);
			
			System.out.println("Ranking of entities based on incoming amount. ");
			Collections.reverse(getDataListBuy());
			printRanking(getDataListBuy());
			System.out.println(UtilityPrint.generateFooterReport(new StringBuilder()));
		
	}
	
	/***
	 * 
	 * @param bs
	 */
	public void printOutgoing() {
		
			
			System.out.println(UtilityPrint.generateTitelReport(new StringBuilder()));
			Collections.sort(getDataListSell() , new CoparetorReport() ); 
			for(DataReport report:getDataListSell()) {
				System.out.println(UtilityPrint.generateRowReport(new StringBuilder(), report));
			}

			System.out.println("2) Amount in USD settled outgoing everyday");
			Map<Date, Double> mapSel = groupSelDate(getDataListSell());
			printReport(mapSel);

			System.out.println("Ranking of entities based on  outgoing amount. ");
			Collections.reverse(getDataListBuy());
			printRanking(getDataListSell());
			System.out.println(UtilityPrint.generateFooterReport(new StringBuilder()));
		
	}
	
	private void printReport(Map<Date, Double> sumOutgoingByDate) {
		DecimalFormat dform = new DecimalFormat(".##");
		
		Iterator<Entry<Date, Double>> iter = null;
		StringBuilder sb = new StringBuilder();
        iter = sumOutgoingByDate.entrySet().iterator();
        while (iter.hasNext()) {
          Entry<Date, Double> entry = iter.next();
          
          sb.append(convertDateInToString(entry.getKey()));
          sb.append(" = ").append('$');
          sb.append(dform.format(entry.getValue()));
          
          if (iter.hasNext()) {
            sb.append('\n');
          }
        
        }
        System.out.println(sb.toString());
        
	}
	
	
	
	
	/***
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	private void loadFromCSV() throws ParseException, IOException {
		    String csvFile="C:\\Data.csv";
		    
		 	String csvSplitBy = ",";
		    String line = "";
		    String[] lineValue = new String[8];
		    Date setlDate = null;
		    double usdAmt = 0.000;
		    DataReport innerObj = null;
		    DateFormat df = new SimpleDateFormat(Constant.DT_FORMAT_II,Locale.ENGLISH);
		    List<DataReport> dataListForBuy = new ArrayList<DataReport>();
		    List<DataReport> dataListForSell = new ArrayList<DataReport>();
		    BufferedReader br = new BufferedReader(new FileReader(csvFile));
		    br.readLine();
		    
		    while ((line = br.readLine()) != null) {
		      lineValue = line.split(csvSplitBy);
		      
		      setlDate = selectSettlementDate (lineValue[5], lineValue[3]);
		      usdAmt = calculateAmount(lineValue[7], lineValue[6], lineValue[2]);
		      
		      innerObj = new DataReport();
		      innerObj.setUsdAmount(usdAmt);
		      if (lineValue[1].equalsIgnoreCase(Constant.BUY_FORMAT)){
			        innerObj.setEntity(lineValue[0]);
			        innerObj.setBuySellIndicator(lineValue[1]);
			        innerObj.setAgreedFx(Double.parseDouble(lineValue[2]));
			        innerObj.setCurrency(lineValue[3]);
			        innerObj.setInstrDate(convertStringInToDate(lineValue[4]));
			        innerObj.setSetlDate(convertStringInToDate(lineValue[5]));
			        innerObj.setUnits(Integer.parseInt(lineValue[6]));
			        innerObj.setPricePerUnit(Double.parseDouble(lineValue[7]));
			        innerObj.setActualSetlDate(setlDate);
			        dataListForBuy.add(innerObj);
		      } else if (lineValue[1].equalsIgnoreCase(Constant.SEL_FORMAT)){
			    	innerObj.setEntity(lineValue[0]);
			        innerObj.setBuySellIndicator(lineValue[1]);
			        innerObj.setAgreedFx(Double.parseDouble(lineValue[2]));
			        innerObj.setCurrency(lineValue[3]);
			        innerObj.setInstrDate(convertStringInToDate(lineValue[4]));
			        innerObj.setSetlDate(convertStringInToDate(lineValue[5]));
			        innerObj.setUnits(Integer.parseInt(lineValue[6]));
			        innerObj.setPricePerUnit(Double.parseDouble(lineValue[7]));
			        innerObj.setActualSetlDate(setlDate);
			        dataListForSell.add(innerObj);
		      }else {
		    	  System.out.println("Uncorrect indicator action");
		      }
		      
		    }
		    
		    if(!dataListForBuy.isEmpty()) {
		        Collections.sort(dataListForBuy , new CompareDate() ); 
		    	this.setDataListBuy(dataListForBuy);
		    }
		   
		    //Collections.sort(dataListForSell , new CoparetorReport() ); 
		    if(!dataListForSell.isEmpty()) {
		    	Collections.sort(dataListForSell , new CompareDate() ); 
		    	this.setDataListSell(dataListForSell);
		    }
	}

	/***
	 * 
	 * @throws ParseException
	 * @throws IOException
	 */
	private void loadFromCSV(String csvFile) throws ParseException, IOException {
		  
			String csvSplitBy = ",";
		    String line = "";
		    String[] lineValue = new String[8];
		    Date setlDate = null;
		    double usdAmt = 0.00;
		    DataReport innerObj = null;
		    DateFormat df = new SimpleDateFormat(Constant.DT_FORMAT_II,Locale.ENGLISH);
		    List<DataReport> dataListForBuy = new ArrayList<DataReport>();
		    List<DataReport> dataListForSell = new ArrayList<DataReport>();
		    BufferedReader br = new BufferedReader(new FileReader(csvFile));
		    br.readLine();
		    
		    while ((line = br.readLine()) != null) {
		      lineValue = line.split(csvSplitBy);
		      
		      setlDate = selectSettlementDate (lineValue[5], lineValue[3]);
		      usdAmt = calculateAmount(lineValue[7], lineValue[6], lineValue[2]);
		      
		      innerObj = new DataReport();
		      innerObj.setUsdAmount(usdAmt);
		      if (lineValue[1].equalsIgnoreCase(Constant.BUY_FORMAT)){
			        innerObj.setEntity(lineValue[0]);
			        innerObj.setBuySellIndicator(lineValue[1]);
			        innerObj.setAgreedFx(Double.parseDouble(lineValue[2]));
			        innerObj.setCurrency(lineValue[3]);
			        innerObj.setInstrDate(convertStringInToDate(lineValue[4]));
			        innerObj.setSetlDate(convertStringInToDate(lineValue[5]));
			        innerObj.setUnits(Integer.parseInt(lineValue[6]));
			        innerObj.setPricePerUnit(Double.parseDouble(lineValue[7]));
			        innerObj.setActualSetlDate(setlDate);
			        dataListForBuy.add(innerObj);
		      } else if (lineValue[1].equalsIgnoreCase(Constant.SEL_FORMAT)){
			    	innerObj.setEntity(lineValue[0]);
			        innerObj.setBuySellIndicator(lineValue[1]);
			        innerObj.setAgreedFx(Double.parseDouble(lineValue[2]));
			        innerObj.setCurrency(lineValue[3]);
			        innerObj.setInstrDate(convertStringInToDate(lineValue[4]));
			        innerObj.setSetlDate(convertStringInToDate(lineValue[5]));
			        innerObj.setUnits(Integer.parseInt(lineValue[6]));
			        innerObj.setPricePerUnit(Double.parseDouble(lineValue[7]));
			        innerObj.setActualSetlDate(setlDate);
			        dataListForSell.add(innerObj);
		      }else {
		    	  System.out.println("Uncorrect indicator action");
		      }
		      
		    }
		    
		    if(!dataListForBuy.isEmpty()) {
		        Collections.sort(dataListForBuy , new CompareDate() ); 
		    	this.setDataListBuy(dataListForBuy);
		    }
		    //Collections.sort(dataListForSell , new CoparetorReport() ); 
		    if(!dataListForSell.isEmpty()) {
		    	Collections.sort(dataListForSell , new CompareDate() ); 
		    	this.setDataListSell(dataListForSell);
		    }
	}

	/***
	 * 
	 * @return
	 */
	public List<DataReport> getDataListBuy() {
		return dataListBuy;
	}

	/***
	 * 
	 * @param dataListBuy
	 */
	public void setDataListBuy(List<DataReport> dataListBuy) {
		
		this.dataListBuy = dataListBuy;
	}

	/***
	 * 
	 * @return
	 */
	public List<DataReport> getDataListSell() {
		return dataListSell;
	}

	/***
	 * 
	 * @param dataListSell
	 */
	public void setDataListSell(List<DataReport> dataListSell) {
		this.dataListSell = dataListSell;
	}


}
