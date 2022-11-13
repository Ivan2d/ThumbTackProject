package net.thumbtack.school.auction.mapper;
import net.thumbtack.school.auction.dto.request.AddLotDtoRequest;
import net.thumbtack.school.auction.dto.request.InfoAboutLotRequest;
import net.thumbtack.school.auction.model.Lot;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.factory.Mappers;

public interface LotMapperFromDto {

    LotMapperFromDto MAPPER = Mappers.getMapper(LotMapperFromDto.class);

    Lot toLot(AddLotDtoRequest dtoRequest);
    Lot toLot(InfoAboutLotRequest dtoRequest);

    @InheritInverseConfiguration
    AddLotDtoRequest fromLot(Lot lot);
}
