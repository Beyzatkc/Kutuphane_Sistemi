public interface Kitap_Servisi_arayuzu {
    public void Kitap_arama(String ad);
    public Kitap Kitap_ekleme(String kitap_adi, String yazar_ad, String yazar_soyad, String baskisi, int adet, String kategori, String yayinevi,int sayfa_sayisi);
    public void Guncelleme(int ID,String kitapAdi,String yazarAdi,String yazarSoyadi,String baski,int adet,String kategori,String yayinevi,int sayfaSayisi);
    public void Kategoriye_Gore_Siralama();
    public void Kitap_Silme(int ID);
    public void Yazarin_Kitaplari(String yazaradi,String yazarsoyadi);
}
