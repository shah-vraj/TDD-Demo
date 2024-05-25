package repository;

import data.entity.ExpenseEntity;
import data.model.Expense;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import repository.impl.ManageExpenseRepositoryImpl;
import util.ExpenseHelper;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ManageExpenseRepositoryTest {

    @Mock
    private DatabaseRepository databaseRepository;

    private ManageExpenseRepository repository;

    private final Expense dummyExpense1 = new Expense("Item1", 1.23, LocalDate.now());
    private final Expense dummyExpense2 = new Expense("Item2", 2.23, LocalDate.now());
    private final Expense dummyExpense3 = new Expense("Item3", 3.23, LocalDate.of(2022, Month.DECEMBER, 15));
    private final Expense dummyExpense4 = new Expense("Item4", 4.23, LocalDate.now());

    private final ExpenseEntity dummyExpenseEntity1 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense1, 1);
    private final ExpenseEntity dummyExpenseEntity2 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense2, 2);
    private final ExpenseEntity dummyExpenseEntity3 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense3, 3);

    @BeforeEach
    void setup() {
        repository = new ManageExpenseRepositoryImpl(databaseRepository);
    }

    @Test
    public void shouldNotAddExpenseWhenNullExpenseProvided() {
        // Given
        Expense expenseToAdd = null;

        // When
        boolean isExpenseAdded = repository.addExpense(expenseToAdd);

        // Then
        verify(databaseRepository, times(0)).addExpense(any());

        assertFalse(isExpenseAdded);
    }

    @Test
    public void shouldNotAddExpenseWhenInvalidExpenseProvided() {
        // Given
        Expense nullNameExpense = new Expense(null, 1.0, LocalDate.now());
        Expense negativeCostExpense = new Expense("Milk", -10.0, LocalDate.now());
        Expense nullDateExpense = new Expense("Milk", 1.0, null);

        // When
        boolean isExpenseAdded1 = repository.addExpense(nullNameExpense);
        boolean isExpenseAdded2 = repository.addExpense(negativeCostExpense);
        boolean isExpenseAdded3 = repository.addExpense(nullDateExpense);

        // Then
        verify(databaseRepository, times(0)).addExpense(any());

        assertFalse(isExpenseAdded1);
        assertFalse(isExpenseAdded2);
        assertFalse(isExpenseAdded3);
    }

    @Test
    public void shouldAddExpenseWhenValidExpenseProvided() {
        // Given
        Expense expense = new Expense("Milk", 7.23, LocalDate.now());
        ArgumentCaptor<ExpenseEntity> expenseEntityArgumentCaptor = ArgumentCaptor.forClass(ExpenseEntity.class);
        when(databaseRepository.addExpense(any())).thenReturn(true);

        // When
        boolean isExpenseAdded = repository.addExpense(expense);

        // Then
        verify(databaseRepository).addExpense(expenseEntityArgumentCaptor.capture());
        assertTrue(ExpenseHelper.areTheSame(expenseEntityArgumentCaptor.getValue(), expense));
        assertTrue(isExpenseAdded);
    }

    @Test
    public void shouldAddExpenseWhenMultipleSimilarExpensesProvided() {
        // Given
        Expense expense1 = new Expense("Milk", 7.23, LocalDate.now());
        Expense expense2 = new Expense("Milk", 7.23, LocalDate.now());
        Expense expense3 = new Expense("Milk", 6.88, LocalDate.now());
        Expense expense4 = new Expense("Milk", 6.88, LocalDate.of(2024, Month.APRIL, 14));
        ArgumentCaptor<ExpenseEntity> expenseEntityArgumentCaptor = ArgumentCaptor.forClass(ExpenseEntity.class);
        when(databaseRepository.addExpense(any())).thenReturn(true);

        // When & Then
        boolean isExpenseAdded = repository.addExpense(expense1);
        verify(databaseRepository).addExpense(expenseEntityArgumentCaptor.capture());
        assertTrue(ExpenseHelper.areTheSame(expenseEntityArgumentCaptor.getValue(), expense1));
        assertTrue(isExpenseAdded);

        isExpenseAdded = repository.addExpense(expense2);
        verify(databaseRepository, times(2)).addExpense(expenseEntityArgumentCaptor.capture());
        assertTrue(ExpenseHelper.areTheSame(expenseEntityArgumentCaptor.getValue(), expense2));
        assertTrue(isExpenseAdded);

        isExpenseAdded = repository.addExpense(expense3);
        verify(databaseRepository, times(3)).addExpense(expenseEntityArgumentCaptor.capture());
        assertTrue(ExpenseHelper.areTheSame(expenseEntityArgumentCaptor.getValue(), expense3));
        assertTrue(isExpenseAdded);

        isExpenseAdded = repository.addExpense(expense4);
        verify(databaseRepository, times(4)).addExpense(expenseEntityArgumentCaptor.capture());
        assertTrue(ExpenseHelper.areTheSame(expenseEntityArgumentCaptor.getValue(), expense4));
        assertTrue(isExpenseAdded);
    }

    @Test
    public void shouldReturnFalseWhenRemoveExpenseCalledAndIdIsNotPresent() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(dummyExpenseEntity1, dummyExpenseEntity2, dummyExpenseEntity3);
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);

        // When
        boolean isExpenseRemoved = repository.removeExpense(dummyExpense4);

        // Then
        verify(databaseRepository, times(0)).removeExpense(any());
        assertFalse(isExpenseRemoved);
    }

    @Test
    public void shouldReturnTrueWhenRemoveExpenseCalledAndIdIsPresent() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(dummyExpenseEntity1, dummyExpenseEntity2, dummyExpenseEntity3);
        ArgumentCaptor<Integer> expenseIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);
        when(databaseRepository.removeExpense(any())).thenReturn(true);

        // When
        boolean isExpenseRemoved = repository.removeExpense(dummyExpense1);

        // Then
        verify(databaseRepository).removeExpense(expenseIdArgumentCaptor.capture());
        assertEquals(expenseIdArgumentCaptor.getValue(), dummyExpenseEntity1.getId());
        assertTrue(isExpenseRemoved);
    }
}