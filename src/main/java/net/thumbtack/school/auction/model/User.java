package net.thumbtack.school.auction.model;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@Setter
@Getter
@NoArgsConstructor
public class User {
    private int id;
    private String firstname;
    private String lastname;
    private String login;
    private String password;
    private String uuid;

    public User(String firstname, String lastname, String login, String password){
        setFirstname(firstname);
        setLastname(lastname);
        setLogin(login);
        setPassword(password);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (getId() != user.getId()) return false;
        if (getFirstname() != null ? !getFirstname().equals(user.getFirstname()) : user.getFirstname() != null)
            return false;
        if (getLastname() != null ? !getLastname().equals(user.getLastname()) : user.getLastname() != null)
            return false;
        if (getLogin() != null ? !getLogin().equals(user.getLogin()) : user.getLogin() != null) return false;
        return getPassword() != null ? getPassword().equals(user.getPassword()) : user.getPassword() == null;
    }

    @Override
    public int hashCode() {
        int result = getFirstname() != null ? getFirstname().hashCode() : 0;
        result = 31 * result + (getLastname() != null ? getLastname().hashCode() : 0);
        result = 31 * result + (getLogin() != null ? getLogin().hashCode() : 0);
        result = 31 * result + (getPassword() != null ? getPassword().hashCode() : 0);
        result = 31 * result + getId();
        return result;
    }


}