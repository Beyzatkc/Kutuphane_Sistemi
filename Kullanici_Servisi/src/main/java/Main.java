import java.util.Scanner;

public class Main {
    public static void main(String[] args) {

        Kullanici_Servisi servis = new Kullanici_Servisi();

        System.out.println("1- Giriş Yap");
        System.out.println("2- Kayıt Ol");


        servis.kullanici_Kaydi("bidik","uygun","@1234","12345",null,"5560");

            servis.kullanici_Girisi("@1234", "12345");

    }
}
