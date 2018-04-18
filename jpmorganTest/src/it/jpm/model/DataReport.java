package it.jpm.model;

import java.io.Serializable;
import java.util.Date;

public class DataReport implements Serializable{

	    private String entity;
	    private String buySellIndicator;
	    private double agreedFx;
	    private String currency;
	    private Date instrDate;
	    private Date setlDate;
	    private int units;
	    private double pricePerUnit;
	    private Date actualSetlDate;
	    private double usdAmount;
	    
	    public String getEntity() {
	        return entity;
	      }
	      public void setEntity(String entity) {
	        this.entity = entity;
	      }
	      public String getBuySellIndicator() {
	        return buySellIndicator;
	      }
	      public void setBuySellIndicator(String buySellIndicator) {
	        this.buySellIndicator = buySellIndicator;
	      }
	      public double getAgreedFx() {
	        return agreedFx;
	      }
	      public void setAgreedFx(double agreedFx) {
	        this.agreedFx = agreedFx;
	      }
	      public String getCurrency() {
	        return currency;
	      }
	      public void setCurrency(String currency) {
	        this.currency = currency;
	      }
	      public Date getInstrDate() {
	        return instrDate;
	      }
	      public void setInstrDate(Date instrDate) {
	        this.instrDate = instrDate;
	      }
	      public Date getSetlDate() {
	        return setlDate;
	      }
	      public void setSetlDate(Date setlDate) {
	        this.setlDate = setlDate;
	      }
	      public int getUnits() {
	        return units;
	      }
	      public void setUnits(int units) {
	        this.units = units;
	      }
	      public double getPricePerUnit() {
	        return pricePerUnit;
	      }
	      public void setPricePerUnit(double pricePerUnit) {
	        this.pricePerUnit = pricePerUnit;
	      }
	      public Date getActualSetlDate() {
	        return actualSetlDate;
	      }
	      public void setActualSetlDate(Date actualSetlDate) {
	        this.actualSetlDate = actualSetlDate;
	      }
	      public double getUsdAmount() {
	        return usdAmount;
	      }
	      public void setUsdAmount(double usdAmount) {
	        this.usdAmount = usdAmount;
	      }
	      public String toString() {
	        return ("Entity = " + this.getEntity() 
	                  + " USD Amount = $" + this.getUsdAmount() + "\n");
	      }

}
