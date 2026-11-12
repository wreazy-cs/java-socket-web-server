package webserver.page;

import java.util.List;

/**
 * Sayfada gösterilecek kişisel bilgiler. Sadece veri taşır, HTML bilgisi içermez.
 *
 * @param fullName   Ad soyad (H1 olarak gösterilir)
 * @param studentNo  Öğrenci numarası (H2 olarak gösterilir)
 * @param department Üniversite ve bölüm bilgisi
 * @param biography  Biyografi paragrafları. İki yıldız arasındaki kısım vurgulanır: **örnek**
 * @param skills     İlgi ve beceri alanları
 * @param products   Sayfadaki basit ürün kataloğu
 * @param githubUrl  GitHub profil adresi
 */
public record Profile(String fullName,
                      String studentNo,
                      String department,
                      List<String> biography,
                      List<String> skills,
                      List<Product> products,
                      String githubUrl) {
}
