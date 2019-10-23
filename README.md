# Knx importer

This project can be used to convert a `.knxproj` file into the OpenHab thing / item format.
It does so by matching the individual group addresses based on their names.
The functions of the group addresses are identified by user provided identifiers.
Those identifiers can be configured using elektra.

## Sample configuration

This is a sample configuration of all values which are currently used:

```
{
    "dimmer": {
        "increaseDecreaseIdentifier": "DIM",
        "onOffControlIdentifier": "LI",
        "onOffStatusIdentifier": "RM LI",
        "percentageControlIdentifier": "WE",
        "percentageStatusIdentifier": "RM WE"
    },
    "rollerShutter": {
        "openCloseSingleStepControlIdentifier": "KZ",
        "openClosedStatusIdentifier": "",
        "positionControlIdentifier": "WE HÖ",
        "positionStatusIdentifier": "RM WE HÖ",
        "stopControlIdentifier": "SP",
        "upDownWriteIdentifier": "LZ"
    },
    "switch": {
        "onOffControlIdentifier": "LI",
        "onOffStatusIdentifier": "RM LI"
    }
}
```

You can save them in a .json file and import them with `kdb import user/knximport yajl < youChoosenConfigFileName.json`

## Requirements

To build this app you need elektra installed on your enviroment as well as the elektra java binding. Instuctions on how to do that can be found[here](https://www.libelektra.org/bindings/jna)

