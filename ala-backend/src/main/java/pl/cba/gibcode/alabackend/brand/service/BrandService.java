package pl.cba.gibcode.alabackend.brand.service;

import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.cba.gibcode.alabackend.brand.filter.BrandFilterBuilder;
import pl.cba.gibcode.alabackend.brand.mapper.BrandMapper;
import pl.cba.gibcode.alabackend.brand.model.BrandCriteriaDto;
import pl.cba.gibcode.alabackend.brand.model.BrandResponseDto;
import pl.cba.gibcode.alabackend.brand.repository.BrandRepository;

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
}
