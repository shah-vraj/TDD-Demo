package repository.impl;

import data.model.Expense;
import repository.AddExpenseRepository;
import repository.DatabaseRepository;

/**
 * Concrete implementation class for AddExpenseRepository
 */
public class AddExpenseRepositoryImpl implements AddExpenseRepository {

    private final DatabaseRepository databaseRepository;

    public AddExpenseRepositoryImpl(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
        databaseRepository.openConnection();
    }

    @Override
    public boolean addExpense(Expense expense) {
        return false;
    }
}
