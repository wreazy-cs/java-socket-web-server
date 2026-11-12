package webserver.page;

/**
 * {@link Profile} bilgisini renkli ve biçimlendirilmiş bir HTML sayfasına çevirir.
 * Sayfada H1 (ad soyad), H2 (öğrenci no), biyografi, beceri listesi ve basit bir ürün kataloğu tablosu bulunur.
 */
public class HtmlPageRenderer implements PageRenderer {

    private static final String STYLE = """
            body {
                margin: 0;
                padding: 40px 16px;
                min-height: 100vh;
                box-sizing: border-box;
                background: linear-gradient(135deg, #e3f2fd, #f3e5f5);
                font-family: Georgia, 'Times New Roman', serif;
                color: #263238;
            }
            .card {
                max-width: 760px;
                margin: 0 auto;
                padding: 32px 40px;
                background: #ffffff;
                border-top: 8px solid #0d47a1;
                border-radius: 10px;
                box-shadow: 0 6px 24px rgba(0, 0, 0, 0.15);
            }
            h1 {
                margin: 0;
                font-family: 'Trebuchet MS', Arial, sans-serif;
                color: #0d47a1;
            }
            h2 {
                margin: 8px 0 4px;
                font-family: Verdana, Geneva, sans-serif;
                color: #d84315;
            }
            .bolum {
                margin: 0 0 16px;
                font-style: italic;
                color: #607d8b;
            }
            h3 {
                font-family: 'Trebuchet MS', Arial, sans-serif;
                color: #00695c;
                border-bottom: 2px solid #b2dfdb;
                padding-bottom: 4px;
            }
            p {
                line-height: 1.7;
                text-align: justify;
            }
            p:first-of-type::first-letter {
                font-size: 2em;
                font-weight: bold;
                color: #0d47a1;
            }
            strong {
                color: #c62828;
            }
            ul {
                list-style: none;
                padding: 0;
                display: flex;
                flex-wrap: wrap;
                gap: 8px;
            }
            li {
                padding: 4px 12px;
                background: #e8f5e9;
                color: #1b5e20;
                border: 1px solid #a5d6a7;
                border-radius: 14px;
                font-family: 'Courier New', monospace;
            }
            table {
                width: 100%;
                border-collapse: collapse;
                font-family: Arial, Helvetica, sans-serif;
                font-size: 15px;
            }
            th {
                text-align: left;
                background: #0d47a1;
                color: #ffffff;
                padding: 8px 12px;
            }
            td {
                padding: 8px 12px;
                border-bottom: 1px solid #e0e0e0;
            }
            tr:nth-child(even) td {
                background: #f5f9ff;
            }
            td.fiyat {
                font-weight: bold;
                color: #d84315;
                white-space: nowrap;
            }
            .alt {
                margin-top: 24px;
                font-family: Arial, Helvetica, sans-serif;
                font-size: 14px;
                color: #78909c;
            }
            a {
                color: #6a1b9a;
            }
            """;

    @Override
    public String render(Profile profile) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n<html lang=\"tr\">\n<head>\n")
                .append("<meta charset=\"UTF-8\">\n")
                .append("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n")
                .append("<title>").append(escape(profile.fullName())).append("</title>\n")
                .append("<style>\n").append(STYLE).append("</style>\n</head>\n<body>\n")
                .append("<div class=\"card\">\n");

        html.append("<h1>").append(escape(profile.fullName())).append("</h1>\n");
        html.append("<h2>Öğrenci No: ").append(escape(profile.studentNo())).append("</h2>\n");
        html.append("<div class=\"bolum\">").append(escape(profile.department())).append("</div>\n");

        html.append("<h3>Kısa Biyografi</h3>\n");
        for (String paragraph : profile.biography()) {
            html.append("<p>").append(emphasize(escape(paragraph))).append("</p>\n");
        }

        html.append("<h3>İlgi ve Beceri Alanları</h3>\n<ul>\n");
        for (String skill : profile.skills()) {
            html.append("<li>").append(escape(skill)).append("</li>\n");
        }
        html.append("</ul>\n");

        html.append("<h3>Ürün Kataloğu</h3>\n<table>\n")
                .append("<tr><th>Ürün</th><th>Açıklama</th><th>Fiyat</th></tr>\n");
        for (Product product : profile.products()) {
            html.append("<tr><td>").append(escape(product.name())).append("</td><td>")
                    .append(escape(product.description())).append("</td><td class=\"fiyat\">")
                    .append(escape(product.price())).append("</td></tr>\n");
        }
        html.append("</table>\n");

        html.append("<div class=\"alt\">GitHub: <a href=\"").append(escape(profile.githubUrl())).append("\">")
                .append(escape(profile.githubUrl())).append("</a></div>\n");

        html.append("</div>\n</body>\n</html>\n");
        return html.toString();
    }

    /** Kullanıcı metnindeki özel karakterleri HTML'e zarar vermeyecek hale getirir. */
    private String escape(String text) {
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }

    /** **metin** biçimindeki kısımları vurgulu (kalın ve renkli) etikete çevirir. */
    private String emphasize(String escapedText) {
        return escapedText.replaceAll("\\*\\*(.+?)\\*\\*", "<strong>$1</strong>");
    }
}
