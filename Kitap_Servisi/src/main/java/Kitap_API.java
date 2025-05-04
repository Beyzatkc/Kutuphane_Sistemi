
import com.sun.net.httpserver.HttpServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

public class Kitap_API {
    public static void main(String[] args) throws IOException {
        HttpServer server=HttpServer.create(new InetSocketAddress(8081),0);

        server.createContext("/KitapArama", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                String[] params = query.split("&");
                String ID = params[0].split("=")[1];
                ID= URLDecoder.decode(ID, StandardCharsets.UTF_8);
                int IDI = Integer.parseInt(ID);

                Kitap_Servisi kitapServis = new Kitap_Servisi();
                Kitap kitap = kitapServis.Kitap_arama(IDI);

                String response;
                if (kitap != null) {
                    String str =String.valueOf(kitap.getID());
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



        server.createContext("/KitapEkleme", (exchange -> {
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
                String kitapadi = fields[0].split("=")[1];
                kitapadi= URLDecoder.decode(kitapadi, StandardCharsets.UTF_8);
                String yazaradi = fields[1].split("=")[1];
                yazaradi= URLDecoder.decode(yazaradi, StandardCharsets.UTF_8);
                String yazarsoyadi = fields[2].split("=")[1];
                yazarsoyadi= URLDecoder.decode(yazarsoyadi, StandardCharsets.UTF_8);
                String baskisi = fields[3].split("=")[1];
                baskisi= URLDecoder.decode(baskisi, StandardCharsets.UTF_8);
                String adet = fields[4].split("=")[1];
                adet= URLDecoder.decode(adet, StandardCharsets.UTF_8);
                int adeti = Integer.parseInt(adet);
                String kategori = fields[5].split("=")[1];
                kategori= URLDecoder.decode(kategori, StandardCharsets.UTF_8);
                String yayinevi = fields[6].split("=")[1];
                yayinevi= URLDecoder.decode(yayinevi, StandardCharsets.UTF_8);
                String sayfa = fields[7].split("=")[1];
                sayfa= URLDecoder.decode(sayfa, StandardCharsets.UTF_8);
                int sayfasi = Integer.parseInt(sayfa);


                Kitap_Servisi kitapServisi = new Kitap_Servisi();
                kitapServisi.Kitap_ekleme(kitapadi,yazaradi,yazarsoyadi,baskisi,adeti,kategori,yayinevi,sayfasi);

                String response = "Kayıt başarılı!";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }));


        server.createContext("/KitapGuncelleme", (exchange -> {
            if ("POST".equals(exchange.getRequestMethod())) {
                // POST verisini al
                InputStreamReader isr = new InputStreamReader(exchange.getRequestBody());
                BufferedReader br = new BufferedReader(isr);
                StringBuilder sb = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    sb.append(line);
                }

                String requestBody = sb.toString();
                String[] fields = requestBody.split("&");
                String ID = fields[0].split("=")[1];
                ID= URLDecoder.decode(ID, StandardCharsets.UTF_8);
                int IDSII = Integer.parseInt(ID);
                String kitapadi = fields[1].split("=")[1];
                kitapadi= URLDecoder.decode(kitapadi, StandardCharsets.UTF_8);
                String yazaradi = fields[2].split("=")[1];
                yazaradi= URLDecoder.decode(yazaradi, StandardCharsets.UTF_8);
                String yazarsoyadi = fields[3].split("=")[1];
                yazarsoyadi= URLDecoder.decode(yazarsoyadi, StandardCharsets.UTF_8);
                String baskisi = fields[4].split("=")[1];
                baskisi= URLDecoder.decode(baskisi, StandardCharsets.UTF_8);
                String adet = fields[5].split("=")[1];
                adet= URLDecoder.decode(adet, StandardCharsets.UTF_8);
                int adeti = Integer.parseInt(adet);
                String kategori = fields[6].split("=")[1];
                kategori= URLDecoder.decode(kategori, StandardCharsets.UTF_8);
                String yayinevi = fields[7].split("=")[1];
                yayinevi= URLDecoder.decode(yayinevi, StandardCharsets.UTF_8);
                String sayfa = fields[8].split("=")[1];
                sayfa= URLDecoder.decode(sayfa, StandardCharsets.UTF_8);
                int sayfasi = Integer.parseInt(sayfa);



                Kitap_Servisi kitapServisi = new Kitap_Servisi();
                kitapServisi.Guncelleme(IDSII,kitapadi,yazaradi,yazarsoyadi,baskisi,adeti,kategori,yayinevi,sayfasi);

                String response = "Guncelleme başarılı!";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();

            }
        }));

        server.createContext("/KitapSilme", (exchange -> {
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
                String ID = fields[0].split("=")[1];
                ID= URLDecoder.decode(ID, StandardCharsets.UTF_8);
                int IDSII = Integer.parseInt(ID);

                Kitap_Servisi kitapServisi = new Kitap_Servisi();
                kitapServisi.Kitap_Silme(IDSII);

                String response = "Silme başarılı!";
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }));

        server.createContext("/YazarinKitaplari", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                String[] params = query.split("&");
                String yazaradi = params[0].split("=")[1];
                yazaradi= URLDecoder.decode(yazaradi, StandardCharsets.UTF_8);
                String yazarsoyadi = params[1].split("=")[1];
                yazarsoyadi= URLDecoder.decode(yazarsoyadi, StandardCharsets.UTF_8);

                Kitap_Servisi kitapServis = new Kitap_Servisi();
                ArrayList<String> kitaplar=kitapServis.Yazarin_Kitaplari(yazaradi,yazarsoyadi);

                StringBuilder stringbirlestirici = new StringBuilder();

                for (int j = 0; j < kitaplar.size(); j += 3) {
                    stringbirlestirici.append("Bulunan Kitap:")
                            .append("\nAdı: ").append(kitaplar.get(j))
                            .append("\nYazar Adı: ").append(kitaplar.get(j + 1))
                            .append("\nYazar Soyadı: ").append(kitaplar.get(j + 2))
                            .append("\n---------------------------\n");
                }

                String response = stringbirlestirici.toString();
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }));

