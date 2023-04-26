package net.thumbtack.school.auction.mappers.mapstruct;
import net.thumbtack.school.auction.dto.request.AddLotDtoRequest;
import net.thumbtack.school.auction.model.Lot;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface LotMapperFromDto {

    LotMapperFromDto MAPPER = Mappers.getMapper(LotMapperFromDto.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "seller", ignore = true)
    @Mapping(target = "obligatoryValueForSell", ignore = true)
    @Mapping(target = "categories", ignore = true)
    Lot toLot(AddLotDtoRequest dtoRequest);

    @InheritInverseConfiguration
    AddLotDtoRequest fromLot(Lot lot);
}