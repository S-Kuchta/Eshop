package sk.kuchta.eshop.api.dto.specification.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductSpecificationRequest {

    private String brand;
    private String model;
    private Map<String, String> genericSpecificationMap = new HashMap<>();
}
