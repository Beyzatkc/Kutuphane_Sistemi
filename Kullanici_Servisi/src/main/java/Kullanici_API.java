import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

public class Kullanici_API {
    public static void main(String[] args) throws IOException {
        // HTTP server'ı başlatıyoruz.
        HttpServer server = HttpServer.create(new InetSocketAddress(8080), 0);

        // Kullanıcı giriş işlemi için GET endpoint'i
        server.createContext("/giris", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                // URL'den parametreleri al
                String query = exchange.getRequestURI().getQuery();
                String[] params = query.split("&");
                String email = params[0].split("=")[1];
                email= URLDecoder.decode(email, StandardCharsets.UTF_8);
                String sifre = params[1].split("=")[1];
                sifre= URLDecoder.decode(sifre, StandardCharsets.UTF_8);

                Kullanici_Servisi kullaniciServisi = new Kullanici_Servisi();
                Kullanici kullanici = kullaniciServisi.kullanici_Girisi(email, sifre);

                String response;
                if (kullanici != null) {
                    String str = String.valueOf(kullanici.getID());
                    response =str;
                } else {
                    response ="";
                }

                // Yanıtı gönder
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }));

        // Kullanıcı kaydı işlemi için POST endpoint'i
        server.createContext("/kayit", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                // POST verisini al
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                // JSON formatındaki veriyi ayıkla
                String requestBody = sb.toString();
                String[] fields = requestBody.split("&");
                String adi = fields[0].split("=")[1];
                adi= URLDecoder.decode(adi, StandardCharsets.UTF_8);
                String soyadi = fields[1].split("=")[1];
                soyadi= URLDecoder.decode(soyadi, StandardCharsets.UTF_8);
                String email = fields[2].split("=")[1];
                email= URLDecoder.decode(email, StandardCharsets.UTF_8);
                String sifre = fields[3].split("=")[1];
                sifre= URLDecoder.decode(sifre, StandardCharsets.UTF_8);
                String kullaniciTel = fields[4].split("=")[1];
                kullaniciTel= URLDecoder.decode(kullaniciTel, StandardCharsets.UTF_8);

                // Kullanıcı kaydını yap
                Kullanici_Servisi kullaniciServisi = new Kullanici_Servisi();
                kullaniciServisi.kullanici_Kaydi(adi, soyadi, email, sifre, null, kullaniciTel);

                String response = "Kayıt başarılı!";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }));

        server.createContext("/TelefonMailGosterme", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                String[] params = query.split("&");
                String id = params[0].split("=")[1];
                id= URLDecoder.decode(id, StandardCharsets.UTF_8);
                int idsi = Integer.parseInt(id);


                Kullanici_Servisi kullaniciServisi = new Kullanici_Servisi();
                Kullanici kullanici = kullaniciServisi.Telefon_Mail_Gosterme(idsi);
                String response;
                if (kullanici != null) {
                    response =kullanici.getEmail()+","+kullanici.getTeli();
                } else {
                    response ="";
                }

                // Yanıtı gönder
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }));

        // Sunucuyu başlat
        server.start();
        System.out.println("Kullanici API server'ı http://localhost:8080 adresinde çalışıyor...");
    }
}
