package firstProd.dev.account;

import firstProd.dev.exception.AccountException;
import org.springframework.stereotype.Component;
import java.util.HashMap;
import java.util.Map;

@Component
public class AccountService
{
    private int idCounter = 1;
    private final AccountProperties accountProperties ;
    private final Map<Integer, Account >accounts = new HashMap<>();

    public AccountService(AccountProperties accountProperties) {
        this.accountProperties = accountProperties;
    }

    public Account createNewAccount( int userId ) {
        Account account = new Account(idCounter, userId, accountProperties.getDefaultAmount());
        idCounter++;
        return account;
        }

    public void saveNewAccount( Account account ) {
        if (account == null) {
            throw new AccountException("No account to save");
        }
        if ( !accounts.isEmpty()){
            if (accounts.containsKey(account.getId())){
                throw new AccountException("Account already exists");
            }
        }

        accounts.put(account.getId(), account);
    }

    public int deposit( int id, int amount ) {

        if (amount <= 0) {
            throw new AccountException("Error:Amount must be positive");
        }

        Account account = accounts.get(id);
        if (account == null) {
            throw new AccountException("Error: Account does not exist");
        }

        account.addMoney(amount);
        return account.balance();

    }

    public int withdraw( int id, int amount ) {

        if (amount <= 0) {
            throw new AccountException("Error:Amount must be positive");
        }

        Account account = accounts.get(id);
        if (account == null) {
            throw new AccountException("Error: Account does not exist");
        }

        account.subMoney(amount);
        return account.balance();
    }


    public void closeAccount( int id ) {
        Account account = accounts.get(id);
        if ( account != null ) {
            accounts.remove(id);
        }else {
            throw new AccountException("Error: Account does not exist");
        }
    }

    public int getBalanceById( int id ) {
        Account account = accounts.get(id);
        return account.balance();
    }

   public int getIdUser(int id) {
        Account account = accounts.get(id);
        if (account != null) {
            return account.userId();
        }else {
            throw new AccountException("Error: Account does not exist");
        }
   }


   public void transferMoney( int fromId, int toId, int amount ) {
       int commission = 0;
       int sumReceived = 0;

       if (amount <= 0) {
           throw new AccountException("Error: Amount must be positive");
       }

       if(fromId == toId) {
           throw new AccountException("Error: From and To Ids cannot be the same");
       }

            Account fromAccount = accounts.get(fromId);
            if (fromAccount == null) {
                throw new AccountException("Error: Account does not exist");
            }

            Account toAccount = accounts.get(toId);
            if (toAccount == null) {
                throw new AccountException("Error: Account does not exist");
            }


            int fromBalance = fromAccount.balance();
            if (fromBalance  < amount) {
                throw new AccountException("Error: Not enough money");
            }

            int fromUserId = fromAccount.userId();
            int toUserId = toAccount.userId();

            if (fromUserId == toUserId) {
                sumReceived = amount;
            } else {

                sumReceived = (int) Math.round(amount * (1 -
                        accountProperties.getTransferCommission()));

                commission =  amount - sumReceived;

            }

            if (sumReceived <= 0){
                throw new AccountException("Error: Amount too small after commission");
            }

             withdraw( fromId, amount);
            try {
                deposit(toId, sumReceived);
            }
            catch (AccountException e)
            { deposit(fromId, amount);
                throw new AccountException("Error: Unable to credit funds");
            }

            System.out.println( "Transfer completed from account " +fromId + " to account " + toId +". Amount: " + amount  + ", commission: "+ commission + " recipient received : " +  sumReceived );
   }
}




