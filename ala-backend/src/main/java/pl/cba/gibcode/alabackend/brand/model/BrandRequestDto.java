package pl.cba.gibcode.alabackend.brand.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import java.util.Set;

@NoArgsConstructor
@Getter
@Setter
public class BrandRequestDto {

	private Long brandId;
	@NotEmpty
	private String name;

	private String imageUrl;

	@NotNull
	private Set<CategoryEnum> categories;
}
