package pl.cba.gibcode.alabackend.brand.model;
import lombok.*;
import org.hibernate.validator.constraints.NotEmpty;

import java.math.BigDecimal;

@Value(staticConstructor = "of")
public class BrandResponseDto {
    private String name;
    private String imgUrl;
    private BigDecimal maxDiscount;
}
