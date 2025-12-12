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
 * - Response time kontrolü (belirli süre altında) 3000ms 
 */
public class GetRequestTest {

    // Base URL(test edilecek servis url'si) - JSONPlaceholder (hazır çekilmiş api)
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    
    // Maksimum kabul edilebilir response time (milisaniye)
    private static final long MAX_RESPONSE_TIME = 3000; // 3 saniye

    @BeforeClass
    public static void setup() {
        // RestAssured base URI ayarlanır
        RestAssured.baseURI = BASE_URL;
        
    
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
                .body("$", is(notNullValue())) 
                .body("size()", greaterThan(0)) 
                .body("[0].id", is(notNullValue())) 
                .body("[0].title", is(notNullValue())) 
                .body("[0].userId", is(notNullValue())) 
                .extract()
                .response();

        
        assertNotNull("Response boş olamaz", response);
        assertEquals("Content-Type JSON olmalı", 
                "application/json; charset=utf-8", 
                response.getContentType());
        
        
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
                .statusCode(200)

                .time(lessThan(MAX_RESPONSE_TIME))
                // body kontrolleri
                .body("id", equalTo(postId)) 
                .body("userId", is(notNullValue())) 
                .body("title", is(notNullValue())) 
                .body("body", is(notNullValue())) 
                .body("title", not(emptyString())) 
                .body("body", not(emptyString())) 
                .extract()
                .response();

        
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
        int userId = 1; 
        
        Response response = given()
                .queryParam("userId", userId)
                .when()
                .get("/posts")
                .then()
                
                .statusCode(200)
                
                .time(lessThan(MAX_RESPONSE_TIME))
            
                .body("$", is(notNullValue())) 
                .body("size()", greaterThan(0)) 
                .body("userId", everyItem(equalTo(userId))) 
                .extract()
                .response();

        
        int postCount = response.jsonPath().getList("$").size();
        assertTrue("En az bir post dönmeli", postCount > 0);
        
        System.out.println("✓ GET /posts?userId=" + userId + " testi başarılı!");
        System.out.println("  - Status Code: " + response.getStatusCode());
        System.out.println("  - Response Time: " + response.getTime() + " ms");
        System.out.println("  - Post Sayısı: " + postCount);
    }
}

