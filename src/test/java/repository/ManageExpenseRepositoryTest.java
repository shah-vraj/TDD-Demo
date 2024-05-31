package repository;

import data.entity.ExpenseEntity;
import model.Expense;
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
    private final Expense dummyExpense5 = new Expense("Item4", 5.23, LocalDate.now());
    private final Expense dummyExpense6 = new Expense("Item4", 4.23, LocalDate.of(2023, Month.AUGUST, 16));
    private final Expense dummyExpense7 = new Expense("Item7", 4.23, LocalDate.now());

    private final ExpenseEntity dummyExpenseEntity1 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense1, 1);
    private final ExpenseEntity dummyExpenseEntity2 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense2, 2);
    private final ExpenseEntity dummyExpenseEntity3 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense3, 3);
    private final ExpenseEntity dummyExpenseEntity5 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense5, 5);
    private final ExpenseEntity dummyExpenseEntity6 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense6, 6);
    private final ExpenseEntity dummyExpenseEntity7 = ExpenseHelper.getExpenseEntityFromExpense(dummyExpense7, 7);

    private final Expense dummyExpense2updated = new Expense("Item2Updated", 4.46, LocalDate.now());

    @BeforeEach
    void setup() {
        repository = new ManageExpenseRepositoryImpl(databaseRepository);
    }

    // Region: Add Expense
    @Test
    public void shouldNotAddExpense_When_NullExpenseProvided_And_AddExpenseIsCalled() {
        // Given
        Expense expenseToAdd = null;

        // When
        boolean isExpenseAdded = repository.addExpense(expenseToAdd);

        // Then
        verify(databaseRepository, never()).addExpense(any());

        assertFalse(isExpenseAdded);
    }

    @Test
    public void shouldNotAddExpense_When_InvalidExpenseProvided_And_AddExpenseIsCalled() {
        // Given
        Expense nullNameExpense = new Expense(null, 1.0, LocalDate.now());
        Expense blankNameExpense = new Expense(" ", 1.0, LocalDate.now());
        Expense negativeCostExpense = new Expense("Milk", -10.0, LocalDate.now());
        Expense nullDateExpense = new Expense("Milk", 1.0, null);

        // When
        boolean isExpenseAdded1 = repository.addExpense(nullNameExpense);
        boolean isExpenseAdded2 = repository.addExpense(blankNameExpense);
        boolean isExpenseAdded3 = repository.addExpense(negativeCostExpense);
        boolean isExpenseAdded4 = repository.addExpense(nullDateExpense);

        // Then
        verify(databaseRepository, never()).addExpense(any());

        assertFalse(isExpenseAdded1);
        assertFalse(isExpenseAdded2);
        assertFalse(isExpenseAdded3);
        assertFalse(isExpenseAdded4);
    }

    @Test
    public void shouldAddExpense_When_ValidExpenseProvided_And_AddExpenseIsCalled() {
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
    public void shouldAddExpense_When_MultipleSimilarExpensesProvided_And_AddExpenseIsCalled() {
        // Given
        Expense expense1 = new Expense("Milk", 7.23, LocalDate.now());
        Expense expense2 = new Expense("Milk", 7.23, LocalDate.now());
        Expense expense3 = new Expense("Milk", 6.88, LocalDate.now());
        Expense expense4 = new Expense("Milk", 6.88, LocalDate.of(2024, Month.APRIL, 14));
        ArgumentCaptor<ExpenseEntity> expenseEntityArgumentCaptor = ArgumentCaptor.forClass(ExpenseEntity.class);
        when(databaseRepository.addExpense(any())).thenReturn(true);
        when(databaseRepository.getAllExpenses())
                .thenReturn(List.of(ExpenseHelper.getExpenseEntityFromExpense(expense1, 1)));

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
    // End region

    // Region: Remove expense
    @Test
    public void shouldNotRemoveExpense_When_NullExpenseProvided_And_RemoveExpenseIsCalled() {
        // Given
        Expense expenseToRemove = null;

        // When
        boolean isExpenseAdded = repository.removeExpense(expenseToRemove);

        // Then
        verify(databaseRepository, never()).removeExpense(1);

        assertFalse(isExpenseAdded);
    }

    @Test
    public void shouldNotRemoveExpense_When_InvalidExpenseProvided_And_RemoveExpenseIsCalled() {
        // Given
        Expense nullNameExpense = new Expense(null, 1.0, LocalDate.now());
        Expense blankNameExpense = new Expense(" ", 1.0, LocalDate.now());
        Expense negativeCostExpense = new Expense("Milk", -10.0, LocalDate.now());
        Expense nullDateExpense = new Expense("Milk", 1.0, null);

        // When
        boolean isExpenseRemoved1 = repository.removeExpense(nullNameExpense);
        boolean isExpenseRemoved2 = repository.removeExpense(blankNameExpense);
        boolean isExpenseRemoved3 = repository.removeExpense(negativeCostExpense);
        boolean isExpenseRemoved4 = repository.removeExpense(nullDateExpense);

        // Then
        verify(databaseRepository, never()).removeExpense(1);

        assertFalse(isExpenseRemoved1);
        assertFalse(isExpenseRemoved2);
        assertFalse(isExpenseRemoved3);
        assertFalse(isExpenseRemoved4);
    }

    @Test
    public void shouldReturnFalse_When_IdIsNotPresent_And_RemoveExpenseIsCalled() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(
                dummyExpenseEntity1,
                dummyExpenseEntity2,
                dummyExpenseEntity3,
                dummyExpenseEntity5,
                dummyExpenseEntity6,
                dummyExpenseEntity7
        );
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);

        // When
        boolean isExpenseRemoved = repository.removeExpense(dummyExpense4);

        // Then
        verify(databaseRepository, never()).removeExpense(4);
        assertFalse(isExpenseRemoved);
    }

    @Test
    public void shouldReturnTrue_When_IdIsPresent_And_RemoveExpenseIsCalled() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(dummyExpenseEntity1, dummyExpenseEntity2, dummyExpenseEntity3);
        ArgumentCaptor<Integer> expenseIdArgumentCaptor = ArgumentCaptor.forClass(Integer.class);
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);
        when(databaseRepository.removeExpense(2)).thenReturn(true);

        // When
        boolean isExpenseRemoved = repository.removeExpense(dummyExpense2);

        // Then
        verify(databaseRepository).removeExpense(expenseIdArgumentCaptor.capture());
        assertEquals(expenseIdArgumentCaptor.getValue(), dummyExpenseEntity2.getId());
        assertTrue(isExpenseRemoved);
    }
    // End region

    // Region: Update expense
    @Test
    public void shouldNotUpdateExpense_When_NullExpenseProvided_And_UpdateExpenseIsCalled() {
        // Given
        Expense nullExpense = null;

        // When
        repository.updateExpense(nullExpense, dummyExpense1);
        repository.updateExpense(dummyExpense1, nullExpense);
        repository.updateExpense(nullExpense, nullExpense);

        // Then
        verify(databaseRepository, never()).updateExpense(any());
    }

    @Test
    public void shouldNotUpdateExpense_When_InvalidExpenseProvided_And_UpdateExpenseIsCalled() {
        // Given
        Expense nullNameExpense = new Expense(null, 1.0, LocalDate.now());
        Expense blankNameExpense = new Expense(" ", 1.0, LocalDate.now());
        Expense negativeCostExpense = new Expense("Milk", -10.0, LocalDate.now());
        Expense nullDateExpense = new Expense("Milk", 1.0, null);

        // When
        repository.updateExpense(nullNameExpense, dummyExpense1);
        repository.updateExpense(dummyExpense1, nullNameExpense);
        repository.updateExpense(nullNameExpense, nullNameExpense);

        repository.updateExpense(blankNameExpense, dummyExpense1);
        repository.updateExpense(dummyExpense1, blankNameExpense);
        repository.updateExpense(blankNameExpense, blankNameExpense);

        repository.updateExpense(negativeCostExpense, dummyExpense1);
        repository.updateExpense(dummyExpense1, negativeCostExpense);
        repository.updateExpense(negativeCostExpense, negativeCostExpense);

        repository.updateExpense(nullDateExpense, dummyExpense1);
        repository.updateExpense(dummyExpense1, nullDateExpense);
        repository.updateExpense(nullDateExpense, nullDateExpense);

        // Then
        verify(databaseRepository, never()).updateExpense(any());
    }

    @Test
    public void shouldUpdateExpense_When_ValidExpensesProvided_And_UpdateExpenseIsCalled() {
        // Given
        List<ExpenseEntity> allExpenses = List.of(dummyExpenseEntity1, dummyExpenseEntity2, dummyExpenseEntity3);
        ArgumentCaptor<ExpenseEntity> expenseEntityArgumentCaptor = ArgumentCaptor.forClass(ExpenseEntity.class);
        when(databaseRepository.getAllExpenses()).thenReturn(allExpenses);

        // When
        repository.updateExpense(dummyExpenseEntity2.toExpense(), dummyExpense2updated);

        // Then
        verify(databaseRepository).updateExpense(expenseEntityArgumentCaptor.capture());
        ExpenseEntity capturedEntity = expenseEntityArgumentCaptor.getValue();
        assertEquals(capturedEntity.getId(), 2);
        assertTrue(ExpenseHelper.areTheSame(capturedEntity, dummyExpense2updated));
    }
    // End region
}