package repository;

import data.model.Expense;

import java.util.List;

/**
 * Repository for viewing recent expenses that deals with all operations related to ViewRecentExpenses screen.
 */
public interface ViewRecentExpensesRepository {
    /**
     * Provides recent expenses by defined limit and offset
     * @param limit Number of expenses to get in return
     * @param offset Offset of the sublist
     * @return List of limit(count) of Expenses starting from Offset
     */
    List<Expense> getRecentExpenses(int limit, int offset);
}
