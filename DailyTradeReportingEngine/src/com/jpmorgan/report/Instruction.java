package com.jpmorgan.report;

public enum Instruction {
    BUY('B'),
    SELL('S');

    private char instruction;
    Instruction(char instruction) {
        this.instruction = instruction;
    }

}
