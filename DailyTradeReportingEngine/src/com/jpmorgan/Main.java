package com.jpmorgan;

import com.jpmorgan.report.Data;
import com.jpmorgan.report.Instruction;
import com.jpmorgan.report.Report;

import java.util.*;

public class Main {

    private static int DATA_SIZE = 100;
    private static List<String> entities = Arrays.asList(new String[]{"foo", "bar"});
    private static Calendar calendar = Calendar.getInstance();


    public static void main(String[] args) {
        Report report = new Report(initData());
        report.reportAmount();
        report.reportRanking();
    }

    private static List<Data> initData() {
        double agreeFx = 0.5d;
        int units = 0;
        double pricePerUnit;

        List<Data> dataList = new ArrayList<Data>(DATA_SIZE);
        Calendar curCalendar = Calendar.getInstance();
        curCalendar.add(Calendar.DATE, -DATA_SIZE);


        for (int i = 0; i < DATA_SIZE; i++) {

            for (com.jpmorgan.report.Currency currency : com.jpmorgan.report.Currency.values()) {
                for (String entity : entities) {
                    String[] period = getDates(curCalendar.getTime());
                    units = units + 100;
                    pricePerUnit = units + agreeFx;
                    Data dataBuy = new Data(entity, Instruction.BUY, agreeFx, currency, period[0], period[1], units, pricePerUnit);
                    units = units + 100;
                    pricePerUnit = units + agreeFx;
                    Data dataSell = new Data(entity, Instruction.SELL, agreeFx, currency, period[0], period[1], units, pricePerUnit);
                    dataList.add(dataBuy);
                    dataList.add(dataSell);
                }
            }
            curCalendar.add(Calendar.DATE, 1);
        }

        return dataList;
    }


    private static String[] getDates(Date date) {
        String[] result = new String[2];

        calendar.setTime(date);
        result[0] = Report.getStrFromDate(calendar.getTime());

        calendar.add(Calendar.DATE, 1);
        result[1] = Report.getStrFromDate(calendar.getTime());
        return result;
    }

}
