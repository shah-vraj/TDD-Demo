package repository.impl;

import data.entity.ExpenseEntity;
import data.model.Expense;
import repository.DatabaseRepository;
import repository.ViewRecentExpensesRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Concrete implementation class for ViewRecentExpensesRepository
 */
public class ViewRecentExpensesRepositoryImpl implements ViewRecentExpensesRepository {

    private final DatabaseRepository databaseRepository;

    public ViewRecentExpensesRepositoryImpl(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
        databaseRepository.openConnection();
    }

    @Override
    public List<Expense> getRecentExpenses(int limit, int offset) {
        if (limit <= 0 || offset < 0)
            return List.of();

        List<ExpenseEntity> allExpenses = databaseRepository.getAllExpenses()
                .stream()
                .sorted(Comparator.comparing(ExpenseEntity::date).reversed())
                .toList();

        return offset > allExpenses.size() - 1
                ? List.of()
                : new ArrayList<>(
                allExpenses.stream()
                        .map(ExpenseEntity::toExpense)
                        .toList()
                        .subList(offset, Math.min(offset + limit, allExpenses.size()))
        );
    }
}
