package sk.kuchta.eshop.api.dto.specification;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecificationDTO {

    private String brand;
    private String model;
    private Map<String, String> genericSpecificationMap = new HashMap<>();
}
