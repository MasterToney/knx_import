package Matching.Implementations;

import Config.DimmerMatcherConfig;
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

        if (controlGroupAddresses.containsKey(percentageIdentifier) || controlGroupAddresses.containsKey(increaseDecreaseIdentifier)) {
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

    public static DimmerMatcher BuildDimmerMatcher(DimmerMatcherConfig config) {
        var mappings = new HashMap<String, BiConsumer<DimmerControl, GroupAddress>>();
        var dataPointMappings = new HashMap<String, String>();

        if (config.hasOnOffStatusIdentifier()) {
            mappings.put(config.getOnOffStatusIdentifier(), DimmerControl::setOnOffReadAddress);
            dataPointMappings.put(config.getOnOffStatusIdentifier(), "DPST-1-1");
        }

        if (config.hasOnOffControlIdentifier()) {
            mappings.put(config.getOnOffControlIdentifier(), DimmerControl::setOnOffWriteAddress);
            dataPointMappings.put(config.getOnOffControlIdentifier(), "DPST-1-1");
        }

        if (config.hasPercentageStatusIdentifier()) {
            mappings.put(config.getPercentageStatusIdentifier(), DimmerControl::setPercentageReadAddress);
            dataPointMappings.put(config.getPercentageStatusIdentifier(), "DPST-5-1");
        }

        mappings.put(config.getPercentageControlIdentifier(), DimmerControl::setPercentageWriteAddress);
        dataPointMappings.put(config.getPercentageControlIdentifier(), "DPST-5-1");

        if (config.hasIncreaseDecreaseIdentifier()) {
            mappings.put(config.getIncreaseDecreaseIdentifier(), DimmerControl::setIncreaseDecreaseAddress);
            dataPointMappings.put(config.getIncreaseDecreaseIdentifier(), "DPST-3-1");
        }


        return new DimmerMatcher(dataPointMappings, mappings, config.getPercentageControlIdentifier(), config.getIncreaseDecreaseIdentifier());
    }
}
