import java.util.Scanner;

public class AuthenticationSystem {
    private static String[] usernames = new String[10]; // Масив імен користувачів
    private static String[] passwords = new String[10]; // Масив паролів
    private static int userCount = 0; // Кількість зареєстрованих користувачів

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            System.out.println("\nМеню:");
            System.out.println("1. Реєстрація нового користувача");
            System.out.println("2. Видалення користувача");
            System.out.println("3. Вихід");
            System.out.print("Виберіть опцію: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    registerUser(scanner);
                    break;
                case 2:
                    deleteUser(scanner);
                    break;
                case 3:
                    System.out.println("Вихід...");
                    return;
                default:
                    System.out.println("Невірний вибір, спробуйте ще раз.");
            }
        }
    }

    private static void registerUser(Scanner scanner) {
        if (userCount >= usernames.length) {
            System.out.println("Максимальна кількість користувачів досягнута!");
            return;
        }
        System.out.print("Введіть ім'я користувача: ");
        String username = scanner.nextLine();
        System.out.print("Введіть пароль: ");
        String password = scanner.nextLine();

        for (int i = 0; i < userCount; i++) {
            if (usernames[i].equals(username)) {
                System.out.println("Користувач з таким ім'ям вже існує!");
                return;
            }
        }

        usernames[userCount] = username;
        passwords[userCount] = password;
        userCount++;
        System.out.println("Користувач успішно зареєстрований!");
    }

    private static void deleteUser(Scanner scanner) {
        if (userCount == 0) {
            System.out.println("Немає зареєстрованих користувачів.");
            return;
        }

        System.out.print("Введіть індекс користувача для видалення (0 - " + (userCount - 1) + "): ");
        int index = scanner.nextInt();
        if (index < 0 || index >= userCount) {
            System.out.println("Невірний індекс!");
            return;
        }

        for (int i = index; i < userCount - 1; i++) {
            usernames[i] = usernames[i + 1];
            passwords[i] = passwords[i + 1];
        }
        usernames[userCount - 1] = null;
        passwords[userCount - 1] = null;
        userCount--;

        System.out.println("Користувач видалений!");
    }
}