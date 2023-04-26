package net.thumbtack.school.auction.mappers.mapstruct;
import net.thumbtack.school.auction.dto.request.RegisterBuyerDtoRequest;
import net.thumbtack.school.auction.model.Buyer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BuyerMapperFromRegister {

    BuyerMapperFromRegister MAPPER = Mappers.getMapper(BuyerMapperFromRegister.class);

    Buyer toBuyer(RegisterBuyerDtoRequest registerBuyerDtoRequest);

    @InheritInverseConfiguration
    RegisterBuyerDtoRequest fromBuyer(Buyer buyer);
}