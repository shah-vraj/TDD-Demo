package repository;

import data.entity.ExpenseEntity;
import repository.impl.DatabaseRepositoryImpl;

import java.util.List;

/**
 * Repository that handles all database related operations
 */
public interface DatabaseRepository {
    /**
     * Connects the database (dummy for this application)
     * @return True is connection successful, Otherwise false (always true for this application)
     */
    boolean openConnection();

    /**
     * Closes connection to the database (dummy for this application)
     * @return True is connection disconnected, Otherwise false (always true for this application)
     */
    boolean closeConnection();

    /**
     * Add an expense to the database
     * @param expense Expense instance to add to the database
     * @return True if expense added to the database, Otherwise false
     */
    boolean addExpense(ExpenseEntity expense);

    /**
     * Remove an expense from the database
     * @param id ID of the expense to remove from the database
     * @return True if expense removed, Otherwise false
     */
    boolean removeExpense(int id);

    /**
     * Get all the expenses added to the database
     * @return List of all expenses added to the database
     */
    List<ExpenseEntity> getAllExpenses();

    /**
     * Provide singleton object of the DatabaseRepository
     * @return DatabaseRepository instance
     */
    static DatabaseRepository getInstance() {
        return DatabaseRepositoryImpl.getInstance();
    }
}
