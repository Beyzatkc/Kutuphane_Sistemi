import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Kitap {
   private String kitap_adi;
   private String yazar_ad;
   private String yazar_soyad;
   private String baskisi;
   private int adet;
   private String kategori;
   private String yayinevi;
   private int sayfa_sayisi;
   private int ID;

    public Kitap(String kitap_adi, String yazar_ad, String yazar_soyad, String baskisi, int adet, String kategori, String yayinevi,int sayfa_sayisi, int ID) {
        this.kitap_adi = kitap_adi;
        this.yazar_ad = yazar_ad;
        this.yazar_soyad = yazar_soyad;
        this.baskisi = baskisi;
        this.adet = adet;
        this.kategori = kategori;
        this.yayinevi = yayinevi;
        this.sayfa_sayisi=sayfa_sayisi;
        this.ID = ID;
    }

    public String getKitap_adi() {
        return kitap_adi;
    }

    public void setKitap_adi(String kitap_adi) {
        this.kitap_adi = kitap_adi;
    }

    public String getYazar_ad() {
        return yazar_ad;
    }

    public void setYazar_ad(String yazar_ad) {
        this.yazar_ad = yazar_ad;
    }

    public String getYazar_soyad() {
        return yazar_soyad;
    }

    public void setYazar_soyad(String yazar_soyad) {
        this.yazar_soyad = yazar_soyad;
    }

    public String getBaskisi() {
        return baskisi;
    }

    public void setBaskisi(String baskisi) {
        this.baskisi = baskisi;
    }

    public int getAdet() {
        return adet;
    }

    public void setAdet(int adet) {
        this.adet = adet;
    }

    public String getKategori() {
        return kategori;
    }

    public void setKategori(String kategori) {
        this.kategori = kategori;
    }

    public String getYayinevi() {
        return yayinevi;
    }

    public void setYayinevi(String yayinevi) {
        this.yayinevi = yayinevi;
    }

    public int getSayfa_sayisi() {
        return sayfa_sayisi;
    }

    public void setSayfa_sayisi(int sayfa_sayisi) {
        this.sayfa_sayisi = sayfa_sayisi;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public static Connection veritabaniBaglantisi() throws SQLException {
        String url = "jdbc:mysql://veritabani:3306/kutuphane?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String kullaniciAdi = "admin";
        String parola = "admin123";
        return DriverManager.getConnection(url,kullaniciAdi,parola);
    }
}
