package repository.impl;

import data.entity.ExpenseEntity;
import repository.DatabaseRepository;

import java.time.LocalDate;
import java.time.Month;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Concrete implementation class for DatabaseRepository
 */
public class DatabaseRepositoryImpl implements DatabaseRepository {

    private static DatabaseRepositoryImpl instance;
    private final List<ExpenseEntity> allExpenses = new ArrayList<>();

    private DatabaseRepositoryImpl() {
        addDummyData();
    }

    /**
     * Singleton object approach to have single object throughout application
     *
     * @return DatabaseRepository instance
     */
    public static DatabaseRepository getInstance() {
        if (instance == null)
            instance = new DatabaseRepositoryImpl();
        return instance;
    }

    @Override
    public boolean openConnection() {
        // Assume open database connection successful
        return true;
    }

    @Override
    public boolean closeConnection() {
        // Assume close database connection successful
        return true;
    }

    @Override
    public boolean addExpense(ExpenseEntity expense) {
        return allExpenses.add(expense);
    }

    @Override
    public boolean removeExpense(int id) {
        return allExpenses.removeIf(expenseEntity -> expenseEntity.getId() == id);
    }

    @Override
    public void updateExpense(ExpenseEntity expense) {
        allExpenses.stream()
                .filter(expenseEntity -> expenseEntity.getId() == expense.getId())
                .findFirst()
                .ifPresent(expenseEntity -> {
                    expenseEntity.setName(expense.getName());
                    expenseEntity.setCost(expense.getCost());
                });
    }

    @Override
    public List<ExpenseEntity> getAllExpenses() {
        return allExpenses;
    }

    private void addDummyData() {
        List<LocalDate> dummyDates = List.of(
                LocalDate.of(2023, Month.MARCH, 3),
                LocalDate.of(2023, Month.MARCH, 4),
                LocalDate.of(2023, Month.MARCH, 5),
                LocalDate.of(2023, Month.DECEMBER, 13),
                LocalDate.of(2023, Month.FEBRUARY, 21),
                LocalDate.of(2023, Month.FEBRUARY, 22),
                LocalDate.of(2023, Month.FEBRUARY, 23),
                LocalDate.of(2023, Month.APRIL, 15),
                LocalDate.of(2023, Month.MAY, 7),
                LocalDate.of(2023, Month.MAY, 8),
                LocalDate.of(2023, Month.MAY, 9)
        );
        for (int id = 0; id < 40; id++) {
            boolean isIdEven = id % 2 == 0;
            allExpenses.add(new ExpenseEntity(
                    id + 1,
                    isIdEven ? "Milk" : "Bread",
                    isIdEven ? 7.23 : 2.86,
                    dummyDates.get(new Random().nextInt(dummyDates.size()))
            ));
        }
    }
}
