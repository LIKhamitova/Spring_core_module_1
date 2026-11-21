package firstProd.dev.operation;

public interface OperationCommand {
    void execute();
    ConsoleOperationType getOperationType();
}
