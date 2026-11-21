package firstProd.dev.operation.commands;

import firstProd.dev.account.Account;
import firstProd.dev.account.AccountService;
import firstProd.dev.console.ConsoleInput;
import firstProd.dev.exception.AccountException;
import firstProd.dev.exception.UserException;
import firstProd.dev.operation.ConsoleOperationType;
import firstProd.dev.operation.OperationCommand;
import firstProd.dev.user.UserService;
import org.springframework.stereotype.Component;


@Component
public class AccountCloseCommand implements OperationCommand {
    private final ConsoleInput consoleInput;
    private final AccountService accountService;
    private final UserService userService;

    public AccountCloseCommand(
            ConsoleInput consoleInput,
            AccountService accountService,
            UserService userService
    ) {
        this.consoleInput = consoleInput;
        this.accountService = accountService;
        this.userService = userService;
    }

    @Override
    public void execute() {

        System.out.println("Enter account id:");
        int accountId = consoleInput.readInt();

        try {
            int userId = accountService.getIdUser(accountId);
            int countAccounts = userService.checkAccount(userId);
            if (countAccounts == 1) {
                System.out.println("Error: You cannot close your only account");
                return;
            }

            int idFirstAccount = userService.getIdFirstAccountByUserId(userId, 0);
            if (idFirstAccount == accountId) {
                idFirstAccount = userService.getIdFirstAccountByUserId(userId, 1);
            }

            int difBalance = accountService.getBalanceById(accountId);
            int balanceFrom = accountService.withdraw(accountId, difBalance );
            try {
                 int balanceNew  = accountService.deposit(idFirstAccount, difBalance );

            } catch (AccountException e) {
                int balanceBack  = accountService.deposit(accountId, difBalance );
                System.out.println(e.getMessage());
                return;
            }

            accountService.closeAccount(accountId);

            try {
                userService.removeAccount(userId, accountId);
            } catch (UserException  | AccountException e) {
                System.out.println(e.getMessage());
                Account oldAccount  = userService.getAccountByUserIdAccountId(userId, accountId);
                accountService.saveNewAccount( oldAccount);
                System.out.println("The funds transfer operation was completed. However, the account could not be closed.");
                return;
            }

            System.out.println("Account " + accountId + " closed. Remaining balance " + difBalance + " transferred to account " + idFirstAccount
            );


        } catch (AccountException | UserException e) {
            System.out.println(e.getMessage());

        }
    }


    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_CLOSE;
    }
}
