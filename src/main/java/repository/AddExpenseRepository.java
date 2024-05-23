package repository;

import data.model.Expense;

/**
 * Repository for adding expense that deals with all operations related to AddExpense screen.
 */
public interface AddExpenseRepository {
    boolean addExpense(Expense expense);
}
