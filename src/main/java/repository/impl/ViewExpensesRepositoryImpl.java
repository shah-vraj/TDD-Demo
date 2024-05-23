package repository.impl;

import data.model.Expense;
import repository.ViewExpensesRepository;

import java.util.List;

/**
 * Concrete implementation class for ViewExpensesRepository
 */
public class ViewExpensesRepositoryImpl implements ViewExpensesRepository {
    @Override
    public List<Expense> getCurrentMonthExpenses() {
        return List.of();
    }

    @Override
    public List<Expense> getExpensesForMonth(String month) {
        return List.of();
    }

    @Override
    public List<Expense> getTotalExpenseCostForCurrentMonth() {
        return List.of();
    }

    @Override
    public List<Expense> getTotalExpenseCostForMonth(String month) {
        return List.of();
    }
}
