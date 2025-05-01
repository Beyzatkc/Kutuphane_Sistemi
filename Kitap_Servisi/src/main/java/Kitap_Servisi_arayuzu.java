import java.util.ArrayList;

public interface Kitap_Servisi_arayuzu {
    public Kitap Kitap_arama(int ID);
    public Kitap Kitap_ekleme(String kitap_adi, String yazar_ad, String yazar_soyad, String baskisi, int adet, String kategori, String yayinevi,int sayfa_sayisi);
    public void Guncelleme(int ID,String kitapAdi,String yazarAdi,String yazarSoyadi,String baski,int adet,String kategori,String yayinevi,int sayfaSayisi);
    public void Kitap_Silme(int ID);
    public ArrayList<String> Yazarin_Kitaplari(String yazaradi, String yazarsoyadi);
    public ArrayList<String> Kitap_Ada_Gore_Arama(String ad);
    public Kitap KitapAdetArttirma(int ID);
    public Kitap KitapAdetAzaltma(int ID);

}
