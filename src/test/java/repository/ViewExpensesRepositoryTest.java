package repository;

import data.entity.ExpenseEntity;
import model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.impl.ViewExpensesRepositoryImpl;
import util.ExpenseHelper;

import java.time.LocalDate;
import java.time.Month;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ViewExpensesRepositoryTest {

    @Mock
    private DatabaseRepository databaseRepository;

    private ViewExpensesRepository repository;

    private final Expense dummyExpense1 = new Expense("Item1", 1.23, LocalDate.now());
    private final Expense dummyExpense2 = new Expense("Item2", 2.23, LocalDate.now());
    private final Expense dummyExpense3 = new Expense("Item3", 3.23, LocalDate.of(2022, Month.DECEMBER, 15));
    private final Expense dummyExpense4 = new Expense("Item4", 4.23, LocalDate.now());
    private final Expense dummyExpense5 = new Expense("Item5", 5.23, LocalDate.of(2023, Month.MAY, 23));

    private final ExpenseEntity dummyExpenseEntity1 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense1, 1);
    private final ExpenseEntity dummyExpenseEntity2 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense2, 2);
    private final ExpenseEntity dummyExpenseEntity3 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense3, 3);
    private final ExpenseEntity dummyExpenseEntity4 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense4, 4);
    private final ExpenseEntity dummyExpenseEntity5 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense5, 5);

    @BeforeEach
    void setup() {
        repository = new ViewExpensesRepositoryImpl(databaseRepository);
    }

    // Region: Current month expenses
    @Test
    public void shouldReturnEmptyList_When_NoExpensesAdded_And_GetCurrentMonthExpensesIsCalled() {
        // Given
        List<ExpenseEntity> allExpenses = Collections.emptyList();
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);

        // When
        List<Expense> result = repository.getCurrentMonthExpenses();

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnEmptyList_When_NoCurrentMonthExpenseAdded_And_GetCurrentMonthExpensesIsCalled() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(dummyExpenseEntity3, dummyExpenseEntity5);
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);

        // When
        List<Expense> result = repository.getCurrentMonthExpenses();

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnAllExpenses_When_AllExpensesAreOfCurrentMonth_And_GetCurrentMonthExpensesIsCalled() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(dummyExpenseEntity1, dummyExpenseEntity2, dummyExpenseEntity4);
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);

        // When
        List<Expense> result = repository.getCurrentMonthExpenses();

        // Then
        assertEquals(result.size(), 3);
        assertTrue(result.stream().anyMatch(expense -> Objects.equals(expense, dummyExpense1)));
        assertTrue(result.stream().anyMatch(expense -> Objects.equals(expense, dummyExpense2)));
        assertTrue(result.stream().anyMatch(expense -> Objects.equals(expense, dummyExpense4)));
    }

    @Test
    public void shouldReturnAllCurrentMonthExpenses_When_MultipleExpensesArePresent_And_GetCurrentMonthExpensesIsCalled() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(
                dummyExpenseEntity1, dummyExpenseEntity2, dummyExpenseEntity3, dummyExpenseEntity4, dummyExpenseEntity5
        );
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);

        // When
        List<Expense> result = repository.getCurrentMonthExpenses();

        // Then
        assertEquals(result.size(), 3);

        // Contains
        assertTrue(result.stream().anyMatch(expense -> Objects.equals(expense, dummyExpense1)));
        assertTrue(result.stream().anyMatch(expense -> Objects.equals(expense, dummyExpense2)));
        assertTrue(result.stream().anyMatch(expense -> Objects.equals(expense, dummyExpense4)));

        // Does not contain
        assertTrue(result.stream().noneMatch(expense -> Objects.equals(expense, dummyExpense3)));
        assertTrue(result.stream().noneMatch(expense -> Objects.equals(expense, dummyExpense5)));
    }
    // End region

    // Region: Specific month expenses
    @Test
    public void shouldReturnEmptyList_When_InvalidInputProvided_And_GetExpensesForMonthIsCalled() {
        // Given
        Month invalidMonth = null;
        int invalidYear = -2021;

        // When
        List<Expense> result1 = repository.getExpensesForMonth(invalidMonth, 2024);
        List<Expense> result2 = repository.getExpensesForMonth(Month.JANUARY, invalidYear);
        List<Expense> result3 = repository.getExpensesForMonth(invalidMonth, invalidYear);

        // Then
        verify(databaseRepository, never()).getAllExpenses();

        assertTrue(result1.isEmpty());
        assertTrue(result2.isEmpty());
        assertTrue(result3.isEmpty());
    }

    @Test
    public void shouldReturnEmptyList_When_NoSpecificMonthExpenseAdded_And_GetExpensesForMonthIsCalled() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(dummyExpenseEntity1, dummyExpenseEntity2, dummyExpenseEntity3);
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);

        // When
        List<Expense> result = repository.getExpensesForMonth(Month.APRIL, 2024);

        // Then
        assertTrue(result.isEmpty());
    }

    @Test
    public void shouldReturnSpecificMonthExpenses_When_SpecificMonthExpenseAdded_And_GetExpensesForMonthIsCalled() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(
                dummyExpenseEntity1, dummyExpenseEntity2, dummyExpenseEntity3, dummyExpenseEntity4
        );
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);
        LocalDate currentLocalDate = LocalDate.now();

        // When
        List<Expense> result = repository.getExpensesForMonth(currentLocalDate.getMonth(), currentLocalDate.getYear());

        // Then
        assertEquals(result.size(), 3);
        assertTrue(result.stream().anyMatch(expense -> Objects.equals(expense, dummyExpense1)));
        assertTrue(result.stream().anyMatch(expense -> Objects.equals(expense, dummyExpense2)));
        assertTrue(result.stream().anyMatch(expense -> Objects.equals(expense, dummyExpense4)));

        assertTrue(result.stream().noneMatch(expense -> Objects.equals(expense, dummyExpense3)));
    }
    // End region

    // Region: Total expense cost for current month
    @Test
    public void shouldReturnZeroCost_When_NoCurrentMonthExpenseAdded_And_GetTotalExpenseCostForCurrentMonthIsCalled() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(dummyExpenseEntity3, dummyExpenseEntity5);
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);

        // When
        double result = repository.getTotalExpenseCostForCurrentMonth();

        // Then
        assertEquals(result, 0.0);
    }

    @Test
    public void shouldReturnProperCost_When_CurrentMonthExpenseAdded_And_GetTotalExpenseCostForCurrentMonthIsCalled() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(dummyExpenseEntity1, dummyExpenseEntity2, dummyExpenseEntity3);
        double expectedCost = dummyExpense1.cost() + dummyExpense2.cost();
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);

        // When
        double result = repository.getTotalExpenseCostForCurrentMonth();

        // Then
        assertEquals(result, expectedCost);
    }
    // End region

    // Region: Total expense cost for specific month
    @Test
    public void shouldReturnZeroCost_When_InvalidInputProvided_And_GetTotalExpenseCostForMonthIsCalled() {
        // Given
        Month nullMonth = null;
        int negativeYear = -2024;

        // When
        double result1 = repository.getTotalExpenseCostForMonth(nullMonth, 2024);
        double result2 = repository.getTotalExpenseCostForMonth(Month.APRIL, negativeYear);
        double result3 = repository.getTotalExpenseCostForMonth(nullMonth, negativeYear);

        // Then
        assertEquals(result1, 0.0);
        assertEquals(result2, 0.0);
        assertEquals(result3, 0.0);
    }

    @Test
    public void shouldReturnZeroCost_When_NoSpecificMonthExpenseAdded_And_GetTotalExpenseCostForMonthIsCalled() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(dummyExpenseEntity3, dummyExpenseEntity5);
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);

        // When
        double result = repository.getTotalExpenseCostForMonth(Month.MAY, 2024);

        // Then
        assertEquals(result, 0.0);
    }

    @Test
    public void shouldReturnProperCost_When_SpecificMonthExpenseAdded_And_GetTotalExpenseCostForMonthIsCalled() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(dummyExpenseEntity3, dummyExpenseEntity4, dummyExpenseEntity5);
        double expectedCost = 5.23;
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);

        // When
        double result = repository.getTotalExpenseCostForMonth(Month.MAY, 2023);

        // Then
        assertEquals(result, expectedCost);
    }
}