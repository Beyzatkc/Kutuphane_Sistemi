import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

public class Odunc_API {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8082), 0);

        server.createContext("/OduncAlma", (exchange -> {
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
                String yanit="";
                String requestBody = sb.toString();
                String[] fields = requestBody.split("&");
                String kitapid = fields[0].split("=")[1];
                kitapid = URLDecoder.decode(kitapid, StandardCharsets.UTF_8);
                int kitapidsi = Integer.parseInt(kitapid);
                String iade = fields[1].split("=")[1];
                iade = URLDecoder.decode(iade, StandardCharsets.UTF_8);
                boolean iademi = Boolean.parseBoolean(iade);

                String email = fields[2].split("=")[1];
                email = URLDecoder.decode(email, StandardCharsets.UTF_8);
                String sifre = fields[3].split("=")[1];
                sifre = URLDecoder.decode(sifre, StandardCharsets.UTF_8);

             // kullanici servisine get istegi
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/giris?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8) +
                                    "&sifre=" + URLEncoder.encode(sifre, StandardCharsets.UTF_8)))
                            .GET()
                            .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    String gelenCevap=response.body();
                  //  kitap servisine get istegi
                    HttpClient client2 = HttpClient.newHttpClient();
                    HttpRequest request2 = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8081/KitapArama?kitapid="+kitapidsi))
                            .GET()
                            .build();
                    HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
                    String gelenCevap2=response2.body();
                    if(!gelenCevap.isEmpty()&&!gelenCevap2.isEmpty()){
                            int kullaniciID = Integer.parseInt(gelenCevap.trim());
                           int kitapidsii=Integer.parseInt(gelenCevap2.trim());
                        Odunc_Servisi oduncservisi=new Odunc_Servisi();
                        oduncservisi.Odunc_Alma(kullaniciID,kitapidsii,null,null,iademi);
                        yanit = "Ödünç alma işlemi başarıyla gerçekleştirildi.";
                    }else if(gelenCevap.isEmpty()){
                        yanit="Kullanıcı girişi başarısız";
                    }else if(gelenCevap2.isEmpty()){
                        yanit="belirtilen id de kitap bulunamadi";
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    String hata = "İstek gönderilirken hata oluştu."+ e.getMessage();
                }
                // Gerekirse cevabı kullanıcıya döndür
                exchange.sendResponseHeaders(200, yanit.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(yanit.getBytes());
                os.close();

            }
        }));



        server.createContext("/iadeEtme", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                // POST verisini al
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }
                String yanit="";
                String requestBody = sb.toString();
                String[] fields = requestBody.split("&");
                String kitapid = fields[0].split("=")[1];
                kitapid = URLDecoder.decode(kitapid, StandardCharsets.UTF_8);
                int kitapidsi = Integer.parseInt(kitapid);

                String email = fields[1].split("=")[1];
                email = URLDecoder.decode(email, StandardCharsets.UTF_8);
                String sifre = fields[2].split("=")[1];
                sifre = URLDecoder.decode(sifre, StandardCharsets.UTF_8);
                // kullanici servisine get istegi
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/giris?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8) +
                                    "&sifre=" + URLEncoder.encode(sifre, StandardCharsets.UTF_8)))
                            .GET()
                            .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    String gelenCevap=response.body();
                    //  kitap servisine get istegi
                    HttpClient client2 = HttpClient.newHttpClient();
                    HttpRequest request2 = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8081/KitapArama?kitapid="+kitapidsi))
                            .GET()
                            .build();
                    HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
                    String gelenCevap2=response2.body();
                    if(!gelenCevap.isEmpty()&&!gelenCevap2.isEmpty()){
                        HttpClient client3 = HttpClient.newHttpClient();
                        HttpRequest request3= HttpRequest.newBuilder()
                                .uri(URI.create("http://localhost:8081/KitapAdetArttirma?kitapid="+kitapidsi))
                                .GET()
                                .build();
                        HttpResponse<String> response3 = client3.send(request3, HttpResponse.BodyHandlers.ofString());
                        String gelenCevapadet=response3.body();
                        if(!gelenCevapadet.isEmpty()){
                            int kullaniciID = Integer.parseInt(gelenCevap.trim());
                          Odunc_Servisi oduncservis=new Odunc_Servisi();
                          oduncservis.iade_Etme(kitapidsi,kullaniciID);
                          yanit="iade başarıyla gerçekleşti.";
                        }else{
                          yanit="iade gerceklestirilemedi ödünc kaydı bulunamadı";
                        }
                    }

                }catch (Exception e){
                    e.printStackTrace();
                    yanit = "İstek gönderilirken hata oluştu."+ e.getMessage();
                }
                // Gerekirse cevabı kullanıcıya döndür
                exchange.sendResponseHeaders(200, yanit.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(yanit.getBytes());
                os.close();

            }
        }));

        server.start();
        System.out.println("Kullanici API server'ı http://localhost:8082 adresinde çalışıyor...");
    }
}
