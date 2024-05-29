package repository;

import data.model.Expense;

/**
 * Repository for managing expense that deals with all operations related to ManageExpense screen.
 */
public interface ManageExpenseRepository {
    /**
     * Adds an expense into the database
     * @param expense Expense to add
     * @return True if expense added, Otherwise false
     */
    boolean addExpense(Expense expense);

    /**
     * Removes expense from the database
     * @param expense Expense to remove
     * @return True if expense removed, Otherwise false
     */
    boolean removeExpense(Expense expense);
}
