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
                "    localIp=\"\",\n" +
                "    readingPause=50, \n" +
                "    responseTimeout=10, \n" +
                "    readRetriesLimit=3, \n" +
                "    autoReconnectPeriod=60,\n" +
                "    localSourceAddr=\"0.0.0\"\n" +
                "] {\n" +
                "    Thing device ets [\n" +
                "        address=\"0.0.0\",\n" +
                "        fetch=true,\n" +
                "        pingInterval=300,\n" +
                "        readInterval=3600\n" +
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
