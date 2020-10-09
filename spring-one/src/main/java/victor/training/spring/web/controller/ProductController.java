package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.ProductDto;
import victor.training.spring.web.controller.dto.ProductSearchCriteria;
import victor.training.spring.web.controller.dto.ProductSearchResult;
import victor.training.spring.web.service.ProductService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class ProductController {

   private final ProductService facade;

   @PostMapping("product/create")
   public Long create(@RequestBody ProductDto productDto) {
      return facade.createProduct(productDto);
   }

   @PostMapping("product/search")
   public List<ProductSearchResult> search(@RequestBody ProductSearchCriteria criteria) {
      return facade.searchProduct(criteria);
   }


}
