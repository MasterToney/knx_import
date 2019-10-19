package Matching.Implementations;

import Models.GroupAddress;
import Models.OpenHAB.DimmerControl;
import Models.OpenHAB.KnxControl;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

public class DimmerMatcher extends GenericMatcher {

    private Map<String, BiConsumer<DimmerControl, GroupAddress>> setterMappings;

    public DimmerMatcher(Map<String, String> identifiers, Map<String, BiConsumer<DimmerControl, GroupAddress>> setterMappings) {
        super(identifiers);
        this.setterMappings = setterMappings;
    }

    @Override
    KnxControl buildKnxControl(Map<String, GroupAddress> controlGroupAddresses) {
        var control = new DimmerControl(controlGroupAddresses.values().iterator().next().getName());

        for (var key: controlGroupAddresses.keySet()) {
            var address = controlGroupAddresses.get(key);

            setterMappings.get(key).accept(control, address);
        }

        return control;
    }

    public static DimmerMatcher BuildDimmerMatcher(String onOffIdentifier, String onOffStatusIdentifier, String percentageIdentifier, String percentageStatusIdentifier, String increaseDecrease) {
        var mappings = new HashMap<String, BiConsumer<DimmerControl, GroupAddress>>();
        var dataPointMappings = new HashMap<String, String>();

        if (onOffStatusIdentifier != null && !onOffStatusIdentifier.isEmpty()) {
            mappings.put(onOffStatusIdentifier, DimmerControl::setOnOffReadAddress);
            dataPointMappings.put(onOffStatusIdentifier, "DPST-1-1");
        }

        if (onOffIdentifier != null && !onOffIdentifier.isEmpty()) {
            mappings.put(onOffIdentifier, DimmerControl::setOnOffWriteAddress);
            dataPointMappings.put(onOffIdentifier, "DPST-1-1");
        }

        if (percentageStatusIdentifier != null && !percentageStatusIdentifier.isEmpty()) {
            mappings.put(percentageStatusIdentifier, DimmerControl::setPercentageReadAddress);
            dataPointMappings.put(percentageStatusIdentifier, "DPST-5-1");
        }

        if (percentageIdentifier != null && !percentageIdentifier.isEmpty()) {
            mappings.put(percentageIdentifier, DimmerControl::setPercentageWriteAddress);
            dataPointMappings.put(percentageIdentifier, "DPST-5-1");
        }
        
        if (increaseDecrease != null && !increaseDecrease.isEmpty()) {
            mappings.put(increaseDecrease, DimmerControl::setIncreaseDecreaseAddress);
            dataPointMappings.put(increaseDecrease, "DPST-3-1");
        }


        return new DimmerMatcher(dataPointMappings, mappings);
    }
}
