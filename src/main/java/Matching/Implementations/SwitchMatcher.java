package Matching.Implementations;

import Config.SwitchMatcherConfig;
import Models.GroupAddress;
import Models.OpenHAB.KnxControl;
import Models.OpenHAB.SwitchControl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

public class SwitchMatcher extends GenericMatcher{

    private Map<String, BiConsumer<SwitchControl, GroupAddress>> setterMappings;

    private final String onOffIdentifier;


    public SwitchMatcher(Map<String, String> identifiers, Map<String, BiConsumer<SwitchControl, GroupAddress>> setterMappings, String onOffIdentifier) {
        super(identifiers);
        this.setterMappings = setterMappings;
        this.onOffIdentifier = onOffIdentifier;
    }

    @Override
    KnxControl buildKnxControl(Map<String, GroupAddress> controlGroupAddresses, List<GroupAddress> groupAddresses) {

        if (controlGroupAddresses.containsKey(onOffIdentifier)) {
            var control = new SwitchControl(controlGroupAddresses.values().iterator().next().getNameWithoutIdentifier());

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

    public static SwitchMatcher BuildSwitchMatcher(SwitchMatcherConfig config) {
        var mappings = new HashMap<String, BiConsumer<SwitchControl, GroupAddress>>();
        var dataPointMappings = new HashMap<String, String>();

        if (config.hasOnOffStatusIdentifier()) {
            mappings.put(config.getOnOffStatusIdentifier(), SwitchControl::setReadAddress);
            dataPointMappings.put(config.getOnOffStatusIdentifier(), "DPST-1-1");
        }

        if (config.hasOnOffIdentifier()) {
            mappings.put(config.getOnOffIdentifier(), SwitchControl::setWriteAddress);
            dataPointMappings.put(config.getOnOffIdentifier(), "DPST-1-1");
        }

        return new SwitchMatcher(dataPointMappings, mappings, config.getOnOffIdentifier());
    }
}
