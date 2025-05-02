import java.sql.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Odunc_Servisi implements Odunc_Servisi_Arayuzu{

    @Override
    public void Odunc_Alma(int kullanici_Id,int kitapId,String alinis_tarihi,String iade_tarihi,boolean iade_edildi_mi) {
        try (Connection conn = Odunc.veritabaniBaglantisi()) {
            String sql = "INSERT INTO Odunc (kullanici_IDSI, kitap_IDSI, iade_edildi_mi) VALUES (?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, kullanici_Id);
            stmt.setInt(2, kitapId);
            stmt.setBoolean(3, iade_edildi_mi);

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

    @Override
    public ArrayList<Integer> Odunc_Kayitlari() {
        ArrayList<Integer> kullaniciIDvekitapID = new ArrayList<>();

        try (Connection conn = Odunc.veritabaniBaglantisi()) {
            String sql = "SELECT * FROM Odunc";
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int kullanici_Id = rs.getInt("kullanici_IDSI");
                int kitapId = rs.getInt("kitap_IDSI");
                Timestamp alinis_tarihi = rs.getTimestamp("alinis_tarihi");
                Timestamp iade_tarihi = rs.getTimestamp("iade_tarihi");
                boolean iade_edildi_mi = rs.getBoolean("iade_edildi_mi");
                if (iade_tarihi != null) {
                    // Bugün ve iade tarihi arasındaki farkı hesapla
                    long gunFarki = ChronoUnit.DAYS.between(LocalDateTime.now(), iade_tarihi.toLocalDateTime());
                    // Eğer 5 gün veya daha az kaldıysa
                    if (gunFarki <= 5 && gunFarki >= 0 && !iade_edildi_mi) {
                        kullaniciIDvekitapID.add(kullanici_Id);
                        kullaniciIDvekitapID.add(kitapId);
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Hata oluştu: " + e.getMessage());
        }

        return kullaniciIDvekitapID;
    }
}
