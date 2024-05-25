package repository;

import data.entity.ExpenseEntity;
import repository.impl.DatabaseRepositoryImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * Repository that handles all database related operations
 */
public interface DatabaseRepository {
    List<ExpenseEntity> allExpenses = new ArrayList<>();

    boolean openConnection();

    boolean closeConnection();

    boolean addExpense(ExpenseEntity expense);

    boolean removeExpense(int id);

    List<ExpenseEntity> getAllExpenses();

    static DatabaseRepository getInstance() {
        return DatabaseRepositoryImpl.getInstance();
    }
}
