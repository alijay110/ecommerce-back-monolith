package pl.cba.gibcode.alabackend.brand.web;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.cba.gibcode.alabackend.brand.model.BrandCriteriaDto;
import pl.cba.gibcode.alabackend.brand.model.BrandResponseDto;
import pl.cba.gibcode.alabackend.brand.service.BrandService;

@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class BrandController {

    private final BrandService brandService;

    @GetMapping("brands")
    public ResponseEntity<Page<BrandResponseDto>> getBrandBy(Pageable pageable, @RequestBody BrandCriteriaDto criteria) {
        return ResponseEntity.ok(brandService.findAll(pageable, criteria));
    }

}
