package Models.OpenHAB;

import Models.GroupAddress;

public class SwitchControl extends KnxControl {

    private GroupAddress writeAddress;

    private GroupAddress readAddress;

    public SwitchControl(String name) {
        super(name);
    }

    public String toThingFormat() {

        var result = new StringBuilder();

        result.append("Type switch : ");
        result.append(getNormalizedName());

        result.append(" [ ");

        result.append(writeChannel(writeAddress, readAddress, "ga"));

        // Type switch        : demoSwitch        "Light"       [ ga="3/0/4+<3/0/5" ]

        return result.substring(0, result.length() - 2) + " ]";
    }

    @Override
    public String toItemFormat() {

        // Switch        OG2         "Light [%s]"               <light>          { channel="knx:device:bridge:generic:OG2" }

        return "Switch " + getNormalizedName() + " \"" + getName() + "\" { channel=\"knx:device:bridge:ets:" + getNormalizedName() + "\" }";
    }

    @Override
    public String toSitemapFormat() {
        return "Switch item=" + getNormalizedName();
    }


    public void setWriteAddress(GroupAddress writeAddress) {
        this.writeAddress = writeAddress;
    }

    public void setReadAddress(GroupAddress readAddress) {
        this.readAddress = readAddress;
    }
}
