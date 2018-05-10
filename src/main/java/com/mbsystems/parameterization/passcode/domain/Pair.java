package com.mbsystems.parameterization.passcode.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Pair {
    private int valueA;
    private int valueB;

    public int getSum() {
        return this.valueA + this.valueB;
    }
}
