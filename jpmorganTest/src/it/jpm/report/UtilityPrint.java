package it.jpm.report;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import it.jpm.model.Constant;
import it.jpm.model.DataReport;

public class UtilityPrint {

	public static  DecimalFormat df2 = new DecimalFormat(".##");
			
	/***
	 * 
	 * @param daysDate
	 * @return
	 */
   private static String dateToString(Date daysDate) {
		DateFormat df = new SimpleDateFormat("dd/MMM/yyyy",Locale.ENGLISH);
		return df.format(daysDate);
   }
   
   /***
    * 
    * @param stringBuilder
    * @return
    */
	public static StringBuilder generateTitelReport( StringBuilder stringBuilder) {
	       
        stringBuilder .append("")
                .append("______________________________________________________________________________________________________\n")
                .append("| "+Constant.COL_1 + " ")
                .append("| "+Constant.COL_2 + " ")
                .append("| "+Constant.COL_3 + " ")
                .append("| "+Constant.COL_4 + " ")
                .append("| "+Constant.COL_5 + " ")
                .append("| "+Constant.COL_6 + " ")
                .append("| "+Constant.COL_7 + " ")
                .append("| "+Constant.COL_8 + " ")
                .append("| "+Constant.COL_9 + "|\n")
                .append("------------------------------------------------------------------------------------------------------\n")
                ;

         
        return stringBuilder;
    }
	
	/***
	 * 
	 * @param stringBuilder
	 * @param row
	 * @return
	 */
	public static StringBuilder generateRowReport(StringBuilder stringBuilder, DataReport row) {
	       
        stringBuilder
                .append("_____________________________________________________________________________________________________\n")
                .append("| "+ row.getEntity()                                +" ").append("|")
                .append("    " + row.getBuySellIndicator()                   +"    ").append("|")
                .append("    " + row.getAgreedFx()                           +"  ").append("|")
                .append("   " + row.getCurrency()                            +"   ").append("|")
                .append("  " + dateToString(row.getInstrDate())              +"  ").append("|")
                .append("   " + dateToString(row.getSetlDate())              +"   ").append("|")
                .append("  " + row.getUnits()                                +"  ").append("|")
                .append("    " + row.getPricePerUnit()                       +" ").append("   |")
                .append("    " + df2.format(row.getUsdAmount()) +" ").append("   |")
                .append("\n")
                .append("----------------------------------------------------------------------------------------------------\n");
                
         
        return stringBuilder;
    }
	
	/***
	 * 
	 * @param stringBuilder
	 * @return
	 */
	public static StringBuilder generateFooterReport(StringBuilder stringBuilder) {
		stringBuilder.append("\n") 
		        .append("_______________________________________________________________________________________________________\n");
        return stringBuilder;
    }

}
