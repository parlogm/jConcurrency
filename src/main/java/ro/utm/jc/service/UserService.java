package ro.utm.jc.service;

import ro.utm.jc.model.entities.Users;
import ro.utm.jc.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public String getLoggedInUserId(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth==null){
            return "nosession";
        }
        return auth.getName();
    }


    public Users getLoggedInUser() {
        String loggedInUserId = this.getLoggedInUserId();
        Users user = this.getUserInfoByUserId(loggedInUserId);
        return user;
    }

    public Users getUserInfoByUserId(String userId){
        Users user = this.userRepo.findOneByUserId(userId).orElseGet( () -> new Users());
        return user;
    }

    public boolean insertOrSaveUser(Users user) {
        this.userRepo.save(user);
        return true;
    }

    public boolean addNewUser(Users user) {
        Users newUser = this.getUserInfoByUserId(user.getUserId());
        if (newUser.getUserId().equals("new")){
            // This means the username is not found therfore its is returning a default value of "new"
            return this.insertOrSaveUser(user);
        }
        else{
            return false;
        }
    }

}

