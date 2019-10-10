package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * represents a single group address
 */
@Data
@AllArgsConstructor
public class GroupAddress {

    @NonNull
    private String Id;

    private int Address;

    @NonNull
    private String Name;

    private String DatapointType;

    private long Puid;

    private String Description;

    public GroupAddress clone() {
        return new GroupAddress(Id, Address, Name, DatapointType, Puid, Description);
    }

    public String getAddressFormated() {
        var result = new StringBuilder();

        var partOne = Address / 65536;
        var partTwo = (Address % 65536) / 256;
        var partThree = Address % 256;

        result.append(partOne);
        result.append('/');
        result.append(partTwo);
        result.append('/');
        result.append(partThree);

        return result.toString();
    }

    // TODO write Name to itemName function which only includes alphanumerical characters and underscores, see https://www.openhab.org/docs/configuration/items.html#name for limitations
}
