import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Bildirim {
    private int kuLlanici_IDSI;
    private String mesaj_icerigi;
    private String gonderim_turu;
    public Bildirim(int kuLlanici_IDSI, String mesaj_icerigi, String gonderim_turu) {
        this.kuLlanici_IDSI = kuLlanici_IDSI;
        this.mesaj_icerigi = mesaj_icerigi;
        this.gonderim_turu = gonderim_turu;
    }

    public int getKuLlanici_IDSI() {
        return kuLlanici_IDSI;
    }

    public void setKuLlanici_IDSI(int kuLlanici_IDSI) {
        this.kuLlanici_IDSI = kuLlanici_IDSI;
    }

    public String getMesaj_icerigi() {
        return mesaj_icerigi;
    }

    public void setMesaj_icerigi(String mesaj_icerigi) {
        this.mesaj_icerigi = mesaj_icerigi;
    }

    public String getGonderim_turu() {
        return gonderim_turu;
    }

    public void setGonderim_turu(String gonderim_turu) {
        this.gonderim_turu = gonderim_turu;
    }
    public static Connection veritabaniBaglantisi() throws SQLException {
        String url = "jdbc:mysql://veritabani:3306/kutuphane?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String kullaniciAdi = "admin";
        String parola = "admin123";
        return DriverManager.getConnection(url,kullaniciAdi,parola);
    }
}
