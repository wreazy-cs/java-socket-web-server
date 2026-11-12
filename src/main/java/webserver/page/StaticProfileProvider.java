package webserver.page;

import java.util.List;

/**
 * Profil bilgisini doğrudan kod içinde tutan basit {@link ProfileProvider} gerçekleştirimi.
 */
public class StaticProfileProvider implements ProfileProvider {

    private final Profile profile = new Profile(
            "Samet Muhammed Ali ŞAFAK",
            "1250505013",
            "Kırklareli Üniversitesi · Yazılım Mühendisliği, 2. sınıf",
            List.of(
                    "Merhaba, ben Samet. Kırklareli Üniversitesi Yazılım Mühendisliği bölümünde okuyorum.",
                    "Şu sıralar **Java** ile nesne yönelimli programlama çalışıyorum. Bu ödevdeki gibi küçük "
                            + "projeler, kavramların kitapta okumaktan çok daha iyi oturmasını sağlıyor. "
                            + "Boş zamanlarımda **web teknolojileri** üzerine bir şeyler denemeyi seviyorum."
            ),
            List.of("Java", "HTML & CSS", "JavaScript", "Python"),
            List.of(
                    new Product("Kablosuz Mouse", "2.4 GHz, sessiz tıklama", "250 TL"),
                    new Product("Mekanik Klavye", "Türkçe Q, mavi switch", "950 TL"),
                    new Product("USB-C Hub", "4 portlu, HDMI çıkışlı", "420 TL"),
                    new Product("Laptop Standı", "Katlanabilir, alüminyum", "380 TL")
            ),
            "https://github.com/wreazy-cs"
    );

    @Override
    public Profile getProfile() {
        return profile;
    }
}
