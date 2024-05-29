package repository.impl;

import data.entity.ExpenseEntity;
import data.model.Expense;
import repository.DatabaseRepository;
import repository.ManageExpenseRepository;
import util.ExpenseHelper;

import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;

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
        if (isExpenseInvalid(expense))
            return false;
        return databaseRepository.addExpense(ExpenseHelper.getExpenseEntityFromExpense(expense, getNextExpenseId()));
    }

    @Override
    public boolean removeExpense(Expense expense) {
        if (isExpenseInvalid(expense))
            return false;
        int idToRemove = getIdForExpense(expense);
        return idToRemove != -1 && databaseRepository.removeExpense(idToRemove);
    }

    @Override
    public void updateExpense(Expense oldExpense, Expense newExpense) {
        return;
    }

    /**
     * Provides ID for the new expense to be added in the database
     *
     * @return Integer denoting the ID of the expense to be added to the database
     * Note: As variable used in lambda expression should be final or effectively final,
     * I have opted to use AtomicInteger rather than normal int variable.
     * @see <a href="https://www.baeldung.com/java-atomic-variables">Atomic Variables in JAVA</a>
     */
    private int getNextExpenseId() {
        AtomicInteger newExpenseId = new AtomicInteger(-1);
        databaseRepository.getAllExpenses()
                .stream()
                .max(Comparator.comparingInt(ExpenseEntity::getId))
                .ifPresent(expenseEntity -> newExpenseId.set(expenseEntity.getId() + 1));
        return newExpenseId.get() != -1 ? newExpenseId.get() : 1;
    }

    /**
     * Provides ID of the already added expense
     *
     * @param expense Expense to get ID for
     * @return Integer denoting ID of the expense provided
     */
    private int getIdForExpense(Expense expense) {
        return databaseRepository.getAllExpenses()
                .stream()
                .filter(entity -> ExpenseHelper.areTheSame(entity, expense))
                .findFirst()
                .map(ExpenseEntity::getId)
                .orElse(-1);
    }

    /**
     * Helper method to check if provided expense is invalid
     *
     * @param expense Expense instance to check invalidity for
     * @return True if provided expense is invalid
     */
    private boolean isExpenseInvalid(Expense expense) {
        if (expense == null)
            return true;
        if (expense.name() == null || expense.name().isBlank())
            return true;
        return expense.cost() < 0 || expense.date() == null;
    }
}