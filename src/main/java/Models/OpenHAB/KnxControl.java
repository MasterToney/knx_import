package Models.OpenHAB;

import Models.GroupAddress;

public abstract class KnxControl {

    private final String name;

    private KnxControl() {
        name = "";
    }

    public KnxControl(String name) {
        this.name = name;
    }

    public abstract String toThingFormat();

    public abstract String toItemFormat();

    public abstract String toSitemapFormat();

    public String getNormalizedName() {
        var name = getName().replace(' ', '_').replace('-', '_');
        return name.replaceAll("[^A-Za-z0-9]", "");
    }

    public String getName() {
        return name;
    }

    String writeChannel(GroupAddress write, GroupAddress status, String channelDesignation) {

        var builder = new StringBuilder();
        builder.append(channelDesignation);
        builder.append("=\"");

        if (write != null || status != null) {


            if (write != null) {
                builder.append(write.getAddressFormated());
            }

            if (status != null) {
                builder.append('+');
                builder.append(status.getAddressFormated());
            }

        }
        builder.append("\", ");

        return builder.toString();
    }


    public boolean isValid() {
        if (getNormalizedName().isEmpty() || getNormalizedName().startsWith("RM")) {
            return false;
        }

        char c = getNormalizedName().toLowerCase().charAt(0);
        return c >= 'a' && c <= 'z';
    }

}
