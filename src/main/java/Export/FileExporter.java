package Export;

import Models.OpenHAB.KnxControl;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public final class FileExporter {

    private FileExporter() { }

    public static void WriteToFile(Path file, String content) {

        try {
            Files.write(file, content.getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void WriteImportedConfiguration(Path confDirectoryPath, List<KnxControl> knxControls, String importName) {

        WriteItemsFile(confDirectoryPath, knxControls, importName);
        WriteThingsFile(confDirectoryPath, knxControls, importName);
        WriteSitemapFile(confDirectoryPath, knxControls, importName);

    }

    private static void WriteSitemapFile(Path confDirectoryPath, List<KnxControl> knxControls, String fileName) {

        var itemsFilePath = Paths.get(confDirectoryPath.toString(), "sitemaps", fileName + ".sitemap");

        WriteToFile(itemsFilePath, SitemapExporter.ExportSitemap(knxControls, fileName));
    }

    private static void WriteItemsFile(Path confDirectoryPath, List<KnxControl> knxControls, String fileName) {

        var itemsFilePath = Paths.get(confDirectoryPath.toString(), "items", fileName + ".items");

        WriteToFile(itemsFilePath, ItemExporter.ExportItems(knxControls));
    }

    private static void WriteThingsFile(Path confDirectoryPath, List<KnxControl> knxControls, String fileName) {

        var itemsFilePath = Paths.get(confDirectoryPath.toString(), "things", fileName + ".things");


        WriteToFile(itemsFilePath, ThingExporter.ExportThings(knxControls));
    }

}
