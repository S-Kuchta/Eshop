package sk.kuchta.eshop.implementation.entity.productSpecification.hardware;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import sk.kuchta.eshop.implementation.entity.productSpecification.ProductSpecification;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ProcessorSpecification extends ProductSpecification {

    @Column
    private String frequency;

    @Column
    private String maxFrequency;
}
