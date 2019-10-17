package Models.OpenHAB;

import Models.GroupAddress;
import lombok.Data;

@Data
public class DimmerControl implements KnxControl {

    private GroupAddress SwitchWriteAddress;

    private GroupAddress SwitchReadAddress;

    private GroupAddress PositionWriteAddress;

    private GroupAddress PositionReadAddress;

    private GroupAddress IncreaseDecreaseAddress;


    @Override
    public String toThingFormat() {
        return null;
    }

    @Override
    public String toItemFormat() {
        return null;
    }

    @Override
    public String toSitemapFormat() {
        return null;
    }

    @Override
    public String getNormalizedName() {
        return null;
    }

    @Override
    public String getName() {
        return null;
    }
}
