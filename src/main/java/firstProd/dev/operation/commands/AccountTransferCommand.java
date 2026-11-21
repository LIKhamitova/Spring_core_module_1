package firstProd.dev.operation.commands;

import firstProd.dev.account.AccountService;
import firstProd.dev.console.ConsoleInput;
import firstProd.dev.exception.AccountException;
import firstProd.dev.operation.ConsoleOperationType;
import firstProd.dev.operation.OperationCommand;
import org.springframework.stereotype.Component;

@Component
public class AccountTransferCommand implements OperationCommand {
    private final AccountService accountService;
    private final ConsoleInput consoleInput;

    public AccountTransferCommand(
            AccountService accountService,
            ConsoleInput consoleInput)
    {
    this.accountService = accountService;
    this.consoleInput = consoleInput;
    }

    @Override
    public void execute() {
        System.out.println("Enter source account id:");
        int sourceAccountId = consoleInput.readInt();
        System.out.println("Enter target account id:");
        int targetAccountId = consoleInput.readInt();
        System.out.println("Enter amount:");
        int amount = consoleInput.readInt();
        try{
            accountService.transferMoney(sourceAccountId,  targetAccountId, amount);
        }catch(AccountException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_TRANSFER;
    }

}
