import java.sql.*;
import java.util.ArrayList;

public class Kitap_Servisi implements Kitap_Servisi_arayuzu {
    @Override
    public Kitap Kitap_arama(int ID) {
        try (Connection conn = Kitap.veritabaniBaglantisi()) {
            String sql = "SELECT * FROM Kitap WHERE kitap_ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ID);
            ResultSet sonuc = stmt.executeQuery();

            while (sonuc.next()) {
                int id = sonuc.getInt("kitap_ID");
                String kitapAdi = sonuc.getString("kitap_adi");
                String yazaradi = sonuc.getString("kitap_yazar_adi");
                String yazarsoyadi = sonuc.getString("kitap_yazar_soyadi");
                String baski = sonuc.getString("kitap_baskisi");
                String yayinevi = sonuc.getString("kitap_yayinevi");
                int adet = sonuc.getInt("kitap_adet");
                String kategori = sonuc.getString("kitap_kategori");
                int sayfa = sonuc.getInt("kitap_sayfa_sayisi");

                System.out.println("Bulunan Kitap:"
                        + "\nID: " + id
                        + "\nAdi: " + kitapAdi
                        + "\nYazar Adi: " + yazaradi
                        + "\nYazar Soyadi: " + yazarsoyadi
                        + "\nBaski: " + baski
                        + "\nYayinevi: " + yayinevi
                        + "\nadet: " + adet
                        + "\nkategori: " + kategori
                        + "\nsayfa: " + sayfa
                        + "\n---------------------------"
                );
                Kitap kitap=new Kitap(kitapAdi,yazaradi,yazarsoyadi,baski,adet,kategori,yayinevi,sayfa,id);
                return kitap;

            }

        } catch (Exception e) {
            System.out.println(" Hata olustu: " + e.getMessage());
        }

        return null;
    }

    @Override
    public ArrayList<String> Kitap_Ada_Gore_Arama(String ad) {
        ArrayList<String>kitaplar=new ArrayList<>();
        try (Connection conn = Kitap.veritabaniBaglantisi()) {
            String sql = "SELECT * FROM Kitap WHERE kitap_adi = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, ad);
            ResultSet sonuc = stmt.executeQuery();

            int sayac = 0;
            while (sonuc.next()) {
                int id = sonuc.getInt("kitap_ID");
                String kitapAdi = sonuc.getString("kitap_adi");
                String yazaradi = sonuc.getString("kitap_yazar_adi");
                String yazarsoyadi = sonuc.getString("kitap_yazar_soyadi");
                String baski = sonuc.getString("kitap_baskisi");
                String yayinevi = sonuc.getString("kitap_yayinevi");
                int adet = sonuc.getInt("kitap_adet");
                String kategori = sonuc.getString("kitap_kategori");
                int sayfa = sonuc.getInt("kitap_sayfa_sayisi");

                System.out.println("Bulunan Kitap:"
                        + "\nID: " + id
                        + "\nAdi: " + kitapAdi
                        + "\nYazar Adi: " + yazaradi
                        + "\nYazar Soyadi: " + yazarsoyadi
                        + "\nBaski: " + baski
                        + "\nYayinevi: " + yayinevi
                        + "\nadet: " + adet
                        + "\nkategori: " + kategori
                        + "\nsayfa: " + sayfa
                        + "\n---------------------------"
                );
                String string = String.valueOf(id);
                String string2 = String.valueOf(adet);
                String string3 = String.valueOf(sayfa);
                kitaplar.add(string);
                kitaplar.add(kitapAdi);
                kitaplar.add(yazaradi);
                kitaplar.add(yazarsoyadi);
                kitaplar.add(baski);
                kitaplar.add(yayinevi);
                kitaplar.add(string2);
                kitaplar.add(kategori);
                kitaplar.add(string3);

                sayac++;
            }
            if (sayac == 0) {
                System.out.println("Aranan kitap bulunamadi");
            }

        } catch (Exception e) {
            System.out.println(" Hata olustu: " + e.getMessage());
        }
       return kitaplar;
    }


