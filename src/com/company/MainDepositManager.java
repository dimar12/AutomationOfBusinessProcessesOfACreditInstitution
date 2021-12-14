package com.company;

import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.*;

public class MainDepositManager implements DepositManager{
    /*
       Метод добавляет в систему информацию о новом вкладе
     * @param pretermPercent процент при досрочном изъятии вклада
     */
    public Deposit addDeposit(Client client, double ammount, double percent, double pretermPercent, int termDays, Date startDate, boolean withPercentCapitalization){
        Deposit deposit = new Deposit(client.getId(), ammount, percent, pretermPercent, termDays, startDate, withPercentCapitalization);
        //Записываем новый вклад в csv файл
        CSV csv = new CSV("deposit.csv");
        csv.writeDeposit(deposit,true);
        return deposit;
    }

    /*
      Метод возвращает список вкладов клиента
    */
    public List<Deposit> getClientDeposits(Client client){
        List<Deposit> allDeposits = getAllDeposits(); //Получаем список всех вкладов приндых банком
        List<Deposit> clientDeposits = new ArrayList<>();
        //Среде всех вкладов находим вклады принадлежащие клиенту
        for (Deposit deposit : allDeposits) {
            if(client.getId().equals(deposit.getClientId())){
                clientDeposits.add(deposit);
            }
        }
        return clientDeposits;
    }

    /*
      Метод возвращает список всех вкладов принятых банком
     */
    public List<Deposit> getAllDeposits(){
        CSV csv = new CSV("deposit.csv");
        return csv.getAllDeposits();
    }


    /*
      Метод возвращает текущий доход по вкладу
     */
    public double getEarnings(Deposit deposit, Date currentDate){
        long days = Duration.between(deposit.getStartDate().toInstant(), currentDate.toInstant()).toDays();//Разница в днях между датами открытия вклада и текущей
        double Earnings;
        //Начисления процентов по вкладам с капитализацией по сложной формуле: S=P*(1+I:100:K)^t-P
        if (deposit.getWithPercentCapitalization()){
            Earnings = deposit.getAmmount()*Math.pow((1+deposit.getPercent()/100/365),days)-deposit.getAmmount();
            Earnings = Math.round(Earnings * 100.0) / 100.0;//Округление до 2 знака после запятой
        }
        //Начисления процентов по вкладам без капитализации по простой формуле: S=(P*I*t:K):100
        else {
            Earnings = (deposit.getAmmount()*deposit.getPercent()*days/365)/100;
            Earnings = Math.round(Earnings * 100.0) / 100.0;
        }
        return Earnings;
    }

    /*
      Метод удаляет запись о вкладе и возвращает сумму к выплате в кассе. Если вклад закрывается досрочно, то сумма к выплате рассчитывается исходя из процента при досрочном изъятии.
     */
    public double removeDeposit(Deposit deposit, Date closeDate){
        double amountToBePaid;
        long days = Duration.between(deposit.getStartDate().toInstant(), closeDate.toInstant()).toDays();
        //Сумма к выплате
        if(days>=deposit.getTermDays()){
            amountToBePaid = deposit.getAmmount() + getEarnings(deposit, closeDate);
        }
        //Сумма к выплате при досрочном изятии
        else {
            //Начисления процентов по вкладам с капитализацией по сложной формуле: S=P*(1+I:100:K)^t-P
            if (deposit.getWithPercentCapitalization()){
                amountToBePaid = deposit.getAmmount() + deposit.getAmmount()*Math.pow((1+deposit.getPretermPercent()/100/365),days)-deposit.getAmmount();
                amountToBePaid = Math.round(amountToBePaid * 100.0) / 100.0;
            }
            //Начисления процентов по вкладам без капитализации по простой формуле: S=(P*I*t:K):100
            else {
                amountToBePaid = deposit.getAmmount() + (deposit.getAmmount()*deposit.getPretermPercent()*days/365)/100;
                amountToBePaid = Math.round(amountToBePaid * 100.0) / 100.0;
            }
        }
        List<Deposit> deposits = getAllDeposits();//Получаем список всех вкладов

        //Удаляем нужную запись
        Iterator<Deposit> it = deposits.iterator();
        while (it.hasNext()) {
            if (it.next().getId().equals(deposit.getId())) {
                it.remove();
                break;
            }
        }
        //Очищаем csv файл
        CSV csv = new CSV("deposit.csv");
        csv.clear();
        //Записываем в csv файл список вкладов без удалённой строки
        for (Deposit depositToWrite : deposits) {
            csv.writeDeposit(depositToWrite,true);
        }
        return amountToBePaid;
    }
}
