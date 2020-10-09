package victor.training.spring.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import victor.training.spring.web.domain.ProductCategory;

@NoArgsConstructor
@AllArgsConstructor
public class ProductSearchCriteria { // smells like JSON
    public String name;
    public ProductCategory category;
    public Long supplierId;
    // name: [    ]
    // category: [    |v]
    // supplier: [    |v]
    // [search]

}
