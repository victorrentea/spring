package victor.training.spring.web.repo;

import victor.training.spring.web.controller.dto.ProductSearchCriteria;
import victor.training.spring.web.controller.dto.ProductSearchResult;

import java.util.List;

public interface ProductRepoSearch {
    List<ProductSearchResult> search(ProductSearchCriteria criteria);
}
