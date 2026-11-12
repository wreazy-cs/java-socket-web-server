package webserver.page;

/**
 * Gösterilecek {@link Profile} bilgisini sağlayan kaynağın sözleşmesi.
 * Bilgi bugün kodun içinde duruyor, yarın bir dosyadan veya veritabanından gelebilir.
 */
public interface ProfileProvider {

    Profile getProfile();
}
