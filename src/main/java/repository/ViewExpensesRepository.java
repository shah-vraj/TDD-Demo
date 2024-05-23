package repository;

import data.model.Expense;

import java.util.List;

/**
 * Repository for viewing expense that deals with all operations related to ViewExpenses screen
 */
public interface ViewExpensesRepository {
    List<Expense> getCurrentMonthExpenses();

    List<Expense> getExpensesForMonth(String month);

    List<Expense> getTotalExpenseCostForCurrentMonth();

    List<Expense> getTotalExpenseCostForMonth(String month);
}
