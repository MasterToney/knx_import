package Matching;

import Models.GroupAddress;
import Models.OpenHAB.KnxControl;

import java.util.List;

public interface ControlMatcher {

    List<KnxControl> ExtractControls(List<GroupAddress> addresses);

}
