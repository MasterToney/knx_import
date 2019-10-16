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
        result.append(getName());
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
        result.append(getName());
        result.append(" \"");
        result.append(getWriteAddress().getName());
        result.append("\" { channel=\"knx:device:bridge:generic:");
        result.append(getName());
        result.append("\" }");

        // Switch        OG2         "Light [%s]"               <light>          { channel="knx:device:bridge:generic:OG2" }

        return result.toString();
    }

    @Override
    public String toSitemapFormat() {
        var resultStringBuilder = new StringBuilder();

        resultStringBuilder.append("Switch item=");
        resultStringBuilder.append(getName());

        return resultStringBuilder.toString();
    }

    @Override
    public String getName() {
        return WriteAddress.getName().replace(' ', '_' );
    }

}
