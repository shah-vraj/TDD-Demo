package repository;

import model.Expense;

import java.time.Month;
import java.util.List;

/**
 * Repository for viewing expense that deals with all operations related to ViewExpenses screen
 */
public interface ViewExpensesRepository {
    /**
     * Get all expenses for current month
     * @return List of Expenses for current month
     */
    List<Expense> getCurrentMonthExpenses();

    /**
     * Get all expenses for requested month and year
     * @param month Month to get all expenses for
     * @param year Year to get all expenses for
     * @return List of Expenses for requested month and year
     */
    List<Expense> getExpensesForMonth(Month month, int year);

    /**
     * Get all expenses cost for current month
     * @return Double representing cost of all Expenses for current month
     */
    double getTotalExpenseCostForCurrentMonth();

    /**
     * Get all expenses cost for requested month and year
     * @param month Month to get cost for
     * @param year Year to get cost for
     * @return Double representing cost of all Expenses for requested month and year
     */
    double getTotalExpenseCostForMonth(Month month, int year);
}
