package pl.cba.gibcode.alabackend.brand.web;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.cba.gibcode.alabackend.brand.model.BrandCriteriaDto;
import pl.cba.gibcode.alabackend.brand.model.BrandResponseDto;
import pl.cba.gibcode.alabackend.brand.model.BrandRequestDto;
import pl.cba.gibcode.alabackend.brand.service.BrandService;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor

@RequestMapping("api")
@Api
public class BrandController {

    private final BrandService brandService;

    @ApiOperation(
            value = "Get all brands",
            notes = "This call is used to get all brands filtered",
            httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Brands returned"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Unknown error")
    })
    @PostMapping("brands/list")
    public ResponseEntity<Page<BrandResponseDto>> getBrandBy(Pageable pageable, @RequestBody BrandCriteriaDto criteria) {
        return ResponseEntity.ok(brandService.findAll(pageable, criteria));
    }

    @ApiOperation(
            value = "Create new brand",
            notes = "This call is used to create a new nonactive brand",
            httpMethod = "POST")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Created brand returned"),
            @ApiResponse(code = 400, message = "Bad request"),
            @ApiResponse(code = 500, message = "Unknown error")
    })
    @PostMapping("brands/create")
    public ResponseEntity<BrandResponseDto> createBrand(@Valid @RequestBody BrandRequestDto createBrandDto){
        return ResponseEntity.ok(brandService.createBrand(createBrandDto));
    }
}
