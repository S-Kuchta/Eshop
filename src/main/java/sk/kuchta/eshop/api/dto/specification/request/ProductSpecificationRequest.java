package sk.kuchta.eshop.api.dto.specification.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import sk.kuchta.eshop.api.dto.specification.ProductSpecificationDTO;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecificationRequest {

    private ProductSpecificationDTO productSpecificationDTO;
}
