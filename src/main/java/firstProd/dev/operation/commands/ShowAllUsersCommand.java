package firstProd.dev.operation.commands;

import firstProd.dev.operation.ConsoleOperationType;
import firstProd.dev.operation.OperationCommand;
import firstProd.dev.user.User;
import firstProd.dev.user.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowAllUsers implements OperationCommand {
    private UserService userService;

    public ShowAllUsers(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(){
       List<User> userList =  userService.getUserList();
       for( User user : userList){
           System.out.println(user);
       }

    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }

}
