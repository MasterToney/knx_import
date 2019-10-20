package Matching.Implementations;

import Models.GroupAddress;
import Models.OpenHAB.DimmerControl;
import Models.OpenHAB.KnxControl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class DimmerMatcher extends GenericMatcher {

    private Map<String, BiConsumer<DimmerControl, GroupAddress>> setterMappings;

    private final String percentageIdentifier;
    private final String increaseDecreaseIdentifier;

    public DimmerMatcher(Map<String, String> identifiers, Map<String, BiConsumer<DimmerControl, GroupAddress>> setterMappings, String percentageIdentifier, String increaseDecreaseIdentifier) {
        super(identifiers);
        this.setterMappings = setterMappings;
        this.percentageIdentifier = percentageIdentifier;
        this.increaseDecreaseIdentifier = increaseDecreaseIdentifier;
    }

    @Override
    KnxControl buildKnxControl(Map<String, GroupAddress> controlGroupAddresses, List<GroupAddress> groupAddresses) {

        if (controlGroupAddresses.containsKey(percentageIdentifier) || controlGroupAddresses.containsKey(percentageIdentifier)) {
            var control = new DimmerControl(controlGroupAddresses.values().iterator().next().getNameWithoutIdentifier());

            for (var key : controlGroupAddresses.keySet()) {
                var address = controlGroupAddresses.get(key);

                setterMappings.get(key).accept(control, address);
            }

            return control;
        }

        for (var groupAddress : controlGroupAddresses.values()) {
            groupAddresses.add(groupAddress);
        }

        return null;
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

        mappings.put(percentageStatusIdentifier, DimmerControl::setPercentageReadAddress);
        dataPointMappings.put(percentageStatusIdentifier, "DPST-5-1");

        mappings.put(percentageIdentifier, DimmerControl::setPercentageWriteAddress);
        dataPointMappings.put(percentageIdentifier, "DPST-5-1");

        if (increaseDecrease != null && !increaseDecrease.isEmpty()) {
            mappings.put(increaseDecrease, DimmerControl::setIncreaseDecreaseAddress);
            dataPointMappings.put(increaseDecrease, "DPST-3-1");
        }


        return new DimmerMatcher(dataPointMappings, mappings, percentageIdentifier, increaseDecrease);
    }
}
