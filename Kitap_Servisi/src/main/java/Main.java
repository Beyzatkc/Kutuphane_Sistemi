public class Main {
    public static void main(String[] args) {
       Kitap_Servisi kitapservisi=new Kitap_Servisi();
       kitapservisi.Kitap_ekleme("nerinin evlenmesi","neriman","uygun","3.Baskı",2,"Aşk","epsilon",1500);
       kitapservisi.Kitap_arama("nerinin evlenmesi");
       kitapservisi.Yazarin_Kitaplari("neriman","uygun");
       kitapservisi.Kategoriye_Gore_Siralama();
        kitapservisi.Guncelleme(1,"nerisin hayati","neri","uygun","3.baski",3,"ask","ephesus",1500);
       kitapservisi.Kitap_Silme(1);
    }
}
