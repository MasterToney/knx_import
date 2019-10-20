package Parser;

import Models.GroupAddress;
import org.w3c.dom.Node;

public abstract class GroupAddressFactory {

    public static GroupAddress CreateGroupAddressFromNode(Node node) {

        var id = getId(node);
        var address = getAddress(node);
        var name = getName(node);
        var datapointType = getDatapointType(node);
        var puid = getPuid(node);
        var description = getDescription(node);


        if (id.isEmpty() || address == -1 || name.isEmpty()) {
            return null;
        } else {
            return new GroupAddress(id, address, name, "", datapointType, puid, description);
        }
    }

    private static String getId(Node node) {

        var item = node.getAttributes().getNamedItem("Id");

        if (item != null) {
            return item.getNodeValue();
        }
        return "";
    }

    private static String getDatapointType(Node node) {

        var item = node.getAttributes().getNamedItem("DatapointType");

        if (item != null) {
            return item.getNodeValue();
        }

        return "";
    }

    private static int getAddress(Node node) {

        var item = node.getAttributes().getNamedItem("Address");

        if (item != null) {
            return Integer.parseInt(item.getNodeValue());
        }
        return -1;
    }

    private static String getName(Node node) {

        var item = node.getAttributes().getNamedItem("Name");

        if (item != null) {
            return item.getNodeValue();
        }
        return "";
    }

    private static String getDescription(Node node) {

        var item = node.getAttributes().getNamedItem("Description");

        if (item != null) {
            return item.getNodeValue();
        }

        return "";
    }

    private static long getPuid(Node node) {

        var item = node.getAttributes().getNamedItem("Puid");

        if (item != null) {
            return Long.parseLong(item.getNodeValue());
        }

        return -1;
    }

}
