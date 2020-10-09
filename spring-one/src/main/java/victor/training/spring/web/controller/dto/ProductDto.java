package victor.training.spring.web.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import victor.training.spring.web.domain.ProductCategory;

@Data
@AllArgsConstructor
public class ProductDto {
	public String name;
	public String upc;
	public Long supplierId;
	public ProductCategory category;
}
