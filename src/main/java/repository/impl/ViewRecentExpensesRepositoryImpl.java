package repository.impl;

import data.model.Expense;
import repository.ViewRecentExpensesRepository;

import java.util.List;

/**
 * Concrete implementation class for ViewRecentExpensesRepository
 */
public class ViewRecentExpensesRepositoryImpl implements ViewRecentExpensesRepository {
    @Override
    public List<Expense> getRecentExpenses(int limit, int offset) {
        return List.of();
    }
}
