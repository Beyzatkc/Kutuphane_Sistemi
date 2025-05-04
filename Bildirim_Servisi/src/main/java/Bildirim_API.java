import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Bildirim_API {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8083), 0);
        server.createContext("/BildirimSmsGonderme", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                String requestBody = sb.toString();
                String[] fields = requestBody.split("&");

                String gonderim_turu = fields[0].split("=")[1];
                gonderim_turu = URLDecoder.decode(gonderim_turu, StandardCharsets.UTF_8);
                String mesajicerigii="";

             try {
                 HttpClient client = HttpClient.newHttpClient();
                 HttpRequest request = HttpRequest.newBuilder()
                         .uri(URI.create("http://odunc-servisi:8082/OduncKayitlari"))
                         .GET()
                         .build();
                 HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                 String gelenCevap = response.body();
                 if(!gelenCevap.isEmpty()) {
                     String[] kelimeler = gelenCevap.trim().split(",");
                     String gelenCevap2 = "";
                     for (int i = 0; i < kelimeler.length; i += 2) {
                         int kullaniciid = Integer.parseInt(kelimeler[i].trim());
                         HttpClient client2 = HttpClient.newHttpClient();
                         HttpRequest request2 = HttpRequest.newBuilder()
                                 .uri(URI.create("http://kullanici-servisi:8080/TelefonMailGosterme?kullaniciid=" + kullaniciid))
                                 .GET()
                                 .build();
                         HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
                         gelenCevap2 = response2.body();
                         String[] mailvetel = gelenCevap2.trim().split(",");
                         Bildirim_Servisi bs = new Bildirim_Servisi();
                         if (gonderim_turu.equalsIgnoreCase("SMS")) {
                              mesajicerigii = mailvetel[1].trim() + ":ödünç almış olduğunuz " + kelimeler[i + 1] + " ID li kitabı 5 gün içinde teslim etmeniz gerekmektedir.";
                             bs.Bildirim_Sms_Gonderme(kullaniciid, mesajicerigii, gonderim_turu);
                             System.out.println( mailvetel[1].trim() + ":ödünç almış olduğunuz " + kelimeler[i + 1] + " ID li kitabı 5 gün içinde teslim etmeniz gerekmektedir.");
                         } else if (gonderim_turu.equalsIgnoreCase("EMAIL")) {
                              mesajicerigii = mailvetel[0].trim() + ":ödünç almış olduğunuz " + kelimeler[i + 1] + " ID li kitabı 5 gün içinde teslim etmeniz gerekmektedir.";
                             bs.Bildirim_Sms_Gonderme(kullaniciid, mesajicerigii, gonderim_turu);
                             System.out.println( mailvetel[0].trim() + ":ödünç almış olduğunuz " + kelimeler[i + 1] + " ID li kitabı 5 gün içinde teslim etmeniz gerekmektedir.");
                         }
                     }
                 }
             }catch (Exception e){
                 e.printStackTrace();
                 mesajicerigii= "İstek gönderilirken hata oluştu."+ e.getMessage();
             }
                exchange.sendResponseHeaders(200, mesajicerigii.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(mesajicerigii.getBytes());
                os.close();
            }
        }));

        // Sunucuyu başlat
        server.start();
        System.out.println("Kullanici API server'ı http://localhost:8083 adresinde çalışıyor...");
    }
}
