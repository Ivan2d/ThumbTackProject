package net.thumbtack.school.auction.model;

import net.thumbtack.school.auction.exception.TokenOffOrON;
import net.thumbtack.school.auction.exception.UserErrorCode;
import net.thumbtack.school.auction.exception.UserException;

import java.util.Objects;
import java.util.UUID;

public class TokenBox {

        private UUID token;
        private String login;
        private TokenOffOrON state;

        public TokenBox (UUID token, String login) {
            setToken(token);
            setLogin(login);
            this.state = TokenOffOrON.ACTIVE;
        }

        public UUID getToken() {
            return token;
        }

        public void setToken(UUID token) {
            this.token = token;
        }

        public String getLogin() {
            return login;
        }

        public void setLogin(String login) {
            this.login = login;
        }

        public TokenOffOrON getState() {
            return state;
        }

        public void isInactiveState() throws UserException {
            if (state == TokenOffOrON.NOT_ACTIVE)
                throw new UserException(UserErrorCode.INACTIVE_TOKEN);
            this.state = TokenOffOrON.NOT_ACTIVE;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof TokenBox)) return false;
            TokenBox tokenBox = (TokenBox) o;
            return Objects.equals(token, tokenBox.token) &&
                    Objects.equals(login, tokenBox.login) &&
                    state == tokenBox.state;
        }

        @Override
        public int hashCode() {
            return Objects.hash(token, login, state);
        }
}
