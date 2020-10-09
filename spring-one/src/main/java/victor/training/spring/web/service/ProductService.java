package victor.training.spring.web.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import victor.training.spring.web.domain.Product;
import victor.training.spring.web.infra.SafetyClient;
import victor.training.spring.web.repo.ProductRepo;
import victor.training.spring.web.repo.SupplierRepo;
import victor.training.spring.web.controller.dto.ProductDto;
import victor.training.spring.web.controller.dto.ProductSearchCriteria;
import victor.training.spring.web.controller.dto.ProductSearchResult;

import java.time.Clock;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private final SafetyClient safetyClient;
    private final ProductRepo productRepo;
    private final SupplierRepo supplierRepo;

    public long createProduct(ProductDto productDto) {
        boolean safe = safetyClient.isSafe(productDto.upc);
        if (!safe) {
            throw new IllegalStateException("Product is not safe: " + productDto.upc);
        }

        Product product = new Product();
        product.setName(productDto.name);
        product.setCategory(productDto.category);
        product.setUpc(productDto.upc);
        product.setSupplier(supplierRepo.getOne(productDto.supplierId));
        // TODO CR check that the supplier is active!
        product.setCreateDate(LocalDateTime.now());
        productRepo.save(product);
        return product.getId();
    }


    @Autowired
    private Clock clock;

    public void checkProduct(Long id) {
        // si apoi mockuiesti timpul co @MockBean sau Clock.fixed
        Product product = productRepo.findById(id).get();
        if (product.getCreateDate().isAfter(LocalDateTime.now(clock).minusHours(1))) {
            System.out.println("Chestii");
        }
    }

    public List<ProductSearchResult> searchProduct(ProductSearchCriteria criteria) {
        return productRepo.search(criteria);
    }
}
