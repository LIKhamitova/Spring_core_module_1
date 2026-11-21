package firstProd.dev.operation.commands;

import firstProd.dev.console.ConsoleInput;
import firstProd.dev.exception.UserException;
import firstProd.dev.operation.ConsoleOperationType;
import firstProd.dev.operation.OperationCommand;
import firstProd.dev.user.User;
import firstProd.dev.user.UserService;
import org.springframework.stereotype.Component;

@Component
public class CreateUserCommand implements OperationCommand {
    private final UserService userService;
    private final ConsoleInput consoleInput;

    public CreateUserCommand(
            UserService userService,
            ConsoleInput consoleInput ) {
        this.userService = userService;
        this.consoleInput = consoleInput;
    }


    @Override
    public void execute(){
        System.out.println("Enter login: ");
        String login = consoleInput.readText();

            try{
                 userService.createUser(login);
            } catch ( UserException e){
                System.out.println(e.getMessage());
            }

        }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.USER_CREATE;
    }

}
