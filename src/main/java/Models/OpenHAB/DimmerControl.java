package Models.OpenHAB;

import Models.GroupAddress;
import lombok.Data;


public class DimmerControl extends KnxControl {

    private GroupAddress OnOffWriteAddress;

    private GroupAddress OnOffReadAddress;

    private GroupAddress PercentageWriteAddress;

    private GroupAddress PercentageReadAddress;

    private GroupAddress IncreaseDecreaseAddress;

    public DimmerControl(String name) {
        super(name);
    }


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
}
