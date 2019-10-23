package Config;

import org.libelektra.Key;

public abstract class ConfigurationProvider {

    final static String ImporterBaseString = "user/knximport/";

    abstract protected String getBaseKeyName();

    protected boolean hasValue(Key key) {
        return key != null && !key.isNull();
    }

    protected Key getBaseKey() {
        return Key.create(getBaseKeyName());
    }

}
