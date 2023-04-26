package net.thumbtack.school.auction.mappers.mapstruct;

import net.thumbtack.school.auction.dto.request.LoginDtoRequest;
import net.thumbtack.school.auction.model.Seller;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SellerMapperFromLogin {
    SellerMapperFromLogin MAPPER = Mappers.getMapper(SellerMapperFromLogin.class);


    Seller toSeller (LoginDtoRequest dtoRequest);

    @InheritInverseConfiguration
    LoginDtoRequest fromSeller(Seller seller);
}
