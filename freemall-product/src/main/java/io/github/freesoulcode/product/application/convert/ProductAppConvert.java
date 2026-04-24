package io.github.freesoulcode.product.application.convert;

import io.github.freesoulcode.product.application.request.CreateProductRequest;
import io.github.freesoulcode.product.application.request.GetProductResponse;
import io.github.freesoulcode.product.domain.model.Product;
import io.github.freesoulcode.product.domain.model.ProductStatus;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductAppConvert {
    GetProductResponse toGetProductResponse(Product product);
   default Product toDomain(CreateProductRequest request){
       return Product.builder()
               .name(request.getName())
               .subTitle(request.getSubTitle())
               .description(request.getDescription())
               .status(ProductStatus.DRAFT)
               .build();
   }
}
