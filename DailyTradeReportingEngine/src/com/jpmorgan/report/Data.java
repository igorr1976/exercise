package com.jpmorgan.report;

public class Data {

    private String entity;
    private Instruction instruction;
    private double agreeFx;
    private Currency currency;
    private String instructionDate;
    private String settlementDate;
    private int units;
    private double pricePerUnit;

    public Data(String entity, Instruction instruction, double agreeFx, Currency currency, String instructionDate, String settlementDate, int units, double pricePerUnit) {
        this.entity = entity;
        this.instruction = instruction;
        this.agreeFx = agreeFx;
        this.currency = currency;
        this.instructionDate = instructionDate;
        this.settlementDate = settlementDate;
        this.units = units;
        this.pricePerUnit = pricePerUnit;
    }

    public String getEntity() {
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public Instruction getInstruction() {
        return instruction;
    }

    public void setInstruction(Instruction instruction) {
        this.instruction = instruction;
    }

    public double getAgreeFx() {
        return agreeFx;
    }

    public void setAgreeFx(double agreeFx) {
        this.agreeFx = agreeFx;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getInstructionDate() {
        return instructionDate;
    }

    public void setInstructionDate(String instructionDate) {
        this.instructionDate = instructionDate;
    }

    public String getSettlementDate() {
        return settlementDate;
    }

    public void setSettlementDate(String settlementDate) {
        this.settlementDate = settlementDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Data data = (Data) o;

        if (Double.compare(data.agreeFx, agreeFx) != 0) return false;
        if (Double.compare(data.pricePerUnit, pricePerUnit) != 0) return false;
        if (units != data.units) return false;
        if (currency != data.currency) return false;
        if (entity != null ? !entity.equals(data.entity) : data.entity != null) return false;
        if (instruction != data.instruction) return false;
        if (instructionDate != null ? !instructionDate.equals(data.instructionDate) : data.instructionDate != null)
            return false;
        if (settlementDate != null ? !settlementDate.equals(data.settlementDate) : data.settlementDate != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = entity != null ? entity.hashCode() : 0;
        result = 31 * result + (instruction != null ? instruction.hashCode() : 0);
        temp = Double.doubleToLongBits(agreeFx);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (currency != null ? currency.hashCode() : 0);
        result = 31 * result + (instructionDate != null ? instructionDate.hashCode() : 0);
        result = 31 * result + (settlementDate != null ? settlementDate.hashCode() : 0);
        result = 31 * result + units;
        temp = Double.doubleToLongBits(pricePerUnit);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }

    @Override
    public String toString() {
        return "Data{" +
                "entity='" + entity + '\'' +
                ", instruction=" + instruction +
                ", agreeFx=" + agreeFx +
                ", currency=" + currency +
                ", instructionDate='" + instructionDate + '\'' +
                ", settlementDate='" + settlementDate + '\'' +
                ", units=" + units +
                ", pricePerUnit=" + pricePerUnit +
                '}';
    }
}
