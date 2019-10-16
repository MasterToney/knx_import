package Export;

import Models.OpenHAB.KnxControl;

import java.util.List;

public final class SitemapExporter {

    private SitemapExporter() {}

    public static String ExportSitemap(List<KnxControl> controls) {
        var resultStringBuilder = new StringBuilder();

        resultStringBuilder.append("sitemap knx label=\"KNX Import Sitemap\" {\n" +
                "  Frame label=\"Imported Items\" {\n");

        for (var control: controls) {
            resultStringBuilder.append("    ");
            resultStringBuilder.append(control.toSitemapFormat());
            resultStringBuilder.append("\n");
        }

        resultStringBuilder.append("  }\n}");

        return resultStringBuilder.toString();
    }

}
