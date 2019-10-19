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
        if (!isComplete()) {
            return "";
        }

        // Type dimmer        : demoDimmer        "Dimmer"      [ switch="5/0/0+<5/0/1", position="5/0/2+<5/0/3", increaseDecrease="5/0/4" ]

        var resultStringBuilder = new StringBuilder();

        resultStringBuilder.append("Type dimmer : ");
        resultStringBuilder.append(getNormalizedName());
        resultStringBuilder.append(" [ ");

        resultStringBuilder.append(writeChannel(OnOffWriteAddress, OnOffReadAddress, "switch"));

        resultStringBuilder.append(writeChannel(PercentageWriteAddress, PercentageReadAddress, "position"));

        resultStringBuilder.append(writeChannel(IncreaseDecreaseAddress, null, "increaseDecrease"));


        return resultStringBuilder.substring(0, resultStringBuilder.length() - 2) + " ]";
    }

    @Override
    public String toItemFormat() {
        if (!isComplete()) {
            return "";
        }

        return "Dimmer " + getNormalizedName() + " \"" + getName() + "\" <light> { channel=\"knx:device:bridge:ets:" + getNormalizedName() + "\" }";
    }

    @Override
    public String toSitemapFormat() {
        if (!isComplete()) {
            return "";
        }

        return "Slider item=" + getNormalizedName();
    }


    private boolean isComplete() {
        return OnOffWriteAddress != null || OnOffReadAddress != null || PercentageWriteAddress != null || PercentageReadAddress != null;
    }

    public void setOnOffWriteAddress(GroupAddress onOffWriteAddress) {
        OnOffWriteAddress = onOffWriteAddress;
    }

    public void setOnOffReadAddress(GroupAddress onOffReadAddress) {
        OnOffReadAddress = onOffReadAddress;
    }

    public void setPercentageWriteAddress(GroupAddress percentageWriteAddress) {
        PercentageWriteAddress = percentageWriteAddress;
    }

    public void setPercentageReadAddress(GroupAddress percentageReadAddress) {
        PercentageReadAddress = percentageReadAddress;
    }

    public void setIncreaseDecreaseAddress(GroupAddress increaseDecreaseAddress) {
        IncreaseDecreaseAddress = increaseDecreaseAddress;
    }
}
