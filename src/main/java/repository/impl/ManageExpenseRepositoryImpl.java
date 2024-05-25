package repository.impl;

import data.model.Expense;
import repository.DatabaseRepository;
import repository.ManageExpenseRepository;

/**
 * Concrete implementation class for ManageExpenseRepository
 */
public class ManageExpenseRepositoryImpl implements ManageExpenseRepository {

    private final DatabaseRepository databaseRepository;

    public ManageExpenseRepositoryImpl(DatabaseRepository databaseRepository) {
        this.databaseRepository = databaseRepository;
        databaseRepository.openConnection();
    }

    @Override
    public boolean addExpense(Expense expense) {
        return false;
    }

    @Override
    public boolean removeExpense(Expense expense) {
        return false;
    }
}
