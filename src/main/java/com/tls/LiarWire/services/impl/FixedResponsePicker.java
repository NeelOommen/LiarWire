package com.tls.LiarWire.services.impl;

import com.tls.LiarWire.dataModels.Probability;
import com.tls.LiarWire.services.ProbabilityPicker;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FixedResponsePicker extends ProbabilityPicker {
    @Override
    public <T extends Probability> T pickObject(List<T> listOfObjects) {
        return listOfObjects.getFirst();
    }

    @Override
    public String getType() {
        return "fixedPicker";
    }
}
