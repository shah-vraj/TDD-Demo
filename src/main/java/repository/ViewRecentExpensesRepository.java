package repository;

import data.model.Expense;

import java.util.List;

/**
 * Repository for viewing recent expenses that deals with all operations related to ViewRecentExpenses screen.
 */
public interface ViewRecentExpensesRepository {
    List<Expense> getRecentExpenses(int limit, int offset);
}
