import java.sql.Connection;
import java.sql.PreparedStatement;

public class Bildirim_Servisi implements Bildirim_Servisi_Arayuzu{
    @Override
    public Bildirim Bildirim_Sms_Gonderme(int kullanici_IDSI,String mesaj_icerigi,String gonderim_turu) {
        try (Connection conn =Bildirim.veritabaniBaglantisi()) {
            String sql = "INSERT INTO Bildirim (kullanici_IDSI, mesaj_icerigi, gonderim_turu) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, kullanici_IDSI);
            stmt.setString(2, mesaj_icerigi);
            stmt.setString(3, gonderim_turu);
            int satirSayisi = stmt.executeUpdate();
            if (satirSayisi > 0) {
                System.out.println("Bildirim basariyla gonderildi!");
                Bildirim bildirim=new Bildirim(kullanici_IDSI,mesaj_icerigi,gonderim_turu);
                return bildirim;
            } else {
                System.out.println("Bildirim gönderilemedi!");
            }

        } catch (Exception e) {
            System.out.println(" Hata olustu: " + e.getMessage());
        }
      return null;
    }

}
