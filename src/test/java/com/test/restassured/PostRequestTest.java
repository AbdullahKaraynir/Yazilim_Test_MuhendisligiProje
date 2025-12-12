package com.test.restassured;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

/**
 * POST istek Test Sınıfı
 * JSONPlaceholder API'sini kullanarak POST istekleri test edilir.
 * 
 * Test Senaryoları:
 * - Status code kontrolü
 * - Request body ile POST isteği
 * - Response body içerisinde beklenen değer kontrolleri
 * - Response time kontrolü (belirli süre altında)
 */
public class PostRequestTest {

    // Base URL - JSONPlaceholder (ücretsiz test API'si)
    private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    
    // Maksimum response time (milisaniye)
    private static final long MAX_RESPONSE_TIME = 3000; // 3 saniye

    @BeforeClass
    public static void setup() {
        
        RestAssured.baseURI = BASE_URL;
        
        
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    /**
     * Test 1: Yeni post oluştur (POST /posts)
     * - Status code: 201 Created 
     * - Request body: JSON formatında post verisi
     * - Response body: Oluşturulan post bilgileri kontrol edilir
     * - Response time: 3 saniye altında olmalı
     */
    @Test
    public void testCreatePostWithJsonBody() {
        // Request body oluşturuluyor (Map kullanarak)
        Map<String, Object> postData = new HashMap<>();
        postData.put("title", "Test Post Başlığı");
        postData.put("body", "Bu bir test post içeriğidir. Rest Assured ile API testi yapılıyor.");
        postData.put("userId", 1);

        Response response = given()
                .contentType(ContentType.JSON) // Content-Type: application/json
                .body(postData) 
                .when()
                .post("/posts")
                .then()
                // Status code kontrolü (JSONPlaceholder 201 döndürür)
                .statusCode(201)
                // Response time kontrolü
                .time(lessThan(MAX_RESPONSE_TIME))
                // Response body kontrolleri
                .body("id", is(notNullValue())) 
                .body("title", equalTo(postData.get("title"))) 
                .body("body", equalTo(postData.get("body"))) 
                .body("userId", equalTo(postData.get("userId"))) 
                .extract()
                .response();

        
        int createdPostId = response.jsonPath().getInt("id");
        String responseTitle = response.jsonPath().getString("title");
        String responseBody = response.jsonPath().getString("body");
        int responseUserId = response.jsonPath().getInt("userId");
        
        assertNotNull("Post ID boş olamaz", createdPostId);
        assertEquals("Title eşleşmeli", postData.get("title"), responseTitle);
        assertEquals("Body eşleşmeli", postData.get("body"), responseBody);
        assertEquals("UserId eşleşmeli", postData.get("userId"), responseUserId);
        
        System.out.println("✓ POST /posts testi başarılı!");
        System.out.println("  - Status Code: " + response.getStatusCode());
        System.out.println("  - Response Time: " + response.getTime() + " ms");
        System.out.println("  - Oluşturulan Post ID: " + createdPostId);
        System.out.println("  - Post Title: " + responseTitle);
    }

    /**
     * Test 2: Yeni post oluştur (String JSON body ile)
     * - Status code: 201 Created
     * - Request body: String formatında JSON
     * - Response body: Beklenen değerler kontrol edilir
     * - Response time: 3 saniye altında olmalı
     */
    @Test
    public void testCreatePostWithStringJsonBody() {
        // Request body String olarak oluşturuluyor
        String jsonBody = "{\n" +
                "  \"title\": \"String JSON ile Test Post\",\n" +
                "  \"body\": \"Bu test String formatında JSON body kullanıyor.\",\n" +
                "  \"userId\": 2\n" +
                "}";

        Response response = given()
                .contentType(ContentType.JSON)
                .body(jsonBody) 
                .when()
                .post("/posts")
                .then()
                
                .statusCode(201)
            
                .time(lessThan(MAX_RESPONSE_TIME))
                
                .body("id", is(notNullValue()))
                .body("title", is(notNullValue()))
                .body("body", is(notNullValue()))
                .body("userId", equalTo(2))
                .body("title", containsString("String JSON"))
                .extract()
                .response();

        
        assertNotNull("Response boş olamaz", response);
        assertEquals("Content-Type JSON olmalı", 
                "application/json; charset=utf-8", 
                response.getContentType());
        
        String title = response.jsonPath().getString("title");
        assertTrue("Title 'String JSON' içermeli", title.contains("String JSON"));
        
        System.out.println("✓ POST /posts (String JSON) testi başarılı!");
        System.out.println("  - Status Code: " + response.getStatusCode());
        System.out.println("  - Response Time: " + response.getTime() + " ms");
        System.out.println("  - Post Title: " + title);
    }

    /**
     * Test 3: Yorum oluştur (POST /comments)
     * - Status code: 201 Created
     * - Request body: JSON formatında comment verisi
     * - Response body: Oluşturulan comment bilgileri kontrol edilir
     * - Response time: 3 saniye altında olmalı
     */
    @Test
    public void testCreateComment() {
        // Request body oluşturuluyor
        Map<String, Object> commentData = new HashMap<>();
        commentData.put("postId", 1);
        commentData.put("name", "Test Kullanıcı");
        commentData.put("email", "test@example.com");
        commentData.put("body", "Bu bir test yorumudur. Rest Assured ile API testi yapılıyor.");

        Response response = given()
                .contentType(ContentType.JSON)
                .body(commentData)
                .when()
                .post("/comments")
                .then()
                
                .statusCode(201)
                
                .time(lessThan(MAX_RESPONSE_TIME))
                
                .body("id", is(notNullValue()))
                .body("postId", equalTo(commentData.get("postId")))
                .body("name", equalTo(commentData.get("name")))
                .body("email", equalTo(commentData.get("email")))
                .body("body", equalTo(commentData.get("body")))
                .body("email", matchesPattern("^[A-Za-z0-9+_.-]+@(.+)$")) // Email format kontrolü
                .extract()
                .response();

        
        int commentId = response.jsonPath().getInt("id");
        String email = response.jsonPath().getString("email");
        
        assertNotNull("Comment ID boş olamaz", commentId);
        assertTrue("Email geçerli formatta olmalı", email.contains("@"));
        
        System.out.println("✓ POST /comments testi başarılı!");
        System.out.println("  - Status Code: " + response.getStatusCode());
        System.out.println("  - Response Time: " + response.getTime() + " ms");
        System.out.println("  - Oluşturulan Comment ID: " + commentId);
        System.out.println("  - Email: " + email);
    }
}

