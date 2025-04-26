FROM openjdk:24
WORKDIR /uygulama
# wait-for-it.sh dosyasını konteynere kopyala
COPY wait-for-it.sh /wait-for-it.sh
RUN chmod +x /wait-for-it.sh
COPY target/Veritabani_Servisi-1.0-SNAPSHOT-jar-with-dependencies.jar veritabaniservis.jar

