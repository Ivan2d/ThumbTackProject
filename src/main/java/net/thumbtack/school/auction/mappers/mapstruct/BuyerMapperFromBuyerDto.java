package net.thumbtack.school.auction.mappers.mapstruct;

import net.thumbtack.school.auction.dto.response.GetUserByTokenDtoResponse;
import net.thumbtack.school.auction.model.Buyer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BuyerMapperFromBuyerDto {
    BuyerMapperFromBuyerDto MAPPER = Mappers.getMapper(BuyerMapperFromBuyerDto.class);

    Buyer toBuyer(GetUserByTokenDtoResponse registerDtoRequest);

    @InheritInverseConfiguration
    GetUserByTokenDtoResponse fromBuyer(Buyer buyer);
}
