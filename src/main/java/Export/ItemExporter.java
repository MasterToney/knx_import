package Export;

import Models.OpenHAB.KnxControl;

import java.util.List;

public final class ItemExporter {

    private ItemExporter() { }


    public static String ExportItems(List<KnxControl> controls) {
        var resultStringBuilder = new StringBuilder();

        for (var control: controls) {
            resultStringBuilder.append(control.toItemFormat());
            resultStringBuilder.append("\n");
        }

        return resultStringBuilder.toString();
    }

}
