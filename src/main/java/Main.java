import Config.DimmerMatcherConfig;
import Config.RollerShutterMatcherConfig;
import Config.SwitchMatcherConfig;
import Export.FileExporter;
import Import.EtsImport;
import Import.Importer;
import Matching.Implementations.DimmerMatcher;
import Matching.Implementations.RollerShutterMatcher;
import Matching.Implementations.SwitchMatcher;
import Models.ImportException;
import Models.OpenHAB.KnxControl;

import java.nio.file.Paths;
import java.util.LinkedList;

public class Main {

    public static void main(String[] args) {

        if (args.length != 2) {

            System.out.println("USAGE: Main etsproject.knxproj openhab/conf/");
            System.exit(0);
        }

        var importPath = args[0];
        var confDirectoryString = args[1];
        var confDirectory = Paths.get(confDirectoryString);

        EtsImport etsImporter = new Importer();

        try {
            var parsedDocument = etsImporter.ImportEtsFile(importPath);
            var groupAddressList = etsImporter.ExtractGroupAddresses(parsedDocument);
            var controls = new LinkedList<KnxControl>();


            var switchMatcherConfig = (new SwitchMatcherConfig()).getSwitchMatcherConfigFromElektra();
            if (switchMatcherConfig != null) {

                var switchMatcher = SwitchMatcher.BuildSwitchMatcher(switchMatcherConfig);
                var switches = switchMatcher.ExtractControls(groupAddressList);
                controls.addAll(switches);
            }

            var rollerShutterMatcherConfig = (new RollerShutterMatcherConfig()).getRollerShutterMatcherConfigFromElektra();

            if (rollerShutterMatcherConfig != null) {

                var rollerShutterMatcher = RollerShutterMatcher.BuildRollershutterMatcher(rollerShutterMatcherConfig);
                var rollerShutters = rollerShutterMatcher.ExtractControls(groupAddressList);
                controls.addAll(rollerShutters);
            }

            var dimmerMatcherConfig = (new DimmerMatcherConfig()).getDimmerMatcherConfigFromElektra();
            if (dimmerMatcherConfig != null) {

                var dimmerMatcher = DimmerMatcher.BuildDimmerMatcher(dimmerMatcherConfig);
                var dimmers = dimmerMatcher.ExtractControls(groupAddressList);
                controls.addAll(dimmers);
            }

            FileExporter.WriteImportedConfiguration(confDirectory, controls, "knximport");

        } catch (ImportException e) {
            e.printStackTrace();
        }
    }
}
