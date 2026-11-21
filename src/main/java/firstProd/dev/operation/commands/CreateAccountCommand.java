package firstProd.dev.operation.commands;

import firstProd.dev.account.Account;
import firstProd.dev.account.AccountService;
import firstProd.dev.console.ConsoleInput;
import firstProd.dev.exception.AccountException;
import firstProd.dev.exception.ConsoleException;
import firstProd.dev.exception.UserException;
import firstProd.dev.operation.ConsoleOperationType;
import firstProd.dev.operation.OperationCommand;
import firstProd.dev.user.User;
import firstProd.dev.user.UserService;
import org.springframework.stereotype.Component;

@Component
public class CreateAccountCommand implements OperationCommand {
    private final ConsoleInput consoleInput;
    private final UserService userService;
    private final AccountService accountService;

    public CreateAccountCommand(
            ConsoleInput consoleInput,
            UserService userService,
            AccountService accountService
    ) {
        this.consoleInput = consoleInput;
        this.userService = userService;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
            System.out.println("Enter user id ");
            int userId = consoleInput.readInt();
                    try{
                        userService.checkUser(userId);
                        Account account = accountService.createNewAccount(userId);
                        userService.addAccountByUser(userId, account);
                        System.out.println(account);
                    } catch(UserException e) {
                        System.out.println(e.getMessage());
                    }
       }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CREATE;
    }
}
