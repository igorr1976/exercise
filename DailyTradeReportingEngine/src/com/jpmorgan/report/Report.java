package com.jpmorgan.report;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Report {

    private static DateFormat dateFormat;
    private static Comparator<Data> amountComparator;
    private static Comparator<Data> rankingComparator;
    private static Calendar calendar;

    static {
        dateFormat = new SimpleDateFormat("dd MMM yyyy", Locale.ENGLISH);
        calendar = Calendar.getInstance();
        amountComparator = new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                if (o1.getInstruction() == Instruction.SELL && o2.getInstruction() == Instruction.BUY) return -1;
                if (o1.getInstruction() != o2.getInstruction()) return 1;

                Date d1 = getDateFromString(o1.getSettlementDate());
                Date d2 = getDateFromString(o2.getSettlementDate());

                return d1.compareTo(d2);
            }
        };

        rankingComparator = new Comparator<Data>() {
            @Override
            public int compare(Data o1, Data o2) {
                return o1.getEntity().compareTo(o2.getEntity());
            }
        };
    }

    private List<Data> dataList;

    public Report(List<Data> data) {
        this.dataList = data;
        checkSettlementDates();
    }

    private double amount(Data data) {
        return data.getAgreeFx() * data.getPricePerUnit() * data.getUnits();
    }

    public void reportRanking() {
        if (dataList == null || dataList.size() == 0) return;

        Collections.sort(dataList, rankingComparator);

        String entity = null;
        Double sellAmount = 0d;
        Double buyAmount = 0d;

        printReport("Ranking of entities");

        for (Data data : dataList) {
            boolean isEntityChanged = entity != null && !entity.equals(data.getEntity());
            if (isEntityChanged) {
                printReport(String.format("%s %d %.2f %s %.2f", entity, sellAmount > buyAmount ? 1 : 0, sellAmount, sellAmount > buyAmount ? ">" : "<=", buyAmount));
                sellAmount = 0d;
                buyAmount = 0d;
            }
            entity = data.getEntity();
            if (data.getInstruction() == Instruction.BUY) {
                buyAmount = buyAmount + amount(data);
            } else {
                sellAmount = sellAmount + amount(data);
            }
        }

        printReport(String.format("%s %d %.2f %s %.2f", entity, sellAmount > buyAmount ? 1 : 0, sellAmount, sellAmount > buyAmount ? ">" : "<=", buyAmount));
    }

    public void reportAmount() {
        if (dataList == null || dataList.size() == 0) return;

        Collections.sort(dataList, amountComparator);
        String day = null;
        Instruction instruction = null;
        Double amount = 0d;
        for (Data data : dataList) {
            boolean isDayChanged = day != null && !day.equals(data.getSettlementDate());
            boolean isInstructionChanged = instruction != null && instruction != data.getInstruction();

            if (isDayChanged || isInstructionChanged) {
                printReport(String.format("%s %.2f", day, amount));
                amount = 0d;
            }
            if (instruction == null || isInstructionChanged) {
                printReport(String.format("Amount in USD settled %s every day", data.getInstruction().name()));
            }
            instruction = data.getInstruction();
            day = data.getSettlementDate();

            amount = amount + amount(data);

        }

        printReport(String.format("%s %.2f", day, amount));
    }

    private static void printReport(String str) {
        System.out.println(str);
    }


    public static Date getDateFromString(String date) {
        try {
            return dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getStrFromDate(Date date) {
        return dateFormat.format(date);
    }

    private void checkSettlementDates() {
        if (dataList == null || dataList.size() == 0) return;
        for (Data data : dataList) {
            checkSettlementDate(data);
        }

    }

    private static void checkSettlementDate(Data data) {
        Date settlementDate = getDateFromString(data.getSettlementDate());
        Currency currency = data.getCurrency();
        calendar.setTime(settlementDate);

        int week = calendar.get(Calendar.DAY_OF_WEEK);
        if (week >= currency.getWorkWeakStart() && week <= currency.getWorkWeakEnd()) return;

        while (week < currency.getWorkWeakStart() || week > currency.getWorkWeakEnd()) {
            calendar.add(Calendar.DATE, 1);
            week = calendar.get(Calendar.DAY_OF_WEEK);
        }
        data.setSettlementDate(getStrFromDate(calendar.getTime()));
    }

}
