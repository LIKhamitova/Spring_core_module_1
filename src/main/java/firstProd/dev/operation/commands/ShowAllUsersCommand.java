package firstProd.dev.operation.commands;

import firstProd.dev.operation.ConsoleOperationType;
import firstProd.dev.operation.OperationCommand;
import firstProd.dev.user.User;
import firstProd.dev.user.UserService;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ShowAllUsersCommand implements OperationCommand {
    private final UserService userService;

    public ShowAllUsersCommand(UserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute() {
            List<User> userList = userService.getUserList();

        if (userList.isEmpty()) {
            System.out.println("No users found");
        }  else {
            for (User user : userList) {
                System.out.println(user);
            }
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.SHOW_ALL_USERS;
    }

}
