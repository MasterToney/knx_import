package Config;

import lombok.Data;
import org.libelektra.KDB;
import org.libelektra.KeySet;

@Data
public class SwitchMatcherConfig extends ConfigurationProvider{

    private String onOffIdentifier;
    private String onOffStatusIdentifier;

    public boolean hasOnOffIdentifier() {
        return onOffIdentifier != null && !onOffIdentifier.isEmpty();
    }

    public boolean hasOnOffStatusIdentifier() {
        return onOffStatusIdentifier != null && !onOffStatusIdentifier.isEmpty();
    }

    protected String getBaseKeyName() {
        return ConfigurationProvider.ImporterBaseString + "switch/";
    }

    public SwitchMatcherConfig getSwitchMatcherConfigFromElektra() {

        try (final KDB kdb = KDB.open(getBaseKey())) {
            var keySet = KeySet.create();

            kdb.get(keySet, getBaseKey());

            var controlKey = keySet.lookup(getBaseKeyName() + "onOffControlIdentifier");
            var statusKey = keySet.lookup(getBaseKeyName() + "onOffStatusIdentifier");

            if (hasValue(controlKey)) {
                var config = new SwitchMatcherConfig();

                config.onOffIdentifier = controlKey.getString();

                if (hasValue(statusKey)){
                    config.onOffStatusIdentifier = statusKey.getString();
                }

                return config;
            }
        } catch (org.libelektra.exception.KDBException e) {
            System.out.println(e);
        }

        return null;
    }
}
