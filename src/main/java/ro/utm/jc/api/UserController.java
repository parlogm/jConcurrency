package ro.utm.jc.api;

import com.google.common.base.Strings;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ro.utm.jc.model.responses.OperationResponse;
import ro.utm.jc.model.entities.Users;
import ro.utm.jc.model.responses.UserResponse;
import ro.utm.jc.service.UserService;

import javax.servlet.http.HttpServletRequest;

@RestController
@Api(tags = {"Authentication"})
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation(value = "Gets current user information", response = UserResponse.class)
    @RequestMapping(value = "/user", method = RequestMethod.GET, produces = {"application/json"})
    public UserResponse getUserInformation(@RequestParam(value = "name", required = false) String userIdParam, HttpServletRequest req) {

        String loggedInUserId = userService.getLoggedInUserId();

        Users user;
        boolean provideUserDetails = false;

        if (Strings.isNullOrEmpty(userIdParam)) {
            provideUserDetails = true;
            user = userService.getLoggedInUser();
        }
        else if (loggedInUserId.equals(userIdParam)) {
            provideUserDetails = true;
            user = userService.getLoggedInUser();
        }
        else {
            //Check if the current user is superuser then provide the details of requested user
            provideUserDetails = true;
            user = userService.getUserInfoByUserId(userIdParam);
        }

        UserResponse resp = new UserResponse();
        if (provideUserDetails) {
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
        }
        else {
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.NO_ACCESS);
            resp.setOperationMessage("No Access");
        }
        resp.setData(user);
        return resp;
    }


    @ApiOperation(value = "Add new user", response = OperationResponse.class)
    @RequestMapping(value = "/user", method = RequestMethod.POST, produces = {"application/json"})
    public OperationResponse addNewUser(@RequestBody Users user, HttpServletRequest req) {
        boolean userAddSuccess = userService.addNewUser(user);
        OperationResponse resp = new OperationResponse();
        if (userAddSuccess==true){
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.SUCCESS);
            resp.setOperationMessage("Users Added");
        }
        else{
            resp.setOperationStatus(OperationResponse.ResponseStatusEnum.ERROR);
            resp.setOperationMessage("Unable to add user");
        }
        return resp;
    }


}
