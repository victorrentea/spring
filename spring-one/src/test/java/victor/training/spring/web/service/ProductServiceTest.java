package victor.training.spring.web.service;


import org.assertj.core.api.Assertions;
import org.junit.Before;
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
import java.time.temporal.ChronoUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@SpringBootTest
@RunWith(SpringRunner.class)
@ActiveProfiles("db-mem")
@Transactional
public class ProductServiceTest  {

   public static final String UPC = "12321323";
   @Autowired
   private ProductService productService;
   @MockBean
   private SafetyClient safetyClient;

//   @MockBean // nu recomand
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
      LocalDateTime startTest = LocalDateTime.now();
      Mockito.when(safetyClient.isSafe(UPC)).thenReturn(true);
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
      Mockito.when(safetyClient.isSafe(UPC)).thenReturn(false);
      productService.createProduct(new ProductDto("scaun", UPC, -1L, ProductCategory.WIFE));
   }
}