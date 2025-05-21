package com.tls.LiarWire.dataModels;

import lombok.Data;

@Data
public class DelayParameters {

    private int avg;

    private int p90;

    private int p95;

    private int p99;

}
