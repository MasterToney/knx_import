package Models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

/**
 * represents a single group address
 */
@Data @AllArgsConstructor
public class GroupAddress {

    @NonNull
    private String Id;

    private int Address;

    @NonNull
    private String Name;

    private String DatapointType;

    private long Puid;

    private String Description;

}
