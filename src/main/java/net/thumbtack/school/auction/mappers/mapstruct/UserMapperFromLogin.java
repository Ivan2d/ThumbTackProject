package net.thumbtack.school.auction.mappers.mapstruct;
import net.thumbtack.school.auction.dto.request.RegisterBuyerDtoRequest;
import net.thumbtack.school.auction.dto.response.GetUserByTokenDtoResponse;
import net.thumbtack.school.auction.dto.response.UserDtoResponse;
import net.thumbtack.school.auction.model.User;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapperFromLogin {

    UserMapperFromLogin MAPPER = Mappers.getMapper(UserMapperFromLogin.class);

    User toUser(RegisterBuyerDtoRequest registerBuyerDtoRequest);
    User toUser(UserDtoResponse userDtoResponse);

    @InheritInverseConfiguration
    GetUserByTokenDtoResponse fromUser(User user);

}