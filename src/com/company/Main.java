package com.company;

import javax.xml.crypto.Data;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class Main {

    public static void main(String[] args) throws ParseException {
        Client client = new Client("Ivan");
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        String newDate = "15/12/2023";
        Date date = formatter.parse(newDate);
	    MainDepositManager manager = new MainDepositManager();
	    manager.addDeposit(client,1100,12,20,300,date,false);
        List<Deposit> deposits = manager.getAllDeposits();
        for (Deposit deposit : deposits) {
            System.out.println(deposit.getAmmount());
        }
        //double earnings = manager.getEarnings(deposits.get(0),date);
        //System.out.println(earnings);
        double earnings = manager.removeDeposit(deposits.get(1),date);
        System.out.println(earnings);
    }
}
