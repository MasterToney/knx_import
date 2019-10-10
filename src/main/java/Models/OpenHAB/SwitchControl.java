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
        result.append(getWriteAddress().getName().replace(" ", ""));
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

    // TODO write to Item format function


}
