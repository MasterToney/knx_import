package Models.OpenHAB;

import Models.GroupAddress;
import lombok.Data;

@Data
public class SwitchControl implements KnxControl {

    private GroupAddress WriteAddress;

    private GroupAddress ReadAddress;

    public String toThingFormat() {

        var result = new StringBuilder();

        result.append("Type switch : ");
        result.append(getNormalizedName());
        result.append(" \"");
        result.append(getWriteAddress().getName());
        result.append("\" [ ga=\"");
        result.append(getWriteAddress().getAddressFormated());

        if (getReadAddress() != null) {
            result.append('+');
            result.append(getReadAddress().getAddressFormated());
        }

        result.append("\" ]");

        // Type switch        : demoSwitch        "Light"       [ ga="3/0/4+<3/0/5" ]

        return result.toString();
    }

    @Override
    public String toItemFormat() {
        var result = new StringBuilder();

        result.append("Switch ");
        result.append(getNormalizedName());
        result.append(" \"");
        result.append(getName());
        result.append("\" { channel=\"knx:device:bridge:generic:");
        result.append(getNormalizedName());
        result.append("\" }");

        // Switch        OG2         "Light [%s]"               <light>          { channel="knx:device:bridge:generic:OG2" }

        return result.toString();
    }

    @Override
    public String toSitemapFormat() {
        var resultStringBuilder = new StringBuilder();

        resultStringBuilder.append("Switch item=");
        resultStringBuilder.append(getNormalizedName());

        return resultStringBuilder.toString();
    }

    @Override
    public String getNormalizedName() {
        var name = getWriteAddress().getName().replace(' ', '_');
        return name.replaceAll("[^A-Za-z0-9]", "");
    }

    @Override
    public String getName() {
        return getWriteAddress().getName();
    }

}
