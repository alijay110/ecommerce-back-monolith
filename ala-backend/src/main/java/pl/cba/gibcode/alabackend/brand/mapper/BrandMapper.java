package pl.cba.gibcode.alabackend.brand.mapper;

import pl.cba.gibcode.alabackend.brand.model.Brand;
import pl.cba.gibcode.alabackend.brand.model.BrandResponseDto;

public class BrandMapper {

    public static BrandResponseDto map(Brand brand){
        return BrandResponseDto.of(brand.getId(), brand.getName(), brand.getImgUrl(), brand.getMaxDiscount());
    }
}
