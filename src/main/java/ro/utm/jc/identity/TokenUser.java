package ro.utm.jc.identity;

import ro.utm.jc.model.entities.Users;
import org.springframework.security.core.authority.AuthorityUtils;

public class TokenUser extends org.springframework.security.core.userdetails.User {
    private Users user;

    //For returning a normal user
    public TokenUser(Users user) {
        super( user.getUserId(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().toString()  )  );
        //super(user.getUserName(), user.getPassword(), true, true, true, true,  AuthorityUtils.createAuthorityList(user.getRole().toString()));
        this.user = user;
    }

    public Users getUser() {
        return user;
    }

    public String getRole() {
        return user.getRole().toString();
    }
}
