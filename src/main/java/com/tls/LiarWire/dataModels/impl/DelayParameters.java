package com.tls.LiarWire.dataModels.impl;

import lombok.Data;

@Data
public class DelayParameters {

    private String type;

    private int avg;

    private int p90;

    private int p95;

    private int p99;

}
