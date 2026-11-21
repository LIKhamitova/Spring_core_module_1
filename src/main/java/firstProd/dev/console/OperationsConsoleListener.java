package firstProd.dev.console;

import firstProd.dev.exception.ConsoleException;
import firstProd.dev.operation.ConsoleOperationType;
import firstProd.dev.operation.OperationCommand;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class OperationsConsoleListener {
    private final Map<ConsoleOperationType, OperationCommand> commandMap = new HashMap<>();
    private final ConsoleInput consoleInput;
    private final List<OperationCommand> commands;

    @PostConstruct
    public void init( ){
        commands.forEach(command ->
                commandMap.put(command.getOperationType(), command)
        );

       int count =  commandMap.size();
        if (count == 0){
            throw new IllegalStateException("No commands found");
        }

        for (ConsoleOperationType type : ConsoleOperationType.values()) {
            if (type == ConsoleOperationType.EXIT) continue;
            if (!commandMap.containsKey(type)) {
                throw new IllegalStateException("No command for " + type);
            }
        }
    }

    public OperationsConsoleListener(
            ConsoleInput consoleInput,
            List<OperationCommand> commands  )

    {
        this.consoleInput = consoleInput;
        this.commands = commands;

    }

    public void start(){
        String console;
        ConsoleOperationType currentCommand ;

        printRegards();
        while ( true) {
            System.out.println("Enter command:");
                try{
                    console =   consoleInput.readText();
                }catch (ConsoleException e){        //EOF
                    System.out.println(e.getMessage());
                    break;
                }

                try {
                     currentCommand  =  ConsoleOperationType.valueOf(console);

                }catch (IllegalArgumentException e){
                    System.out.println("Bad command.Try again");
                    continue;
                }

                if ( currentCommand  == ConsoleOperationType.EXIT ){
                    break;
                }

                OperationCommand operationCommand = commandMap.get(currentCommand);

                try{
                    operationCommand.execute();
                }catch (ConsoleException e ){
                    System.out.println(e.getMessage());
                }

        }
        System.out.println("MiniBank stopped.");
    }


    private void printRegards(){
        System.out.println("MiniBank started. Type EXIT to stop.");

        List<String> commandsList = new ArrayList<>();
        for (ConsoleOperationType command : ConsoleOperationType.values()) {
            commandsList.add(command.name());
        }
        String result = String.join(", ", commandsList);
        System.out.println( result );

    }
}





