package com.tls.LiarWire.services;

import com.tls.LiarWire.dataModels.Probability;

import java.util.List;

public abstract class ProbabilityPicker {

    public abstract <T extends Probability> T pickObject(List<T> listOfObjects);

    public abstract String getType();

}
