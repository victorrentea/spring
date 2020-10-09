package victor.training.spring.web.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import victor.training.spring.web.controller.dto.ProductDto;
import victor.training.spring.web.controller.dto.ProductSearchCriteria;
import victor.training.spring.web.controller.dto.ProductSearchResult;
import victor.training.spring.web.service.ProductService;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
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
//   @Autowired
//   JobLaucher

   @PostMapping("product/{id}/description")
   public void uploadDescription(@PathVariable long id, HttpServletRequest request) throws IOException {
//      byte[] zeceMega = IOUtils.toByteArray(request.getInputStream()); --- pute a out of memory

      File tempFile = File.createTempFile("", "");

      try (FileOutputStream outputStream = new FileOutputStream(tempFile)) {
         IOUtils.copy(request.getInputStream(), outputStream);
      }
//      jobLauncher.launch

   }

   @PostMapping("product/search")
   public List<ProductSearchResult> search(@RequestBody ProductSearchCriteria criteria) {
      return facade.searchProduct(criteria);
   }


}
