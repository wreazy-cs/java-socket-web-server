package webserver.page;

/**
 * Katalogdaki tek bir ürün.
 *
 * @param name        Ürün adı
 * @param description Kısa açıklama
 * @param price       Fiyat (gösterim için metin olarak tutulur, örn. "250 TL")
 */
public record Product(String name, String description, String price) {
}
