package net.thumbtack.school.auction.mapper;
import net.thumbtack.school.auction.dto.request.RegisterDtoRequest;
import net.thumbtack.school.auction.model.Buyer;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BuyerMapperFromRegister {

    BuyerMapperFromRegister MAPPER = Mappers.getMapper(BuyerMapperFromRegister.class);

    Buyer toBuyer(RegisterDtoRequest registerDtoRequest);

    @InheritInverseConfiguration
    RegisterDtoRequest fromBuyer(Buyer buyer);
}