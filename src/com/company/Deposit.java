package com.company;

import java.util.Date;
import java.util.UUID;

public class Deposit {
    private String id;
    private String clientId;
    private double ammount;
    private double percent;
    private double pretermPercent;
    private int termDays;
    private Date startDate;
    private boolean withPercentCapitalization;

   public Deposit(String id, String clientId, double ammount, double percent, double pretermPercent, int termDays, Date startDate, boolean withPercentCapitalization){
       this.id = id;
       this.clientId = clientId;
       this.ammount = ammount;
       this.percent = percent;
       this.pretermPercent = pretermPercent;
       this.termDays = termDays;
       this.startDate = startDate;
       this.withPercentCapitalization = withPercentCapitalization;
   }
    public Deposit(String clientId, double ammount, double percent, double pretermPercent, int termDays, Date startDate, boolean withPercentCapitalization){
        this.id = UUID.randomUUID().toString().replace("-", "");
        this.clientId = clientId;
        this.ammount = ammount;
        this.percent = percent;
        this.pretermPercent = pretermPercent;
        this.termDays = termDays;
        this.startDate = startDate;
        this.withPercentCapitalization = withPercentCapitalization;
    }

    public String getId(){
        return this.id;
    }

    public void setId(String id){
        this.id = id;
    }

    public String getClientId(){
        return this.clientId;
    }

    public void setClientId(String clientIdid){
        this.clientId = clientIdid;
    }

    public double getAmmount(){
        return this.ammount;
    }

    public void setAmmount(double ammount){
        this.ammount = ammount;
    }

    public double getPercent(){
        return this.percent;
    }

    public void setPercent(double percent){
        this.percent = percent;
    }

    public double getPretermPercent(){
        return this.pretermPercent;
    }

    public void setPretermPercent(double pretermPercent){
        this.pretermPercent = pretermPercent;
    }

    public double getTermDays(){
        return this.termDays;
    }

    public void setTermDays(int termDays){
        this.termDays = termDays;
    }

    public Date getStartDate(){
        return this.startDate;
    }

    public void setStartDate(Date startDate){
        this.startDate = startDate;
    }

    public boolean getWithPercentCapitalization(){
        return this.withPercentCapitalization;
    }

    public void setWithPercentCapitalization(boolean withPercentCapitalization){
        this.withPercentCapitalization = withPercentCapitalization;
    }
}
