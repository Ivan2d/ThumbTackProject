package net.thumbtack.school.auction.mapper;
import net.thumbtack.school.auction.dto.request.RegisterDtoRequest;
import net.thumbtack.school.auction.model.Seller;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SellerMapperFromRegister {

    SellerMapperFromRegister MAPPER = Mappers.getMapper(SellerMapperFromRegister.class);


    Seller toSeller (RegisterDtoRequest dtoRequest);

    @InheritInverseConfiguration
    RegisterDtoRequest fromSeller(Seller seller);
}