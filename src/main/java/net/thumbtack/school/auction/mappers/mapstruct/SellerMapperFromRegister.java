package net.thumbtack.school.auction.mappers.mapstruct;
import net.thumbtack.school.auction.dto.request.RegisterBuyerDtoRequest;
import net.thumbtack.school.auction.dto.request.RegisterSellerDtoRequest;
import net.thumbtack.school.auction.model.Seller;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SellerMapperFromRegister {

    SellerMapperFromRegister MAPPER = Mappers.getMapper(SellerMapperFromRegister.class);


    Seller toSeller (RegisterSellerDtoRequest dtoRequest);

    @InheritInverseConfiguration
    RegisterBuyerDtoRequest fromSeller(Seller seller);
}