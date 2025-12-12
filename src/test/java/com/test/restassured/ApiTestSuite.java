package com.test.restassured;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

/**
 * Test Suite Sınıfı
 * Tüm test sınıflarını bir arada çalıştırmak için kullanılır.
 * 
 * Kullanım:
 * - IDE'den bu sınıfı çalıştırarak tüm testleri toplu olarak çalıştırabilirsiniz
 * - Maven ile: mvn test
 */
@RunWith(Suite.class)
@Suite.SuiteClasses({
        GetRequestTest.class,
        PostRequestTest.class
})
public class ApiTestSuite {
    // Bu sınıf sadece test suite tanımlaması için kullanılır
    // İçerik gerekmez
}

