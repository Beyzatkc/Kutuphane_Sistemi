import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class Odunc {
    private int kullanici_IDSI;
    private int kitap_IDSI;
    private LocalDateTime alinisTarihi;
    private LocalDateTime iade_tarihi;
    private boolean iadeEdildiMi;

    public Odunc(int kullanici_IDSI, int kitap_IDSI, LocalDateTime alinisTarihi, LocalDateTime iade_tarihi, boolean iadeEdildiMi) {
        this.kullanici_IDSI = kullanici_IDSI;
        this.kitap_IDSI = kitap_IDSI;
        this.alinisTarihi = alinisTarihi;
        this.iade_tarihi = iade_tarihi;
        this.iadeEdildiMi = iadeEdildiMi;
    }

    public int getKullanici_IDSI() {
        return kullanici_IDSI;
    }

    public void setKullanici_IDSI(int kullanici_IDSI) {
        this.kullanici_IDSI = kullanici_IDSI;
    }

    public int getKitap_IDSI() {
        return kitap_IDSI;
    }

    public void setKitap_IDSI(int kitap_IDSI) {
        this.kitap_IDSI = kitap_IDSI;
    }

    public LocalDateTime getAlinisTarihi() {
        return alinisTarihi;
    }

    public void setAlinisTarihi(LocalDateTime alinisTarihi) {
        this.alinisTarihi = alinisTarihi;
    }

    public LocalDateTime getIade_tarihi() {
        return iade_tarihi;
    }

    public void setIade_tarihi(LocalDateTime iade_tarihi) {
        this.iade_tarihi = iade_tarihi;
    }

    public boolean isIadeEdildiMi() {
        return iadeEdildiMi;
    }

    public void setIadeEdildiMi(boolean iadeEdildiMi) {
        this.iadeEdildiMi = iadeEdildiMi;
    }
    public static Connection veritabaniBaglantisi() throws SQLException {
        String url = "jdbc:mysql://veritabani:3306/kutuphane?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String kullaniciAdi = "admin";
        String parola = "admin123";
        return DriverManager.getConnection(url,kullaniciAdi,parola);
    }
}
