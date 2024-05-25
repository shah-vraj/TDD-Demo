package repository;

import data.model.Expense;

/**
 * Repository for managing expense that deals with all operations related to ManageExpense screen.
 */
public interface ManageExpenseRepository {
    boolean addExpense(Expense expense);

    boolean removeExpense(Expense expense);
}
