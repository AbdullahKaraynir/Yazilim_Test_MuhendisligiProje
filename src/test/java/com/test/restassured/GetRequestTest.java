package com.test.restassured;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * GET Request Test Sınıfı
 * JSONPlaceholder API'sini kullanarak GET istekleri test edilir.
 * 
 * Test Senaryoları:
 * - Status code kontrolü
 * - Response body içerisinde beklenen değer kontrolleri
 * - Response time kontrolü (belirli süre altında)
 */
public class GetRequestTest {

    // Base URL - JSONPlaceholder (ücretsiz test API'si)
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    
    // Maksimum kabul edilebilir response time (milisaniye)
    private static final long MAX_RESPONSE_TIME = 3000; // 3 saniye

    @BeforeClass
    public static void setup() {
        // RestAssured base URI ayarlanır
        RestAssured.baseURI = BASE_URL;
        
        // Logging ayarları (isteğe bağlı - debug için)
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    /**
     * Test 1: Tüm postları getir (GET /posts)
     * - Status code: 200 OK
     * - Response body: Array döndürmeli
     * - Response time: 3 saniye altında olmalı
     */
    @Test
    public void testGetAllPosts() {
        Response response = given()
                .when()
                .get("/posts")
                .then()
                // Status code kontrolü
                .statusCode(200)
                // Response time kontrolü (milisaniye cinsinden)
                .time(lessThan(MAX_RESPONSE_TIME))
                // Response body kontrolleri
                .body("$", is(notNullValue())) // Response boş olmamalı
                .body("size()", greaterThan(0)) // En az bir eleman olmalı
                .body("[0].id", is(notNullValue())) // İlk elemanın id'si olmalı
                .body("[0].title", is(notNullValue())) // İlk elemanın title'ı olmalı
                .body("[0].userId", is(notNullValue())) // İlk elemanın userId'si olmalı
                .extract()
                .response();

        // Ek kontroller
        assertNotNull("Response boş olamaz", response);
        assertEquals("Content-Type JSON olmalı", 
                "application/json; charset=utf-8", 
                response.getContentType());
        
        // Response body'den spesifik değer kontrolü
        int firstPostId = response.jsonPath().getInt("[0].id");
        assertTrue("İlk post ID'si pozitif olmalı", firstPostId > 0);
        
        System.out.println("✓ GET /posts testi başarılı!");
        System.out.println("  - Status Code: " + response.getStatusCode());
        System.out.println("  - Response Time: " + response.getTime() + " ms");
        System.out.println("  - İlk Post ID: " + firstPostId);
    }

    /**
     * Test 2: Belirli bir postu getir (GET /posts/{id})
     * - Status code: 200 OK
     * - Response body: Beklenen alanlar ve değerler kontrol edilir
     * - Response time: 3 saniye altında olmalı
     */
    @Test
    public void testGetPostById() {
        int postId = 1; // Test için ID 1 kullanılıyor
        
        Response response = given()
                .pathParam("id", postId)
                .when()
                .get("/posts/{id}")
                .then()
                // Status code kontrolü
                .statusCode(200)
                // Response time kontrolü
                .time(lessThan(MAX_RESPONSE_TIME))
                // Response body kontrolleri
                .body("id", equalTo(postId)) // ID eşleşmeli
                .body("userId", is(notNullValue())) // userId olmalı
                .body("title", is(notNullValue())) // title olmalı
                .body("body", is(notNullValue())) // body olmalı
                .body("title", not(emptyString())) // title boş olmamalı
                .body("body", not(emptyString())) // body boş olmamalı
                .extract()
                .response();

        // Ek kontroller
        String title = response.jsonPath().getString("title");
        int userId = response.jsonPath().getInt("userId");
        
        assertNotNull("Title boş olamaz", title);
        assertTrue("User ID pozitif olmalı", userId > 0);
        assertTrue("Title uzunluğu 0'dan büyük olmalı", title.length() > 0);
        
        System.out.println("✓ GET /posts/" + postId + " testi başarılı!");
        System.out.println("  - Status Code: " + response.getStatusCode());
        System.out.println("  - Response Time: " + response.getTime() + " ms");
        System.out.println("  - Post Title: " + title);
        System.out.println("  - User ID: " + userId);
    }

    /**
     * Test 3: Kullanıcının postlarını getir (GET /posts?userId={userId})
     * - Status code: 200 OK
     * - Response body: Tüm postların userId'si eşleşmeli
     * - Response time: 3 saniye altında olmalı
     */
    @Test
    public void testGetPostsByUserId() {
        int userId = 1; // Test için userId 1 kullanılıyor
        
        Response response = given()
                .queryParam("userId", userId)
                .when()
                .get("/posts")
                .then()
                // Status code kontrolü
                .statusCode(200)
                // Response time kontrolü
                .time(lessThan(MAX_RESPONSE_TIME))
                // Response body kontrolleri
                .body("$", is(notNullValue())) // Response boş olmamalı
                .body("size()", greaterThan(0)) // En az bir post olmalı
                .body("userId", everyItem(equalTo(userId))) // Tüm postların userId'si eşleşmeli
                .extract()
                .response();

        // Ek kontroller
        int postCount = response.jsonPath().getList("$").size();
        assertTrue("En az bir post dönmeli", postCount > 0);
        
        System.out.println("✓ GET /posts?userId=" + userId + " testi başarılı!");
        System.out.println("  - Status Code: " + response.getStatusCode());
        System.out.println("  - Response Time: " + response.getTime() + " ms");
        System.out.println("  - Post Sayısı: " + postCount);
    }
}

