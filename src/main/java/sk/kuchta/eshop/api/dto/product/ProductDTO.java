package sk.kuchta.eshop.api.dto.product;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sk.kuchta.eshop.api.dto.specification.ProductSpecificationDTO;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {
    private String name;
    private String description;
    private int price;
    private ProductSpecificationDTO productSpecificationDTO;
    private String productCode;
}
