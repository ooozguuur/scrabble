# Scrabble Oyunu

Kullanılan Teknojiler
 - Java8
 - Spring
 - Spring Rest
 - JPA
 - Hibernate
 - Postgresql
 - H2
 - Log4j
 - Junit
 - Mockito
 - Swagger
 - Mapstruct

Scrabble Oyunu bir maven projesidir. 'database.properties' den database tanımlaması yapılarak, her hangi bir konfigürasyon gerekmeden uygulama varsayılan olarak 'prod' environment ile tomcat, wildfly v.s server da çalışabilmekte. Proje ilk çalıştırıldığında sözlükteki kelimeler eklenip eklenilmediği kontrol edilmekte , eklenmemiş ise database bunlar yazılmakta. Swagger 'dan yazılmış olan rest servislere erişilebilmektedir. Mapstruct kullanılarak gerekli olmayan datalar rest service 'den dönülmemekte.

Swagger URL = http://${server_address}/scrabble/swagger-ui.html#

Eksik Noktalar:
    
   - Unit testler tamamlanmadı.
   - Integration testlere başlayamadım.
   - Sözlük datasını elasticsearch'e , dil destekli olarak çok daha hızlı yazmak istiyordum fakat başlayamadım.
   - Uygulama loglarını Logstash ile elasticsearch yazmak istiyordum fakat başlayamadım.
   

