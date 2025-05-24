package com.tls.LiarWire.factories;

import com.tls.LiarWire.services.ProbabilityPicker;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
@Slf4j
public class ResponsePickerFactory {

    private final Map<String, ProbabilityPicker> pickerMap;

    public ResponsePickerFactory(List<ProbabilityPicker> processors) {
        this.pickerMap = processors.stream()
                .collect(Collectors.toMap(ProbabilityPicker::getType, Function.identity()));
        log.info("Response Pickers Available: {}", this.pickerMap.size());
    }

    public ProbabilityPicker getProcessor(String type) {
        return pickerMap.get(type);
    }

}
