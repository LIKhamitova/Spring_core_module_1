package firstProd.dev.operation.commands;

import firstProd.dev.account.AccountService;
import firstProd.dev.console.ConsoleInput;
import firstProd.dev.exception.AccountException;
import firstProd.dev.operation.ConsoleOperationType;
import firstProd.dev.operation.OperationCommand;
import org.springframework.stereotype.Component;

@Component
public class AccountDepositCommand implements OperationCommand {
    private final ConsoleInput consoleInput;
    private final AccountService accountService;

    public AccountDepositCommand(
            ConsoleInput consoleInput,
            AccountService accountService) {
        this.consoleInput = consoleInput;
        this.accountService = accountService;
    }

    @Override
    public void execute() {
        System.out.println("Enter account id:");
        int id = consoleInput.readInt();
        System.out.println("Enter amount:");
        int amount = consoleInput.readInt();
            try{
                int newBalance =  accountService.deposit(id, amount);
                System.out.println("Deposited " +amount + " to account " + id +". New balance: "+ newBalance);
            }catch(AccountException e){
                System.out.println(e.getMessage());
            }
        }

    @Override
    public ConsoleOperationType getOperationType() {
        return ConsoleOperationType.ACCOUNT_DEPOSIT;
    }

}