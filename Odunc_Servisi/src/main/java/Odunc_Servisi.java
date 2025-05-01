import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLIntegrityConstraintViolationException;

public class Odunc_Servisi implements Odunc_Servisi_Arayuzu{

    @Override
    public void Odunc_Alma(int kullanici_Id,int kitapId,String alinis_tarihi,String iade_tarihi,boolean iade_edildi_mi) {
        try (Connection conn = Odunc.veritabaniBaglantisi()) {
            String sql = "INSERT INTO Odunc (kullanici_IDSI, kitap_IDSI, alinis_tarihi, iade_tarihi, iade_edildi_mi) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, kullanici_Id);
            stmt.setInt(2, kitapId);
            stmt.setString(3, alinis_tarihi);
            stmt.setString(4, iade_tarihi);
            stmt.setBoolean(5, iade_edildi_mi);

            int satirSayisi = stmt.executeUpdate();
            if (satirSayisi > 0) {
                System.out.println("Odunc kaydi basarili!");
            } else {
                System.out.println("Odunc kaydi basarisiz!");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Zaten odunc islemi yapildi!");
        } catch (Exception e) {
            System.out.println("Hata olustu: " + e.getMessage());
        }
    }

    @Override
    public void iade_Etme(int kitapID, int kullaniciID) {
        try (Connection conn = Odunc.veritabaniBaglantisi()) {
            // Önce: Kayıt gerçekten var mı ve iade edilmemiş mi?
            String kontrolSql = "SELECT * FROM Odunc WHERE kitap_IDSI = ? AND kullanici_IDSI = ? AND iade_edildi_mi = false";
            PreparedStatement kontrolStmt = conn.prepareStatement(kontrolSql);
            kontrolStmt.setInt(1, kitapID);
            kontrolStmt.setInt(2, kullaniciID);
            ResultSet sonuc = kontrolStmt.executeQuery();

            if (sonuc.next()) {
                // Güncelle: iade_edildi_mi = true yap
                String guncelleSql = "UPDATE Odunc SET iade_edildi_mi = true WHERE kitap_IDSI = ? AND kullanici_IDSI = ?";
                PreparedStatement guncelleStmt = conn.prepareStatement(guncelleSql);
                guncelleStmt.setInt(1, kitapID);
                guncelleStmt.setInt(2, kullaniciID);
                int satirSayisi = guncelleStmt.executeUpdate();

                if (satirSayisi > 0) {
                    System.out.println("Kitap iade edilmiştir.");
                } else {
                    System.out.println("Güncelleme başarısız.");
                }
            } else {
                System.out.println("Bu ID'ye ait iade edilmemiş ödünç kaydı bulunamadı.");
            }

        } catch (Exception e) {
            System.out.println("Hata oluştu: " + e.getMessage());
        }
    }


}
