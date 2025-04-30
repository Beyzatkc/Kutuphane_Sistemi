import java.sql.Connection;
import java.sql.PreparedStatement;
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
    public boolean iade_Etme(boolean iade_edildi_mi) {
        if (!iade_edildi_mi) {
            iade_edildi_mi = true;
        }
        return iade_edildi_mi;  // true döner
    }

}
