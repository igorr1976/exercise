package com.jpmorgan.report;

public enum Currency {
    AED(1, 5),
    SGP(0, 4),
    SAR(1, 5);

    public int getWorkWeakStart() {
        return workWeakStart;
    }

    public int getWorkWeakEnd() {
        return workWeakEnd;
    }

    int workWeakStart;
    int workWeakEnd;

    Currency(int workWeakStart, int workWeakEnd) {
        this.workWeakStart = workWeakStart;
        this.workWeakEnd = workWeakEnd;
    }

}
