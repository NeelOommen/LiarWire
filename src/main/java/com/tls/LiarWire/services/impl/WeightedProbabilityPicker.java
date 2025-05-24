package com.tls.LiarWire.services.impl;

import com.tls.LiarWire.dataModels.Probability;
import com.tls.LiarWire.services.ProbabilityPicker;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class WeightedProbabilityPicker extends ProbabilityPicker {
    @Override
    public <T extends Probability> T pickObject(List<T> listOfObjects) {
        double totalWeight = listOfObjects.stream().mapToDouble(T::getProbability).sum();

        int idx = 0;
        for (double r = Math.random() * totalWeight; idx < listOfObjects.size() - 1; ++idx) {
            r -= listOfObjects.get(idx).getProbability();
            if (r <= 0.0) break;
        }

       return listOfObjects.get(idx);
    }

    @Override
    public String getType() {
        return "weightedProbabilityPicker";
    }
}
