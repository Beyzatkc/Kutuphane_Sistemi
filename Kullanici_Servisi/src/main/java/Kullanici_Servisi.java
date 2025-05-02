import java.sql.*;

public class Kullanici_Servisi implements Kullanici_Servisi_Arayuzu{

    @Override
    public Kullanici kullanici_Girisi( String Email, String sifre) {
        try (Connection conn =Kullanici.veritabaniBaglantisi()) {
            String sql = "SELECT * FROM Kullanici WHERE kullanici_email = ? AND kullanici_sifre = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, Email);
            stmt.setString(2, sifre);

            ResultSet sonuc = stmt.executeQuery();

            if (sonuc.next()) {
                System.out.println("Giris yapildi!");
                String ad=sonuc.getString("kullanici_adi");
                String soyad=sonuc.getString("kullanici_soyadi");
                 sifre=sonuc.getString("kullanici_sifre");
                 Email=sonuc.getString("kullanici_email");
                String Kayit=sonuc.getString("kayit_tarihi");
                String tel=sonuc.getString("kullanici_tel");
                int ID=sonuc.getInt("kullanici_ID");
                Kullanici kullanici=new Kullanici(ad,soyad,sifre,Email,Kayit,tel,ID);
                return kullanici;
            } else {
                System.out.println(" Eposta veya şifre hatalı!");
            }

        } catch (Exception e) {
            System.out.println(" Hata olustu: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void kullanici_Kaydi(String adi, String soyadi, String Email, String sifre, String kayit_tarihi,String kullanici_tel) {
        try (Connection conn =Kullanici.veritabaniBaglantisi()) {
            String sql = "INSERT INTO Kullanici (kullanici_adi, kullanici_soyadi, kullanici_email, kullanici_sifre ,kayit_tarihi,kullanici_tel) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, adi);
            stmt.setString(2, soyadi);
            stmt.setString(3, Email);
            stmt.setString(4, sifre);
            stmt.setString(5,kayit_tarihi);
            stmt.setString(6,kullanici_tel);

            int satirSayisi = stmt.executeUpdate();
            if (satirSayisi > 0) {
                System.out.println("Kayit basarili!");
            } else {
                System.out.println("Kayit basarisiz!");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Bu e-posta zaten kayitli: " + Email);
        } catch (Exception e) {
            System.out.println("Hata olustu: " + e.getMessage());
        }
    }
    @Override
    public Kullanici Telefon_Mail_Gosterme(int ID) {
        try (Connection conn =Kullanici.veritabaniBaglantisi()) {
            String sql = "SELECT * FROM Kullanici WHERE kullanici_ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ID);

            ResultSet sonuc = stmt.executeQuery();
            if (sonuc.next()) {
                String ad=sonuc.getString("kullanici_adi");
                String soyad=sonuc.getString("kullanici_soyadi");
                String sifre=sonuc.getString("kullanici_sifre");
                String Email=sonuc.getString("kullanici_email");
                String Kayit=sonuc.getString("kayit_tarihi");
                String tel=sonuc.getString("kullanici_tel");
                Kullanici kullanici=new Kullanici(ad,soyad,sifre,Email,Kayit,tel,ID);
                return kullanici;
            } else {
                System.out.println("Bu ID de kullanıcı bulunamadı");
            }

        } catch (Exception e) {
            System.out.println(" Hata olustu: " + e.getMessage());
        }
        return null;
    }
}
