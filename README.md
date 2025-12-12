# Rest Assured API Test Projesi

## 📋 Proje Açıklaması

Bu proje, **Yazılım Test Mühendisliği** dersi kapsamında hazırlanmış bir API test projesidir. **Rest Assured** kütüphanesi kullanılarak Java/Maven/jUnit4 ile otomatik regresyon testleri yazılmıştır.

### 🎯 Proje Gereksinimleri

- ✅ Rest Assured kütüphanesi kullanımı
- ✅ Java/Maven/jUnit4 yapısı
- ✅ En az bir servis çağrısı
- ✅ Status code kontrolü
- ✅ Response body içerisinde beklenen değer kontrolleri
- ✅ X süre altında cevap kontrolü (response time)
- ✅ GET ve POST örnekleri
- ✅ Request body içeren testler

## 🛠️ Teknolojiler

- **Java 8+**
- **Maven 3.x**
- **Rest Assured 5.3.2**
- **jUnit 4.13.2**
- **Hamcrest 2.2**

## 📁 Proje Yapısı

```
Yazılım_Test_Mühendisliği/
├── pom.xml                                    # Maven proje yapılandırması
├── README.md                                  # Proje dokümantasyonu
└── src/
    ├── main/
    │   └── java/
    │       └── com/
    │           └── test/
    │               └── restassured/
    └── test/
        └── java/
            └── com/
                └── test/
                    └── restassured/
                        ├── GetRequestTest.java      # GET request testleri
                        ├── PostRequestTest.java     # POST request testleri
                        └── ApiTestSuite.java        # Test suite (tüm testleri çalıştırma)
```

## 🚀 Kurulum ve Çalıştırma

### Ön Gereksinimler

1. **Java JDK 8 veya üzeri** yüklü olmalı
   ```bash
   java -version
   ```

2. **Maven 3.x** yüklü olmalı
   ```bash
   mvn -version
   ```

### Adım 1: Projeyi İndirin/Klonlayın

Proje dosyalarını bilgisayarınıza indirin.

### Adım 2: Maven Bağımlılıklarını Yükleyin

Proje dizininde terminal/komut satırını açın ve şu komutu çalıştırın:

```bash
mvn clean install
```

Bu komut:
- Tüm Maven bağımlılıklarını indirir (Rest Assured, jUnit, vb.)
- Projeyi derler
- Testleri çalıştırır

### Adım 3: Testleri Çalıştırma

#### Tüm Testleri Çalıştırma

```bash
mvn test
```

#### Belirli Bir Test Sınıfını Çalıştırma

```bash
# GET testlerini çalıştır
mvn test -Dtest=GetRequestTest

# POST testlerini çalıştır
mvn test -Dtest=PostRequestTest
```

#### IDE'den Çalıştırma

- **IntelliJ IDEA**: Test sınıfına sağ tıklayıp "Run" seçeneğini kullanın
- **Eclipse**: Test sınıfına sağ tıklayıp "Run As > JUnit Test" seçeneğini kullanın
- **VS Code**: Test metoduna tıklayıp "Run Test" butonunu kullanın

## 📝 Test Senaryoları

### GET Request Testleri (`GetRequestTest.java`)

#### 1. `testGetAllPosts()`
- **Endpoint**: `GET /posts`
- **Kontroller**:
  - ✅ Status Code: 200 OK
  - ✅ Response Time: < 3000 ms
  - ✅ Response body boş değil
  - ✅ En az bir post döndürülüyor
  - ✅ Post alanları (id, title, userId) kontrol ediliyor

#### 2. `testGetPostById()`
- **Endpoint**: `GET /posts/{id}`
- **Kontroller**:
  - ✅ Status Code: 200 OK
  - ✅ Response Time: < 3000 ms
  - ✅ Dönen post ID'si eşleşiyor
  - ✅ Post alanları (title, body, userId) kontrol ediliyor

