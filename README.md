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

Afterwards you can run `java -jar target/knx_import-1.0-SNAPSHOT.jar demo/sampleProject.knxproj demo/openhabconf/` imports the sample project and take a look at the created items, things and the sitemap.

The generated files can be read by openHAB with the KNX binding installed, simply copy the files in the respective directories or change the output path directly to your openHAB conf folder.

All used KNX group addresses are representative only, so do not expect them to work for your project. Also when you import your KNX project, be sure to properly list the identifiers for the different group addresses. Note that you only need to specify one control identifier per thing for it to be imported, if they are left blank the program won't generate the corresponding items. Say you only want to import on/off switches you only need to specify the key `user/knximport/switch/onOffControlIdentifier` .

## Requirements

For this to app to function you need to have Elektra installed, infos can be found [here](https://www.libelektra.org/docgettingstarted/installation).

The Elektra API is consumed by the jna binding of Elektra which needs to be available in your local maven repository, instuctions on how to do that can be found [here](https://www.libelektra.org/bindings/jna).

Building the app can be done by running `mvn install` in the root folder of the project.
