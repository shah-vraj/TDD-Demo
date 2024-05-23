package repository;

import data.entity.ExpenseEntity;
import data.model.Expense;
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

    boolean addExpense(Expense expense);

    boolean removeExpense(Expense expense);

    static DatabaseRepository getInstance() {
        return DatabaseRepositoryImpl.getInstance();
    }
}
