package Models.OpenHAB;

import Models.GroupAddress;

public class RollershutterControl extends KnxControl {

    private GroupAddress UpDownWriteAddress;

    private GroupAddress UpDownSingleStepWriteAddress;

    private GroupAddress OpenClosedReadAddress;

    private GroupAddress PositionWriteAddress;

    private GroupAddress PositionReadAddress;

    private GroupAddress StopMoveAddress;

    public RollershutterControl(String name) {
        super(name);
    }


    @Override
    public String toThingFormat() {
        if (!isComplete()) {
            return "";
        }

        // Type rollershutter : demoRollershutter "Shade"       [ upDown="4/3/50+4/3/51", stopMove="4/3/52+4/3/53", position="4/3/54+<4/3/55" ]

        var resultStringBuilder = new StringBuilder();

        resultStringBuilder.append("Type rollershutter : ");
        resultStringBuilder.append(getNormalizedName());
        resultStringBuilder.append(" [ ");

        resultStringBuilder.append(writeChannel(UpDownWriteAddress, OpenClosedReadAddress, "upDown"));

        resultStringBuilder.append(writeChannel(StopMoveAddress, null, "stopMove"));

        resultStringBuilder.append(writeChannel(PositionWriteAddress, PositionReadAddress, "position"));

        return resultStringBuilder.substring(0, resultStringBuilder.length() - 2) + " ]";
    }



    @Override
    public String toItemFormat() {
        if (!isComplete()) {
            return "";
        }

        // Rollershutter demoRollershutter  "Shade [%d %%]"            <rollershutter>  { channel="knx:device:bridge:generic:demoRollershutter" }

        return "Rollershutter " + getNormalizedName() + " \"" + getName() + "\" <rollershutter> { channel=\"knx:device:bridge:ets:" + getNormalizedName() + "\" }";
    }

    @Override
    public String toSitemapFormat() {
        if (!isComplete()) {
            return "";
        }

        // Switch item=demoRollershutter

        return "Switch item=" + getNormalizedName();
    }

    private boolean isComplete() {
        return UpDownWriteAddress != null || OpenClosedReadAddress != null || PositionWriteAddress != null || PositionReadAddress != null;
    }

    public void setUpDownWriteAddress(GroupAddress upDownWriteAddress) {
        UpDownWriteAddress = upDownWriteAddress;
    }

    public void setUpDownSingleStepWriteAddress(GroupAddress upDownSingleStepWriteAddress) {
        UpDownSingleStepWriteAddress = upDownSingleStepWriteAddress;
    }

    public void setOpenClosedReadAddress(GroupAddress openClosedReadAddress) {
        OpenClosedReadAddress = openClosedReadAddress;
    }

    public void setPositionWriteAddress(GroupAddress positionWriteAddress) {
        PositionWriteAddress = positionWriteAddress;
    }

    public void setPositionReadAddress(GroupAddress positionReadAddress) {
        PositionReadAddress = positionReadAddress;
    }

    public void setStopMoveAddress(GroupAddress stopMoveAddress) {
        StopMoveAddress = stopMoveAddress;
    }
}