        server.createContext("/KitapAdaGoreArama", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                String[] params = query.split("&");
                String kitapadi = params[0].split("=")[1];
                kitapadi= URLDecoder.decode(kitapadi, StandardCharsets.UTF_8);

                Kitap_Servisi kitapServis = new Kitap_Servisi();
                ArrayList<String> kitaplar=kitapServis.Kitap_Ada_Gore_Arama(kitapadi);

                StringBuilder stringbirlestirici = new StringBuilder();

                for (int j = 0; j < kitaplar.size(); j += 9) {
                    stringbirlestirici.append("Bulunan Kitap:")
                            .append("\nID: ").append(kitaplar.get(j))
                            .append("\nAdi: ").append(kitaplar.get(j + 1))
                            .append("\nYazar Adi: ").append(kitaplar.get(j + 2))
                            .append("\nYazar Soyadi: ").append(kitaplar.get(j + 3))
                            .append("\nBaski: ").append(kitaplar.get(j + 4))
                            .append("\nYayinevi: ").append(kitaplar.get(j + 5))
                            .append("\nadet: ").append(kitaplar.get(j + 6))
                            .append("\nkategori: ").append(kitaplar.get(j + 7))
                            .append("\nsayfa: ").append(kitaplar.get(j + 8))
                            .append("\n---------------------------\n");
                }

                String response = stringbirlestirici.toString();
                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }));

        server.createContext("/KitapAdetArttirma", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                String[] params = query.split("&");
                String ID = params[0].split("=")[1];
                ID= URLDecoder.decode(ID, StandardCharsets.UTF_8);
                int IDI = Integer.parseInt(ID);

                Kitap_Servisi kitapServis = new Kitap_Servisi();
                Kitap kitap = kitapServis.KitapAdetArttirma(IDI);

                String response;
                if (kitap != null) {
                    String str =String.valueOf(kitap.getAdet());
                    response =str;
                } else {
                    response ="";
                }

                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }));

        server.createContext("/KitapAdetAzaltma", (exchange -> {
            if ("GET".equals(exchange.getRequestMethod())) {
                String query = exchange.getRequestURI().getQuery();
                String[] params = query.split("&");
                String ID = params[0].split("=")[1];
                ID= URLDecoder.decode(ID, StandardCharsets.UTF_8);
                int IDI = Integer.parseInt(ID);

                Kitap_Servisi kitapServis = new Kitap_Servisi();
                Kitap kitap = kitapServis.KitapAdetAzaltma(IDI);

                String response;
                if (kitap != null) {
                    String str =String.valueOf(kitap.getAdet());
                    response =str;
                } else {
                    response ="";
                }

                exchange.sendResponseHeaders(200, response.getBytes().length);
                OutputStream os = exchange.getResponseBody();
                os.write(response.getBytes());
                os.close();
            }
        }));

        server.start();
        System.out.println("Kullanici API server'ı http://localhost:8081 adresinde çalışıyor...");
    }

}
