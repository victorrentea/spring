package victor.training.spring.web.service;


import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import victor.training.spring.web.controller.dto.ProductDto;
import victor.training.spring.web.domain.Product;
import victor.training.spring.web.domain.ProductCategory;
import victor.training.spring.web.domain.Supplier;
import victor.training.spring.web.infra.SafetyClient;
import victor.training.spring.web.repo.ProductRepo;
import victor.training.spring.web.repo.SupplierRepo;

import java.time.LocalDateTime;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest(properties = "safety.service.url.base=http://localhost:9999")
@RunWith(SpringRunner.class)
@ActiveProfiles("db-mem")
@Transactional
public class ProductServiceWireMockTest {

   public static final String UPC = "12321323";
   @Autowired
   private ProductService productService;

   @Rule
   public WireMockRule wireMock = new WireMockRule(9999);

   @Autowired
   private SupplierRepo supplierRepo;
   @Autowired
   private ProductRepo productRepo;
   private Long supplierId;

   @Before
   public void initialize() {
      supplierId = supplierRepo.save(new Supplier()).getId();
   }
   @Test
   public void testCreateProduct() {
      // programeaza un server WireMock pornit pe localhost din teste
      WireMock.stubFor(get(urlEqualTo("/product/"+UPC+"/safety"))
          .willReturn(aResponse()
              .withStatus(200)
              .withHeader("Content-Type", "application/json")
              .withBody("{\"entries\": [{\"category\": \"SAFE\", \"detailsUrl\":  \"url\"}]}"))); // override


      LocalDateTime startTest = LocalDateTime.now();
      long id = productService.createProduct(new ProductDto("scaun", UPC, supplierId, ProductCategory.WIFE));
      Product product = productRepo.findById(id).get();

      assertEquals("scaun", product.getName());
      assertEquals(ProductCategory.WIFE, product.getCategory());
      assertEquals(UPC, product.getUpc());
      assertEquals(supplierId, product.getSupplier().getId());

      assertNotNull(product.getCreateDate());
//      LocalDateTime today = LocalDateTime.now().truncatedTo(ChronoUnit.DAYS);
//      assertEquals(today, product.getCreateDate().truncatedTo(ChronoUnit.DAYS));

      Assertions.assertThat(product.getCreateDate()).isAfter(startTest);
   }

   @Test(expected = IllegalStateException.class)
   public void throwsForUnsafeProduct() {

      WireMock.stubFor(get(urlEqualTo("/product/"+UPC+"/safety"))
          .willReturn(aResponse()
              .withStatus(200)
              .withHeader("Content-Type", "application/json")
              .withBody("{\"entries\": [{\"category\": \"something\", \"detailsUrl\":  \"url\"}]}"))); // override

      productService.createProduct(new ProductDto("scaun", UPC, -1L, ProductCategory.WIFE));
   }
}