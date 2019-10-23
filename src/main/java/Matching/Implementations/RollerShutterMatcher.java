package Matching.Implementations;

import Config.RollerShutterMatcherConfig;
import Models.GroupAddress;
import Models.OpenHAB.KnxControl;
import Models.OpenHAB.RollershutterControl;

import java.util.*;
import java.util.function.BiConsumer;

public class RollerShutterMatcher extends GenericMatcher{

    private Map<String, BiConsumer<RollershutterControl, GroupAddress>> setterMappings;

    public RollerShutterMatcher(Map<String, String> identifiers, Map<String, BiConsumer<RollershutterControl, GroupAddress>> setterMappings) {
        super(identifiers);
        this.setterMappings = setterMappings;
    }

    @Override
    KnxControl buildKnxControl(Map<String, GroupAddress> controlGroupAddresses, List<GroupAddress> groupAddresses) {
        var control = new RollershutterControl(controlGroupAddresses.values().iterator().next().getNameWithoutIdentifier());

        for (var key: controlGroupAddresses.keySet()) {
            var address = controlGroupAddresses.get(key);

            setterMappings.get(key).accept(control, address);
        }

        return control;
    }

    public static RollerShutterMatcher BuildRollershutterMatcher(RollerShutterMatcherConfig config) {
        var mappings = new HashMap<String, BiConsumer<RollershutterControl, GroupAddress>>();
        var dataPointMappings = new HashMap<String, String>();

        if (config.hasUpDownControlIdentifier()) {
            mappings.put(config.getUpDownWriteIdentifier(), RollershutterControl::setUpDownWriteAddress);
            dataPointMappings.put(config.getUpDownWriteIdentifier(), "DPST-1-8");
        }

        if (config.hasOpenClosedReadIdentifier()) {
            mappings.put(config.getOpenClosedReadIdentifier(), RollershutterControl::setOpenClosedReadAddress);
            dataPointMappings.put(config.getOpenClosedReadIdentifier(), "DPST-1-9");
        }

        if (config.hasPositionReadIdentifier()) {
            mappings.put(config.getPositionReadIdentifier(), RollershutterControl::setPositionReadAddress);
            dataPointMappings.put(config.getPositionReadIdentifier(), "DPST-5-1");
        }

        if (config.hasPositionWriteIdentifier()) {
            mappings.put(config.getPositionWriteIdentifier(), RollershutterControl::setPositionWriteAddress);
            dataPointMappings.put(config.getPositionWriteIdentifier(), "DPST-5-1");
        }

        if (config.hasStopWriteIdentifier()) {
            mappings.put(config.getStopWriteIdentifier(), RollershutterControl::setStopMoveAddress);
            dataPointMappings.put(config.getStopWriteIdentifier(), "DPST-1-10");
        }

        if (config.hasOpenCloseSingleStepWriteIdentifier()) {
            mappings.put(config.getOpenCloseSingleStepWriteIdentifier(), RollershutterControl::setUpDownSingleStepWriteAddress);
            dataPointMappings.put(config.getOpenCloseSingleStepWriteIdentifier(), "DPST-1-7");
        }

        return new RollerShutterMatcher(dataPointMappings, mappings);
    }
}
