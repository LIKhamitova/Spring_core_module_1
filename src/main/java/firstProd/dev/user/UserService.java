package firstProd.dev.user;

import firstProd.dev.account.Account;
import firstProd.dev.account.AccountService;
import firstProd.dev.exception.AccountException;
import firstProd.dev.exception.UserException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class UserService {
    private final Map<Integer, User> userList = new HashMap<Integer, User>();
    private final AccountService accountService;
    private int idCounter = 1;

    public UserService(AccountService accountService) {
        this.accountService = accountService;
    }

    public void createUser(String login) {

        if (login.isEmpty()){
            throw new UserException("Error: Login cannot be empty");
        }

        for (User user : userList.values()) {
            if (login.equals(user.getLogin()))
                throw new UserException("Error: Login already exists");
        }

            User user =  new User(login, idCounter, new ArrayList<>());
            userList.put(user.getId(), user);
            idCounter++;
            Account newAccount = accountService.createNewAccount(user.getId());
            addAccountByUser(user.getId(), newAccount);
            System.out.println(user);
    }


   public List<User> getUserList() {
        return List.copyOf(userList.values());
   }

    public void checkUser(int userId) {
       User user =  userList.get(userId);
       if (user == null){
           throw new UserException("Error: User not found");
       }
    }

  public void addAccountByUser(int userId, Account account){
        User user =  userList.get(userId);
        if (user == null) {
            throw new UserException("Error: User not found");
        }
            user.addAccountUser(account);
            try{
                accountService.saveNewAccount(account);
            } catch( AccountException e) {
                user.removeAccountFromUser(account.getId());
                throw new UserException(e.getMessage());
            }
        }


     public int getIdFirstAccountByUserId(int userId, int index ) {
       User user =  userList.get(userId);
       if (user == null){
           throw new UserException("Error: User not found");
       }
       return user.getIdAccountByIndex(index);
     }


  public void removeAccount(int userId, int accountId ){
        User user =  userList.get(userId);
        if(user != null) {
           user.removeAccountFromUser(accountId);
        } else {
            throw new UserException("Error: User not found");
        }
  }

    public  Account getAccountByUserIdAccountId  (int userId, int accountId) {
        User user = userList.get(userId);
        if (user == null) {
            throw new UserException("Error: User not found");
        }

        List<Account> accounts = user.getAccounts();
        for (Account account : accounts) {
            if (account.getId() == accountId) {
                return account;
            }
        }
     return null;
    }

    public int checkAccount(int userId){
        User user =  userList.get(userId);
        if (user == null) {
            throw new UserException("Error: User not found");
        }
        return user.countAccounts();

    }

}
