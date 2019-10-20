package Export;

import Models.OpenHAB.KnxControl;
import Util.AlphanumComparator;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public final class ThingExporter {

    private ThingExporter() { }

    public static String ExportThings(List<KnxControl> controls) {
        var result = new StringBuilder();

        result.append(
                "Bridge knx:ip:bridge [\n" +
                "    type=\"TUNNEL\", \n" +
                "    ipAddress=\"192.168.0.xxx\", \n" +
                "    portNumber=3671, \n" +
                "    autoReconnectPeriod=60\n" +
                "] {\n" +
                "    Thing device ets [\n" +
                "    ] {\n");

        var sortedControls = controls.stream()
                .sorted(Comparator.comparing(KnxControl::getNormalizedName, new AlphanumComparator()))
                .collect(Collectors.toList());

        for (var control: sortedControls) {
            if (!control.isValid()) {
                continue;
            }

            result.append("        ");
            result.append(control.toThingFormat());
            result.append("\n");
        }

        result.append("    }\n}\n");

        return result.toString();
    }

}
