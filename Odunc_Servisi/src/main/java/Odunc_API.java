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
import java.util.ArrayList;

public class Odunc_API {
    public static void main(String[] args) throws IOException {
        HttpServer server = HttpServer.create(new InetSocketAddress(8082), 0);

        server.createContext("/OduncAlma", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
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
                String iade = fields[1].split("=")[1];
                iade = URLDecoder.decode(iade, StandardCharsets.UTF_8);
                boolean iademi = Boolean.parseBoolean(iade);

                String email = fields[2].split("=")[1];
                email = URLDecoder.decode(email, StandardCharsets.UTF_8);
                String sifre = fields[3].split("=")[1];
                sifre = URLDecoder.decode(sifre, StandardCharsets.UTF_8);

                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://kullanici-servisi:8080/giris?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8) +
                                    "&sifre=" + URLEncoder.encode(sifre, StandardCharsets.UTF_8)))
                            .GET()
                            .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    String gelenCevap=response.body();
                  //  kitap servisine get istegi
                    HttpClient client2 = HttpClient.newHttpClient();
                    HttpRequest request2 = HttpRequest.newBuilder()
                            .uri(URI.create("http://kitap-servisi:8081/KitapAdetAzaltma?kitapid="+kitapidsi))
                            .GET()
                            .build();
                    HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
                    String gelenCevap2=response2.body();
                    if(!gelenCevap.isEmpty()&&!gelenCevap2.isEmpty()){
                            int kullaniciID = Integer.parseInt(gelenCevap.trim());
                        Odunc_Servisi oduncservisi=new Odunc_Servisi();
                        oduncservisi.Odunc_Alma(kullaniciID,kitapidsi,null,null,iademi);
                        yanit = "Ödünç alma işlemi başarıyla gerçekleştirildi.";
                    }else if(gelenCevap.isEmpty()){
                        yanit="Kullanıcı girişi başarısız";
                    }else if(gelenCevap2.isEmpty()){
                        yanit="Ödün alma işlemi başarısız";
                    }

                }catch (Exception e){
                    e.printStackTrace();
                     yanit = "İstek gönderilirken hata oluştu."+ e.getMessage();
                }
                exchange.sendResponseHeaders(200, yanit.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(yanit.getBytes());
                os.close();

            }
        }));



        server.createContext("/iadeEtme", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
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
                try {
                    HttpClient client = HttpClient.newHttpClient();
                    HttpRequest request = HttpRequest.newBuilder()
                            .uri(URI.create("http://kullanici-servisi:8080/giris?email=" + URLEncoder.encode(email, StandardCharsets.UTF_8) +
                                    "&sifre=" + URLEncoder.encode(sifre, StandardCharsets.UTF_8)))
                            .GET()
                            .build();
                    HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
                    String gelenCevap=response.body();
                    HttpClient client2 = HttpClient.newHttpClient();
                    HttpRequest request2 = HttpRequest.newBuilder()
                            .uri(URI.create("http://kitap-servisi:8081/KitapAdetArttirma?kitapid="+kitapidsi))
                            .GET()
                            .build();
                    HttpResponse<String> response2 = client2.send(request2, HttpResponse.BodyHandlers.ofString());
                    String gelenCevap2=response2.body();
                    if(!gelenCevap.isEmpty()&&!gelenCevap2.isEmpty()){
                        int kullaniciID = Integer.parseInt(gelenCevap.trim());
                        Odunc_Servisi oduncservisi=new Odunc_Servisi();
                        oduncservisi.iade_Etme(kitapidsi,kullaniciID);
                        yanit = "iade etme işlemi başarıyla gerçekleştirildi.";
                    }else if(gelenCevap.isEmpty()){
                        yanit="Kullanıcı girişi başarısız";
                    }else if(gelenCevap2.isEmpty()){
                        yanit="iade işlemi basarisiz";
                    }

                }catch (Exception e){
                    e.printStackTrace();
                     yanit = "İstek gönderilirken hata oluştu."+ e.getMessage();
                }
                exchange.sendResponseHeaders(200, yanit.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(yanit.getBytes());
                os.close();

            }
        }));

        server.createContext("/OduncKayitlari", (exchange -> {
            String response="";
            if ("GET".equals(exchange.getRequestMethod())) {
                Odunc_Servisi oduncservis = new Odunc_Servisi();
                ArrayList<Integer> oduncKayitlari = oduncservis.Odunc_Kayitlari();
                StringBuilder builder = new StringBuilder();
                for (int i = 0; i < oduncKayitlari.size(); i += 2) {
                    builder.append(oduncKayitlari.get(i))
                            .append(",")
                            .append(oduncKayitlari.get(i + 1));

                    if (i + 2 < oduncKayitlari.size()) {
                        builder.append(",");
                    }
                }
                 response = builder.toString();
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }));



        server.start();
        System.out.println("Kullanici API server'ı http://localhost:8082 adresinde çalışıyor...");
    }
}
