package com.company;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class CSV {
    private String file;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
    public CSV(String file){
        this.file = file;
    }
    //Метод записывает вклад в строку csv файла
    public void writeDeposit(Deposit deposit,boolean append) {
        try (
                FileWriter writer = new FileWriter(new File(file), append)) {
            StringBuilder sb = new StringBuilder();
            sb.append(deposit.getId());
            sb.append(',');
            sb.append(deposit.getClientId());
            sb.append(',');
            sb.append(deposit.getAmmount());
            sb.append(',');
            sb.append(deposit.getPercent());
            sb.append(',');
            sb.append(deposit.getPretermPercent());
            sb.append(',');
            sb.append(deposit.getTermDays());
            sb.append(',');
            sb.append(formatter.format(deposit.getStartDate()));
            sb.append(',');
            sb.append(deposit.getWithPercentCapitalization());
            sb.append('\n');
            writer.write(sb.toString());

        } catch (
                IOException e) {
            System.out.println(e.getMessage());
        }
    }

    //Метод удаляет все записи csv файла без удаления файла
    public void clear() {
        try (FileWriter writer = new FileWriter(new File(file))) {
            writer.write("");
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }
    //Считываем все вклады из csv файла
    public List<Deposit> getAllDeposits() {
        List<Deposit> deposits = new ArrayList<>();
        try (Scanner scanner = new Scanner(new File(file))) {
            //Проходим до конца файла считывая каждую строку
            while (scanner.hasNextLine()) {
                deposits.add(getRecordFromLine(scanner.nextLine()));//Получем вклад из строки
            }
        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }
        return deposits;
    }

    //Метод считывает строку и возвращает вклад
    private Deposit getRecordFromLine(String line) throws ParseException {
        List<String> values = new ArrayList<String>();
        //Получаем список значений записанных в строку
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(",");
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        //Получаем вклад записаный в строке
        return new Deposit(values.get(0), values.get(1), Double.parseDouble(values.get(2)), Double.parseDouble(values.get(3)), Double.parseDouble(values.get(4)), Integer.valueOf(values.get(5)), formatter.parse(values.get(6)), Boolean.parseBoolean(values.get(7)));
    }

    public String getFile(){
        return file;
    }

    public void setFile(String file){
        this.file = file;
    }
}
