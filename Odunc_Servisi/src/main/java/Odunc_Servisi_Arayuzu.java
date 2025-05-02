import java.util.ArrayList;

public interface Odunc_Servisi_Arayuzu {
 public void Odunc_Alma(int kullanici_Id,int kitapId,String alinis_tarihi,String iade_tarihi,boolean iade_edildi_mi);
 public void iade_Etme(int kitapID, int kullaniciID);
 public ArrayList<Integer> Odunc_Kayitlari();
}
