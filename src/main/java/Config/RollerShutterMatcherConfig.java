package Config;

import lombok.Data;
import org.libelektra.KDB;
import org.libelektra.KeySet;

@Data
public class RollerShutterMatcherConfig extends ConfigurationProvider{

    private String upDownWriteIdentifier;
    private String openClosedReadIdentifier;
    private String positionWriteIdentifier;
    private String positionReadIdentifier;
    private String stopWriteIdentifier;
    private String openCloseSingleStepWriteIdentifier;

    public boolean hasUpDownControlIdentifier() {
        return upDownWriteIdentifier != null && !upDownWriteIdentifier.isEmpty();
    }

    public boolean hasOpenClosedReadIdentifier() {
        return openClosedReadIdentifier != null && !openClosedReadIdentifier.isEmpty();
    }

    public boolean hasPositionWriteIdentifier() {
        return positionWriteIdentifier != null && !positionWriteIdentifier.isEmpty();
    }

    public boolean hasPositionReadIdentifier() {
        return positionReadIdentifier != null && !positionReadIdentifier.isEmpty();
    }

    public boolean hasStopWriteIdentifier() {
        return stopWriteIdentifier != null && !stopWriteIdentifier.isEmpty();
    }

    public boolean hasOpenCloseSingleStepWriteIdentifier() {
        return openCloseSingleStepWriteIdentifier != null && openCloseSingleStepWriteIdentifier.isEmpty();
    }

    protected String getBaseKeyName() {
        return ConfigurationProvider.ImporterBaseString + "rollerShutter/";
    }

    public RollerShutterMatcherConfig getRollerShutterMatcherConfigFromElektra() {

        try (final KDB kdb = KDB.open(getBaseKey())) {
            var keySet = KeySet.create();

            kdb.get(keySet, getBaseKey());

            var upDownControlKey = keySet.lookup(getBaseKeyName() + "upDownWriteIdentifier");
            var openClosedStatusKey = keySet.lookup(getBaseKeyName() + "openClosedStatusIdentifier");
            var positionControlKey = keySet.lookup(getBaseKeyName() + "positionControlIdentifier");
            var positionStatusKey = keySet.lookup(getBaseKeyName() + "positionStatusIdentifier");
            var stopControlKey = keySet.lookup(getBaseKeyName() + "stopControlIdentifier");
            var openCloseSingleStepControlKey = keySet.lookup(getBaseKeyName() + "openCloseSingleStepControlIdentifier");

            var config = new RollerShutterMatcherConfig();

            if (hasValue(upDownControlKey)) {
                config.upDownWriteIdentifier = upDownControlKey.getString();
            }

            if (hasValue(openClosedStatusKey)) {
                config.openClosedReadIdentifier = openClosedStatusKey.getString();
            }

            if (hasValue(positionControlKey)) {
                config.positionWriteIdentifier = positionControlKey.getString();
            }

            if (hasValue(positionStatusKey)) {
                config.positionReadIdentifier = positionStatusKey.getString();
            }

            if (hasValue(stopControlKey)) {
                config.stopWriteIdentifier = stopControlKey.getString();
            }

            if (hasValue(openCloseSingleStepControlKey)) {
                config.openCloseSingleStepWriteIdentifier = openCloseSingleStepControlKey.getString();
            }

            return config;

        } catch (org.libelektra.exception.KDBException e) {
            System.out.println(e);
        }

        return null;
    }
}