    @Override
    public Kitap Kitap_ekleme(String kitap_adi, String yazar_ad, String yazar_soyad, String baskisi, int adet, String kategori, String yayinevi, int sayfa_sayisi) {
        try (Connection conn = Kitap.veritabaniBaglantisi()) {
            String sql = "INSERT INTO Kitap (kitap_adi, kitap_yazar_adi, kitap_yazar_soyadi, kitap_baskisi ,kitap_adet,kitap_kategori,kitap_yayinevi,kitap_sayfa_sayisi) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setString(1, kitap_adi);
            stmt.setString(2, yazar_ad);
            stmt.setString(3, yazar_soyad);
            stmt.setString(4, baskisi);
            stmt.setInt(5, adet);
            stmt.setString(6, kategori);
            stmt.setString(7, yayinevi);
            stmt.setInt(8, sayfa_sayisi);

            int satirSayisi = stmt.executeUpdate();
            if (satirSayisi > 0) {
                System.out.println("Kayit basarili!");
                Kitap kitap = new Kitap(kitap_adi, yazar_ad, yazar_soyad, baskisi, adet, kategori, yayinevi, sayfa_sayisi, 1);
                return kitap;
            } else {
                System.out.println("Kayit basarisiz!");
            }

        } catch (SQLIntegrityConstraintViolationException e) {
            System.out.println("Bu kitap zaten kayıtlı!");
        } catch (Exception e) {
            System.out.println("Hata olustu: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void Guncelleme(int ID, String kitapAdi, String yazarAdi, String yazarSoyadi, String baski, int adet, String kategori, String yayinevi, int sayfaSayisi) {
        try (Connection conn = Kitap.veritabaniBaglantisi()) {
            String sql = "SELECT * FROM Kitap WHERE kitap_ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setInt(1, ID);

            ResultSet sonuc = stmt.executeQuery();
            if (sonuc.next()) {
                String sql2 = "UPDATE Kitap SET kitap_adi = ?, kitap_yazar_adi = ?, kitap_yazar_soyadi = ?, kitap_baskisi = ?, kitap_adet = ?, kitap_kategori = ?, kitap_yayinevi = ?, kitap_sayfa_sayisi = ? WHERE kitap_ID = ?";
                PreparedStatement stmt2 = conn.prepareStatement(sql2);

                stmt2.setString(1, kitapAdi);
                stmt2.setString(2, yazarAdi);
                stmt2.setString(3, yazarSoyadi);
                stmt2.setString(4, baski);
                stmt2.setInt(5, adet);
                stmt2.setString(6, kategori);
                stmt2.setString(7, yayinevi);
                stmt2.setInt(8, sayfaSayisi);
                stmt2.setInt(9, ID);

                int satirSayisii = stmt2.executeUpdate();
                if (satirSayisii > 0) {
                    System.out.println("Kitap başarıyla güncellendi!");
                } else {
                    System.out.println("Eksik bilgi girdiniz");
                }
            } else {
                System.out.println("Belirtilen ID ile kitap bulunamadı.");
            }

        } catch (Exception e) {
            System.out.println("Hata oluştu: " + e.getMessage());
        }

    }

    @Override
    public void Kitap_Silme(int ID) {
        try (Connection conn = Kitap.veritabaniBaglantisi()) {
            String sql = "DELETE FROM Kitap WHERE kitap_ID = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);

            stmt.setInt(1, ID);

            int satirSayisi = stmt.executeUpdate();

            if (satirSayisi > 0) {
                System.out.println("Kitap başarıyla silindi!");
            } else {
                System.out.println("Belirtilen ID ile kitap bulunamadı.");
            }

        } catch (Exception e) {
            System.out.println("Hata oluştu: " + e.getMessage());
        }
    }

    @Override
    public ArrayList<String> Yazarin_Kitaplari(String yazaradi, String yazarsoyadi) {
        ArrayList<String>kitaplar=new ArrayList<>();
        try (Connection conn = Kitap.veritabaniBaglantisi()) {
            String sql = "SELECT DISTINCT kitap_adi FROM Kitap WHERE kitap_yazar_adi = ? AND kitap_yazar_soyadi = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, yazaradi);
            stmt.setString(2, yazarsoyadi);
            ResultSet sonuc = stmt.executeQuery();

            int sayac = 0;
            while (sonuc.next()) {
                String kitapAdi = sonuc.getString("kitap_adi");

                System.out.println("Bulunan Kitap:"
                        + "\nAdi: " + kitapAdi
                        + "\nYazar Adi: " + yazaradi
                        + "\nYazar Soyadi: " + yazarsoyadi
                        + "\n---------------------------"
                );
                kitaplar.add(kitapAdi);
                kitaplar.add(yazaradi);
                kitaplar.add(yazarsoyadi);
                sayac++;
            }
            if (sayac == 0) {
                System.out.println("Yazarın kitabı bulunamadı");
            }

        } catch (Exception e) {
            System.out.println("Hata oluştu: " + e.getMessage());
        }
        return kitaplar;
    }
}