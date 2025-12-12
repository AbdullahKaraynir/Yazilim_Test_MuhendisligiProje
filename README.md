# Rest Assured API Test Projesi

##  Proje Açıklaması

Bu proje, **Yazılım Test Mühendisliği** dersi kapsamında hazırlanmış bir API test projesidir. **Rest Assured** kütüphanesi kullanılarak Java/Maven/jUnit4 ile otomatik regresyon testleri yazılmıştır.

###  Proje Gereksinimleri

 Rest Assured kütüphanesi kullanımı
 Java/Maven/jUnit4 yapısı
 En az bir servis çağrısı
 Status code kontrolü
   Response body içerisinde beklenen değer kontrolleri
   X süre altında cevap kontrolü (response time)
   GET ve POST örnekleri
  Request body içeren testler

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



## Test Senaryoları

### GET Request Testleri (`GetRequestTest.java`)

#### 1. `testGetAllPosts()`
- **Endpoint**: `GET /posts`
- **Kontroller**:
   Status Code: 200 OK
     Response Time: < 3000 ms
     Response body boş değil
     En az bir post döndürülüyor
     Post alanları (id, title, userId) kontrol ediliyor

#### 2. `testGetPostById()`
- **Endpoint**: `GET /posts/{id}`
- **Kontroller**:
   Status Code: 200 OK
     Response Time: < 3000 ms
     Dönen post ID'si eşleşiyor
     Post alanları (title, body, userId) kontrol ediliyor

#### 3. `testGetPostsByUserId()`
- **Endpoint**: `GET /posts?userId={userId}`
- **Kontroller**:
   Status Code: 200 OK
     Response Time: < 3000 ms
     Tüm postların userId'si eşleşiyor

### POST Request Testleri (`PostRequestTest.java`)

#### 1. `testCreatePostWithJsonBody()`
- **Endpoint**: `POST /posts`
- **Request Body**: JSON (Map kullanarak)
- **Kontroller**:
   Status Code: 201 Created
     Response Time: < 3000 ms
     Oluşturulan post ID'si var
     Request body'deki değerler response'da eşleşiyor

#### 2. `testCreatePostWithStringJsonBody()`
- **Endpoint**: `POST /posts`
- **Request Body**: String JSON
- **Kontroller**:
   Status Code: 201 Created
     Response Time: < 3000 ms
     Response body kontrolleri

#### 3. `testCreateComment()`
- **Endpoint**: `POST /comments`
- **Request Body**: JSON (Map kullanarak)
- **Kontroller**:
   Status Code: 201 Created
     Response Time: < 3000 ms
     Email format kontrolü
     Tüm alanlar eşleşiyor

## 🔍 Test API'si

Proje, test amaçlı olarak **JSONPlaceholder** API'sini kullanmaktadır:
- **Base URL**: `https://jsonplaceholder.typicode.com`
- **Ücretsiz test API'si**
- **Dokümantasyon**: https://jsonplaceholder.typicode.com/

## 📊 Test Sonuçları


GET /posts testi başarılı!
  - Status Code: 200
  - Response Time: 1234 ms
  - İlk Post ID: 1

 POST /posts testi başarılı!
  - Status Code: 201
  - Response Time: 567 ms
  - Oluşturulan Post ID: 101
  - Post Title: Test Post Başlığı
```






Bu proje eğitim amaçlıdır.

---

**Not**: Testlerin çalışması için internet bağlantısı gereklidir (JSONPlaceholder API'sine erişim için).

