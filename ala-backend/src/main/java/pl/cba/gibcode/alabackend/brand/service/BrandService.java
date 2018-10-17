package pl.cba.gibcode.alabackend.brand.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cba.gibcode.alabackend.brand.filter.BrandFilterBuilder;
import pl.cba.gibcode.alabackend.brand.mapper.BrandMapper;
import pl.cba.gibcode.alabackend.brand.model.*;
import pl.cba.gibcode.alabackend.brand.repository.BrandRepository;
import pl.cba.gibcode.alabackend.shared.BusinessException;

@Service
@RequiredArgsConstructor
public class BrandService {

    private final BrandRepository brandRepository;
    private final BrandFilterBuilder brandFilterBuilder;

    @Transactional(readOnly = true)
    public Page<BrandResponseDto> findAll(Pageable pageable, BrandCriteriaDto criteria) {
        Predicate predicate = brandFilterBuilder.getBrandsFiltered(criteria);
        return brandRepository.findAll(predicate, pageable).map(BrandMapper::map);
    }

    @Transactional(readOnly = true)
    public Page<BrandResponseDto> findAllForAdmin(Pageable pageable, BrandCriteriaDto criteria) {
        Predicate predicate = brandFilterBuilder.getBrandsFilteredForAdmin(criteria);
        return brandRepository.findAll(predicate, pageable).map(BrandMapper::map);
    }

    @Transactional
    public BrandResponseDto createBrand(BrandRequestDto createBrandDto){
        brandRepository.findByName(createBrandDto.getName()).ifPresent( (brand) ->{
            throw new BusinessException(String.format("Brand with name %s already exists", brand.getName()));
        });
        Brand newBrand = BrandMapper.map(createBrandDto);
        return BrandMapper.map(brandRepository.save(newBrand));
    }

    @Transactional
    public BrandResponseDto updateBrand(BrandRequestDto updateBrandDto){
        brandRepository.findByName(updateBrandDto.getName()).ifPresent( (brand) ->{
            throw new BusinessException(String.format("Brand with name %s already exists", brand.getName()));
        });
        Brand brand = brandRepository.findById(updateBrandDto.getBrandId())
                .orElseThrow(() -> new BusinessException(String.format("Brand with name %s doesnt exist", updateBrandDto.getName())));
        brand.setImgUrl(updateBrandDto.getImageUrl());
        brand.setCategories(updateBrandDto.getCategories());
        brand.setName(updateBrandDto.getName());

        return BrandMapper.map(brand);
    }

    @Transactional
    public BrandResponseDto deleteBrand(BrandRequestDto deleteBrandDto){
        Brand brand = brandRepository.findById(deleteBrandDto.getBrandId())
                .orElseThrow(() -> new BusinessException(String.format("Brand with name %s doesnt exist", deleteBrandDto.getName())));
        brand.setDeleted(true);
        return BrandMapper.map(brand);
    }
}
