import data.enums.MenuOption;
import ui.ManageExpense;
import ui.ViewExpenses;
import ui.ViewRecentExpenses;

import java.util.Scanner;

public class SpendWiseApplication {

    public static void main(String[] args) {
        SpendWiseApplication spendWiseApplication = new SpendWiseApplication();
        spendWiseApplication.showMenu();
    }

    @SuppressWarnings("InfiniteRecursion") // Suppressing cause compiler is not figuring out System.exit
    private void showMenu() {
        ManageExpense manageExpense = new ManageExpense();
        ViewExpenses viewExpenses = new ViewExpenses();
        ViewRecentExpenses viewRecentExpenses = new ViewRecentExpenses();
        MenuOption selectedMenuOption = printMenuAndGetSelectedMenuOption();
        System.out.println();

        switch (selectedMenuOption) {
            case AddExpense -> manageExpense.showAddExpense();
            case RemoveExpense -> manageExpense.showRemoveExpense();
            case UpdateExpense -> manageExpense.showUpdateExpense();
            case CurrentMonthExpenses -> viewExpenses.showCurrentMonthExpenses();
            case SpecificMonthExpenses -> viewExpenses.showSpecificMonthExpenses();
            case CurrentMonthExpensesCost -> viewExpenses.showCurrentMonthExpensesCost();
            case SpecificMonthExpensesCost -> viewExpenses.showSpecificMonthExpensesCost();
            case RecentExpenses -> viewRecentExpenses.showRecentExpenses();
            case Exit -> System.exit(0);
        }
        showMenu();
    }

    private MenuOption printMenuAndGetSelectedMenuOption() {
        Scanner scanner = new Scanner(System.in);
        String menu = """
                --------------------------------------
                1. Add an expense
                2. Remove an expense
                3. Update an expense
                4. Get all expenses for current month
                5. Get all expenses for specific month
                6. Get current month expenses cost
                7. Get specific month expenses cost
                8. Get recent expenses
                9. Exit
                --------------------------------------
                """;
        System.out.print(menu);
        System.out.print("Select an option between 1 and 9: ");

        String selectedMenuOptionString = scanner.nextLine();
        if (!isValidMenuOption(selectedMenuOptionString)) {
            System.out.println("Oops wrong input provided, please try again.\n");
            return printMenuAndGetSelectedMenuOption();
        }
        return MenuOption.values()[Integer.parseInt(selectedMenuOptionString) - 1];
    }

    private boolean isValidMenuOption(String selectedMenuOptionString) {
        if (selectedMenuOptionString == null || selectedMenuOptionString.isBlank())
            return false;
        try {
            int selectedMenuOption = Integer.parseInt(selectedMenuOptionString);
            return selectedMenuOption > 0 && selectedMenuOption <= 9;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
