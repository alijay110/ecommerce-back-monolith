package pl.cba.gibcode.alabackend.brand.mapper;

import pl.cba.gibcode.alabackend.brand.model.Brand;
import pl.cba.gibcode.alabackend.brand.model.BrandResponseDto;
import pl.cba.gibcode.alabackend.brand.model.BrandRequestDto;

import static java.util.Objects.nonNull;

public class BrandMapper {

    public static BrandResponseDto map(Brand brand){
        return BrandResponseDto.of(brand.getName(), brand.getImgUrl(), brand.getMaxDiscount());
    }

    public static Brand map(BrandRequestDto createBrandDto){
        Brand brand = new Brand();
        brand.setName(createBrandDto.getName());
        brand.setCategories(createBrandDto.getCategories());
        if(nonNull(createBrandDto.getImageUrl())){
            brand.setImgUrl(createBrandDto.getImageUrl());
        }
        return brand;
    }
}