#### 3. `testGetPostsByUserId()`
- **Endpoint**: `GET /posts?userId={userId}`
- **Kontroller**:
  - ✅ Status Code: 200 OK
  - ✅ Response Time: < 3000 ms
  - ✅ Tüm postların userId'si eşleşiyor

### POST Request Testleri (`PostRequestTest.java`)

#### 1. `testCreatePostWithJsonBody()`
- **Endpoint**: `POST /posts`
- **Request Body**: JSON (Map kullanarak)
- **Kontroller**:
  - ✅ Status Code: 201 Created
  - ✅ Response Time: < 3000 ms
  - ✅ Oluşturulan post ID'si var
  - ✅ Request body'deki değerler response'da eşleşiyor

#### 2. `testCreatePostWithStringJsonBody()`
- **Endpoint**: `POST /posts`
- **Request Body**: String JSON
- **Kontroller**:
  - ✅ Status Code: 201 Created
  - ✅ Response Time: < 3000 ms
  - ✅ Response body kontrolleri

#### 3. `testCreateComment()`
- **Endpoint**: `POST /comments`
- **Request Body**: JSON (Map kullanarak)
- **Kontroller**:
  - ✅ Status Code: 201 Created
  - ✅ Response Time: < 3000 ms
  - ✅ Email format kontrolü
  - ✅ Tüm alanlar eşleşiyor

## 🔍 Test API'si

Proje, test amaçlı olarak **JSONPlaceholder** API'sini kullanmaktadır:
- **Base URL**: `https://jsonplaceholder.typicode.com`
- **Ücretsiz test API'si**
- **Dokümantasyon**: https://jsonplaceholder.typicode.com/

## 📊 Test Sonuçları

Testler başarıyla çalıştığında terminalde şu çıktıyı göreceksiniz:

```
✓ GET /posts testi başarılı!
  - Status Code: 200
  - Response Time: 1234 ms
  - İlk Post ID: 1

✓ POST /posts testi başarılı!
  - Status Code: 201
  - Response Time: 567 ms
  - Oluşturulan Post ID: 101
  - Post Title: Test Post Başlığı
```

## 🎓 Öğrenilen Konular

Bu proje ile şu konular uygulanmıştır:

1. **Rest Assured Temelleri**
   - GET ve POST request'leri
   - Request body oluşturma (Map ve String JSON)
   - Response validation

2. **Test Kontrolleri**
   - Status code kontrolü
   - Response body validation
   - Response time kontrolü
   - JSON path kullanımı

3. **Maven Proje Yapısı**
   - pom.xml yapılandırması
   - Bağımlılık yönetimi
   - Test çalıştırma

4. **jUnit 4**
   - Test metodları
   - Assertion'lar
   - Test suite oluşturma

## 🔧 Özelleştirme

### Response Time Limitini Değiştirme

Test sınıflarında `MAX_RESPONSE_TIME` değişkenini değiştirerek maksimum response time'ı ayarlayabilirsiniz:

```java
private static final long MAX_RESPONSE_TIME = 5000; // 5 saniye
```

### Farklı API Kullanma

`BASE_URL` değişkenini değiştirerek farklı bir API kullanabilirsiniz:

```java
private static final String BASE_URL = "https://api.example.com";
```

## 📚 Kaynaklar

- [Rest Assured Dokümantasyonu](https://rest-assured.io/)
- [jUnit 4 Dokümantasyonu](https://junit.org/junit4/)
- [Maven Dokümantasyonu](https://maven.apache.org/)
- [JSONPlaceholder API](https://jsonplaceholder.typicode.com/)

## 👤 Geliştirici

Bu proje Yazılım Test Mühendisliği dersi kapsamında hazırlanmıştır.

## 📄 Lisans

Bu proje eğitim amaçlıdır.

---

**Not**: Testlerin çalışması için internet bağlantısı gereklidir (JSONPlaceholder API'sine erişim için).

