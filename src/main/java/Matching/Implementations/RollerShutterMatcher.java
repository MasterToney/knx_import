package Matching.Implementations;

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
    KnxControl buildKnxControl(Map<String, GroupAddress> controlGroupAddresses) {
        var control = new RollershutterControl(controlGroupAddresses.values().iterator().next().getName());

        for (var key: controlGroupAddresses.keySet()) {
            var address = controlGroupAddresses.get(key);

            setterMappings.get(key).accept(control, address);
        }

        return control;
    }

    public static RollerShutterMatcher BuildRollershutterMatcher(String upDownWriteAddress, String openClosedReadAddress, String positionWriteAddress, String positionReadAddress, String stopWriteAddress, String openCloseSingleStepWriteAddress) {
        var mappings = new HashMap<String, BiConsumer<RollershutterControl, GroupAddress>>();
        var dataPointMappings = new HashMap<String, String>();

        if (upDownWriteAddress != null && !upDownWriteAddress.isEmpty()) {
            mappings.put(upDownWriteAddress, RollershutterControl::setUpDownWriteAddress);
            dataPointMappings.put(upDownWriteAddress, "DPST-1-8");
        }

        if (openClosedReadAddress != null && !openClosedReadAddress.isEmpty()) {
            mappings.put(openClosedReadAddress, RollershutterControl::setOpenClosedReadAddress);
            dataPointMappings.put(openClosedReadAddress, "DPST-1-9");
        }

        if (positionWriteAddress != null && !positionWriteAddress.isEmpty()) {
            mappings.put(positionWriteAddress, RollershutterControl::setPositionWriteAddress);
            dataPointMappings.put(positionWriteAddress, "DPST-5-1");
        }

        if (positionReadAddress != null && !positionReadAddress.isEmpty()) {
            mappings.put(positionReadAddress, RollershutterControl::setPositionReadAddress);
            dataPointMappings.put(positionReadAddress, "DPST-5-1");
        }

        if (stopWriteAddress != null && !stopWriteAddress.isEmpty()) {
            mappings.put(stopWriteAddress, RollershutterControl::setStopMoveAddress);
            dataPointMappings.put(stopWriteAddress, "DPST-1-10");
        }

        if (openCloseSingleStepWriteAddress != null && !openCloseSingleStepWriteAddress.isEmpty()) {
            mappings.put(openCloseSingleStepWriteAddress, RollershutterControl::setUpDownSingleStepWriteAddress);
            dataPointMappings.put(openCloseSingleStepWriteAddress, "DPST-1-7");
        }

        return new RollerShutterMatcher(dataPointMappings, mappings);
    }
}
