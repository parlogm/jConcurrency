package ro.utm.jc.identity;

import ro.utm.jc.model.entities.Users;
import ro.utm.jc.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

    @Autowired
    private UserRepo userRepo;
    private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();

    @Override
    public final TokenUser loadUserByUsername(String username) throws UsernameNotFoundException, DisabledException {

        final Users user = userRepo.findOneByUserId(username).orElseThrow(() -> new UsernameNotFoundException("Users not found"));
        TokenUser currentUser;
        if (user.isActive() == true){
            currentUser = new TokenUser(user);
        }
        else{
            throw new DisabledException("Users is not activated (Disabled Users)");
            //If pending activation return a disabled user
            //currentUser = new TokenUser(user, false);
        }
        detailsChecker.check(currentUser);
        return currentUser;
    }
}
