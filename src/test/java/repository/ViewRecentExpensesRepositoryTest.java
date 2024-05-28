package repository;

import data.entity.ExpenseEntity;
import data.model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.impl.ViewRecentExpensesRepositoryImpl;
import util.ExpenseHelper;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ViewRecentExpensesRepositoryTest {

    @Mock
    private DatabaseRepository databaseRepository;

    private ViewRecentExpensesRepository repository;

    private final Expense dummyExpense1 = new Expense("Item1", 5.23, LocalDate.of(2024, Month.FEBRUARY, 7));
    private final Expense dummyExpense2 = new Expense("Item2", 4.23, LocalDate.of(2024, Month.MARCH, 10));
    private final Expense dummyExpense3 = new Expense("Item3", 3.23, LocalDate.of(2024, Month.APRIL, 15));
    private final Expense dummyExpense4 = new Expense("Item4", 2.23, LocalDate.now());
    private final Expense dummyExpense5 = new Expense("Item5", 1.23, LocalDate.now());

    private final ExpenseEntity dummyExpenseEntity1 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense1, 1);
    private final ExpenseEntity dummyExpenseEntity2 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense2, 2);
    private final ExpenseEntity dummyExpenseEntity3 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense3, 3);
    private final ExpenseEntity dummyExpenseEntity4 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense4, 4);
    private final ExpenseEntity dummyExpenseEntity5 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense5, 5);

    @BeforeEach
    void setup() {
        repository = new ViewRecentExpensesRepositoryImpl(databaseRepository);
    }

    @Test
    public void shouldReturnEmptyListWhenInvalidInputProvided() {
        // Given
        int invalidLimit = -10;
        int invalidOffset = -2;

        // When
        List<Expense> result1 = repository.getRecentExpenses(invalidLimit, 2);
        List<Expense> result2 = repository.getRecentExpenses(10, invalidOffset);
        List<Expense> result3 = repository.getRecentExpenses(invalidLimit, invalidOffset);

        // Then
        assertTrue(result1.isEmpty());
        assertTrue(result2.isEmpty());
        assertTrue(result3.isEmpty());
    }

    @Test
    public void shouldReturnProperExpensesWhenLimitAndOffsetAreInRangeOfAllExpenses() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(
                dummyExpenseEntity1, dummyExpenseEntity2, dummyExpenseEntity3, dummyExpenseEntity4, dummyExpenseEntity5
        );
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);

        // When
        List<Expense> result = repository.getRecentExpenses(2, 0);

        // Then
        assertEquals(result.size(), 2);
        assertTrue(result.stream().anyMatch(expense -> Objects.equals(expense, dummyExpense4)));
        assertTrue(result.stream().anyMatch(expense -> Objects.equals(expense, dummyExpense5)));
    }

    @Test
    public void shouldReturnProperExpensesWhenLimitAndOffsetAreOutOfRangeOfAllExpenses() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(dummyExpenseEntity1, dummyExpenseEntity2, dummyExpenseEntity3);
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);

        // When
        List<Expense> result1 = repository.getRecentExpenses(5, 0);
        List<Expense> result2 = repository.getRecentExpenses(5, 4);

        // Then
        assertEquals(result1.size(), 3);
        assertTrue(result1.stream().anyMatch(expense -> Objects.equals(expense, dummyExpense1)));
        assertTrue(result1.stream().anyMatch(expense -> Objects.equals(expense, dummyExpense2)));
        assertTrue(result1.stream().anyMatch(expense -> Objects.equals(expense, dummyExpense3)));

        assertTrue(result2.isEmpty());
    }
}