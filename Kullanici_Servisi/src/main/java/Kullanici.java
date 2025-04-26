import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Kullanici {
    private String adi;
    private String soyadi;
    private String sifre;
    private String Email;
    private String kayit_tarihi;
    private String kullanici_tel;
    private int ID;

    public Kullanici(String adi, String soyadi, String sifre, String email, String kayit_tarihi, String kullanici_tel,int ID) {
        this.adi = adi;
        this.soyadi = soyadi;
        this.sifre = sifre;
        Email = email;
        this.kayit_tarihi = kayit_tarihi;
        this.kullanici_tel=kullanici_tel;
        this.ID = ID;
    }
    public String getAdi() {
        return adi;
    }
    public void setAdi(String adi) {
        this.adi = adi;
    }
    public String getTeli() {
        return kullanici_tel;
    }
    public void setTeli(String kullanici_tel) {
        this.kullanici_tel =kullanici_tel;
    }

    public String getSoyadi() {
        return soyadi;
    }
    public void setSoyadi(String soyadi) {
        this.soyadi = soyadi;
    }
    public String getSifre() {
        return sifre;
    }
    public void setSifre(String sifre) {
        this.sifre = sifre;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public String getKayit_tarihi() {
        return kayit_tarihi;
    }
    public void setKayit_tarihi(String kayit_tarihi) {
        this.kayit_tarihi = kayit_tarihi;
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
