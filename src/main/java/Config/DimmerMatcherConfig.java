package Config;

import lombok.Data;
import org.libelektra.KDB;
import org.libelektra.KeySet;

@Data
public class DimmerMatcherConfig extends ConfigurationProvider{

    private String onOffControlIdentifier;
    private String onOffStatusIdentifier;
    private String percentageControlIdentifier;
    private String percentageStatusIdentifier;
    private String increaseDecreaseIdentifier;

    public boolean hasOnOffControlIdentifier() {
        return onOffControlIdentifier != null && !onOffControlIdentifier.isEmpty();
    }

    public boolean hasOnOffStatusIdentifier() {
        return onOffStatusIdentifier != null && !onOffStatusIdentifier.isEmpty();
    }

    public boolean hasPercentageControlIdentifier() {
        return percentageControlIdentifier != null && !percentageControlIdentifier.isEmpty();
    }

    public boolean hasPercentageStatusIdentifier() {
        return percentageStatusIdentifier != null && !percentageStatusIdentifier.isEmpty();
    }

    public boolean hasIncreaseDecreaseIdentifier() {
        return increaseDecreaseIdentifier != null && !increaseDecreaseIdentifier.isEmpty();
    }

    protected String getBaseKeyName() {
        return ConfigurationProvider.ImporterBaseString + "dimmer/";
    }

    public DimmerMatcherConfig getDimmerMatcherConfigFromElektra() {

        try (final KDB kdb = KDB.open(getBaseKey())) {
            var keySet = KeySet.create();

            kdb.get(keySet, getBaseKey());

            var onOffControlKey = keySet.lookup(getBaseKeyName() + "onOffControlIdentifier");
            var onOffStatusKey = keySet.lookup(getBaseKeyName() + "onOffStatusIdentifier");
            var percentageControlKey = keySet.lookup(getBaseKeyName() + "percentageControlIdentifier");
            var percentageStatusKey = keySet.lookup(getBaseKeyName() + "percentageStatusIdentifier");
            var increaseDecreaseKey = keySet.lookup(getBaseKeyName() + "increaseDecreaseIdentifier");

            var config = new DimmerMatcherConfig();

            if (hasValue(onOffControlKey)) {
                config.onOffControlIdentifier = onOffControlKey.getString();
            }

            if (hasValue(onOffStatusKey)) {
                config.onOffControlIdentifier = onOffControlKey.getString();
            }

            if (hasValue(percentageControlKey)) {
                config.percentageControlIdentifier = percentageControlKey.getString();
            }

            if (hasValue(percentageStatusKey)) {
                config.percentageStatusIdentifier= percentageStatusKey.getString();
            }

            if (hasValue(increaseDecreaseKey)) {
                config.increaseDecreaseIdentifier = increaseDecreaseKey.getString();
            }

            return config;

        } catch (org.libelektra.exception.KDBException e) {
            System.out.println(e);
        }

        return null;
    }
}
