package net.thumbtack.school.auction.mappers.mapstruct;

import net.thumbtack.school.auction.dto.request.LoginDtoRequest;
import net.thumbtack.school.auction.model.Buyer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BuyerMapperFromLogin {
    BuyerMapperFromLogin MAPPER = Mappers.getMapper(BuyerMapperFromLogin.class);
    Buyer toBuyer(LoginDtoRequest registerDtoRequest);
    @InheritInverseConfiguration
    LoginDtoRequest fromBuyer(Buyer buyer);
}
