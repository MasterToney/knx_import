package Matching.Implementations;

import Models.GroupAddress;
import Models.OpenHAB.KnxControl;

import java.util.*;

public abstract class GenericMatcher {

    private Map<String, Map<String, GroupAddress>> identifiedAddresses = new HashMap<>(); // key is entry from identifiers, value is list of all matched addresses

    private Map<String, String> addressTypeIdentifiers = new HashMap<>();
    private List<KnxControl> controls = new LinkedList<>();


    public GenericMatcher(Map<String, String> identifiers) {

        for (var entry : identifiers.entrySet()) {
            addressTypeIdentifiers.put(entry.getKey(), entry.getValue());
        }
    }

    public List<KnxControl> ExtractControls(List<GroupAddress> addresses) {

        sortGroupAddresses(addresses);

        for (var itemName : identifiedAddresses.keySet()) {
            var control = buildKnxControl(identifiedAddresses.get(itemName), addresses);

            if (control != null) {
                controls.add(control);
            }
        }

        return controls;
    }

    private void sortGroupAddresses(List<GroupAddress> addresses) {

        var iter = addresses.listIterator();

        while (iter.hasNext()) {
            var address = iter.next();

            for (var designation : addressTypeIdentifiers.entrySet()) {

                if (org.apache.commons.lang3.StringUtils.containsIgnoreCase(address.getName(), designation.getKey())
                        && address.equalsDataPoint(designation.getValue())) {

                    address.setNameWithoutIdentifier(address.getName().replace(designation.getKey(), "").trim());

                    if (identifiedAddresses.containsKey(address.getNameWithoutIdentifier())) {
                        identifiedAddresses.get(address.getNameWithoutIdentifier()).put(designation.getKey(), address);
                    } else {
                        identifiedAddresses.put(address.getNameWithoutIdentifier(), new HashMap<>() {{
                            put(designation.getKey(), address);
                        }});
                    }

                    iter.remove();
                    break; // we found a match for this groupaddress, therefore don't check to match with other designations
                }
            }
        }
    }

    abstract KnxControl buildKnxControl(Map<String, GroupAddress> controlGroupAddresses, List<GroupAddress> groupAddresses);
}
