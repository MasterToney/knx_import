package Models.OpenHAB;

import Models.GroupAddress;
import lombok.Data;

@Data
public class RollershutterControl implements KnxControl {

    private GroupAddress UpDownWriteAddress;

    private GroupAddress UpDownReadAddress;

    private GroupAddress PositionWriteAddress;

    private GroupAddress PositionReadAddress;

    private GroupAddress StopMoveAddress;

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
