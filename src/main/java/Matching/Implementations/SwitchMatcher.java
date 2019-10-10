package Matching.Implementations;

import Matching.ControlMatcher;
import Models.GroupAddress;
import Models.OpenHAB.KnxControl;
import Models.OpenHAB.SwitchControl;

import java.util.LinkedList;
import java.util.List;

public class SwitchMatcher implements ControlMatcher {

    private final String controlDesignation;
    private final String statusDesignation;

    public SwitchMatcher(String controlDesignation, String statusDesignation) {
        this.controlDesignation = controlDesignation;
        this.statusDesignation = statusDesignation;
    }

    @Override
    public List<KnxControl> ExtractControls(List<GroupAddress> addresses) {

        var onOffAddresses = new LinkedList<GroupAddress>();
        var statusAddresses = new LinkedList<GroupAddress>();


        for (var groupAddress : addresses) {

            if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(groupAddress.getName(), statusDesignation)) {

                statusAddresses.add(groupAddress.clone());
            } else if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(groupAddress.getName(), controlDesignation)) {

                onOffAddresses.add(groupAddress.clone());
            }
        }

        onOffAddresses.stream().forEach(address -> address.setName(address.getName().replace(controlDesignation, "").trim()));
        statusAddresses.stream().forEach(address -> address.setName(address.getName().replace(statusDesignation, "").trim()));

        var resultControls = BuildControlsFromAddresses(onOffAddresses, statusAddresses, addresses);

        return resultControls;
    }

    private List<KnxControl> BuildControlsFromAddresses(List<GroupAddress> controlAddresses, List<GroupAddress> statusAddresses, List<GroupAddress> all) {
        var resultControls = new LinkedList<KnxControl>();


        var iter = statusAddresses.listIterator();
        while (iter.hasNext()) {
            var statusAddress = iter.next();
            var nameIdentifier = statusAddress.getName().replace(statusDesignation, "").trim();

            var controlAddress = controlAddresses.stream()
                    .filter(ad -> org.apache.commons.lang3.StringUtils.containsIgnoreCase(ad.getName(), nameIdentifier))
                    .findAny().orElse(null);

            if (controlAddress != null) {
                var switchControl = new SwitchControl();
                switchControl.setWriteAddress(controlAddress);
                switchControl.setReadAddress(statusAddress);

                resultControls.add(switchControl);

                all.remove(statusAddress);
                all.remove(controlAddress);

                controlAddresses.remove(controlAddress);
                iter.remove();
            }
        }

        var resultSize = resultControls.size();
        System.out.printf("Found %d matches for switches with status group addresses\n", resultControls.size());

        iter = controlAddresses.listIterator();
        while (iter.hasNext()) {
            var controlAddress = iter.next();
            var nameIdentifier = controlAddress.getName().replace(controlDesignation, "").trim();

            var statusAddress = statusAddresses.stream()
                    .filter(ad -> org.apache.commons.lang3.StringUtils.containsIgnoreCase(ad.getName(), nameIdentifier))
                    .findAny().orElse(null);

            var switchControl = new SwitchControl();
            switchControl.setWriteAddress(controlAddress);

            if (statusAddress != null) {
                switchControl.setReadAddress(controlAddress);

                resultControls.add(switchControl);

                all.remove(statusAddress);

                statusAddresses.remove(controlAddress);
            }
            iter.remove();
            all.remove(controlAddress);

            resultControls.add(switchControl);
        }

        System.out.printf("Found %d matches for switches without status group addresses\n", resultControls.size() - resultSize);

        return resultControls;
    }

}
