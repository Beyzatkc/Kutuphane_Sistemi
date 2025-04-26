import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
public class Veritabani_Olusturma {
    public static void main(String[] args){
        String url = "jdbc:mysql://veritabani:3306/kutuphane?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
        String kullanici = "admin";
        String sifre = "admin123"; // kendi şifrene göre değiştir tatlım

        try {
            Connection baglanti = DriverManager.getConnection(url, kullanici, sifre);
            Statement stmt = baglanti.createStatement();
            // Kullanıcı Tablosu
            stmt.executeUpdate("""
                 CREATE TABLE IF NOT EXISTS Kullanici (
                     kullanici_ID INT AUTO_INCREMENT PRIMARY KEY,
                     kullanici_adi VARCHAR(100) NOT NULL,
                     kullanici_soyadi VARCHAR(100) NOT NULL,
                     kullanici_sifre VARCHAR(100) NOT NULL,
                     kullanici_email VARCHAR(100) UNIQUE NOT NULL,
                     kayit_tarihi DATETIME DEFAULT CURRENT_TIMESTAMP,
                     kullanici_tel VARCHAR(100)
                  );
            """);

            // Kitap Tablosu
            stmt.executeUpdate("""
               CREATE TABLE IF NOT EXISTS Kitap (
                     kitap_ID INT AUTO_INCREMENT PRIMARY KEY,
                     kitap_adi VARCHAR(100) NOT NULL,
                     kitap_yazar_adi VARCHAR(100) NOT NULL,
                     kitap_yazar_soyadi VARCHAR(100) NOT NULL,
                     kitap_baskisi VARCHAR(100) NOT NULL,
                     kitap_adet INT NOT NULL,
                     kitap_kategori VARCHAR(100) NOT NULL,
                     kitap_yayinevi VARCHAR(100) NOT NULL,
                     kitap_sayfa_sayisi INT NOT NULL
                 );
            """);

            stmt.executeUpdate("""
                 CREATE TABLE IF NOT EXISTS Odunc (
                    kullanici_IDSI INT,
                    kitap_IDSI INT,
                    alinis_tarihi DATETIME DEFAULT CURRENT_TIMESTAMP,
                    iade_tarihi DATETIME DEFAULT (DATE_ADD(NOW(), INTERVAL 30 DAY)),
                    iade_edildi_mi TINYINT(1) DEFAULT 0,
                    CONSTRAINT Kullanici_FK_Odunc FOREIGN KEY (kullanici_IDSI) REFERENCES Kullanici(kullanici_ID) ON DELETE CASCADE ON UPDATE CASCADE,
                    CONSTRAINT Kitap_FK_Odunc FOREIGN KEY (kitap_IDSI) REFERENCES Kitap(kitap_ID) ON DELETE CASCADE ON UPDATE CASCADE
                  );
            """);


            stmt.executeUpdate("""
               CREATE TABLE IF NOT EXISTS Bildirim (
                   kullanici_IDSI INT,
                   mesaj_icerigi VARCHAR(100) NOT NULL,
                   gonderim_turu VARCHAR(100) NOT NULL,
                   CONSTRAINT Bildirim_FK FOREIGN KEY (kullanici_IDSI) REFERENCES Kullanici(kullanici_ID) ON DELETE CASCADE ON UPDATE CASCADE
                  );
            """);

            System.out.println("MySQL tabloları başarıyla oluşturuldu! 💃");
            baglanti.close();

        } catch (Exception e) {
            System.out.println("Hata: " + e.getMessage());
        }
    }
}
