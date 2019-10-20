package Matching.Implementations;

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

    public static SwitchMatcher BuildSwitchMatcher(String onOffIdentifier, String onOffStatusIdentifier) {
        var mappings = new HashMap<String, BiConsumer<SwitchControl, GroupAddress>>();
        var dataPointMappings = new HashMap<String, String>();

        if (onOffStatusIdentifier != null && !onOffStatusIdentifier.isEmpty()) {
            mappings.put(onOffStatusIdentifier, SwitchControl::setWriteAddress);
            dataPointMappings.put(onOffStatusIdentifier, "DPST-1-1");
        }

        if (onOffIdentifier != null && !onOffIdentifier.isEmpty()) {
            mappings.put(onOffIdentifier, SwitchControl::setReadAddress);
            dataPointMappings.put(onOffIdentifier, "DPST-1-1");
        }

        return new SwitchMatcher(dataPointMappings, mappings, onOffIdentifier);
    }
}
