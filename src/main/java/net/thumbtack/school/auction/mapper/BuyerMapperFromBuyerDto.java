package net.thumbtack.school.auction.mapper;

import net.thumbtack.school.auction.dto.response.GetBuyerByTokenDtoResponse;
import net.thumbtack.school.auction.model.Buyer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BuyerMapperFromBuyerDto {
    BuyerMapperFromBuyerDto MAPPER = Mappers.getMapper(BuyerMapperFromBuyerDto.class);

    Buyer toBuyer(GetBuyerByTokenDtoResponse registerDtoRequest);

    @InheritInverseConfiguration
    GetBuyerByTokenDtoResponse fromBuyer(Buyer buyer);
}
