import java.util.Scanner;

public class AuthSystem {
    private static final int MAX_USERS = 15;
    private static User[] users = new User[MAX_USERS];

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1 - Додати користувача");
            System.out.println("2 - Видалити користувача");
            System.out.println("3 - Аутентифікація");
            System.out.println("4 - Вихід");
            System.out.print("Оберіть дію: ");

            int choice = scanner.nextInt();
            scanner.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Введіть ім'я: ");
                        String username = scanner.nextLine();
                        System.out.print("Введіть пароль: ");
                        String password = scanner.nextLine();
                        addUser(username, password);
                        break;
                    case 2:
                        System.out.print("Введіть ім'я користувача для видалення: ");
                        String delUsername = scanner.nextLine();
                        removeUser(delUsername);
                        break;
                    case 3:
                        System.out.print("Введіть ім'я: ");
                        String authUsername = scanner.nextLine();
                        System.out.print("Введіть пароль: ");
                        String authPassword = scanner.nextLine();
                        authenticate(authUsername, authPassword);
                        break;
                    case 4:
                        System.out.println("Програма завершена.");
                        return;
                    default:
                        System.out.println("Невірний вибір.");
                }
            } catch (AuthException e) {
                System.out.println("Помилка: " + e.getMessage());
            }
        }
    }

    private static void addUser(String username, String password) throws AuthException {
        for (int i = 0; i < MAX_USERS; i++) {
            if (users[i] == null) {
                users[i] = new User(username, password);
                System.out.println("Користувач доданий.");
                return;
            }
        }
        throw new UserLimitException("Досягнуто максимальну кількість користувачів (15).");
    }

    private static void removeUser(String username) throws AuthException {
        for (int i = 0; i < MAX_USERS; i++) {
            if (users[i] != null && users[i].getUsername().equals(username)) {
                users[i] = null;
                System.out.println("Користувача видалено.");
                return;
            }
        }
        throw new UserNotFoundException("Користувача не знайдено.");
    }

    private static void authenticate(String username, String password) throws AuthException {
        for (User user : users) {
            if (user != null && user.getUsername().equals(username)) {
                if (user.checkPassword(password)) {
                    System.out.println("Аутентифікація успішна.");
                    return;
                } else {
                    throw new AuthenticationException("Неправильний пароль.");
                }
            }
        }
        throw new AuthenticationException("Користувача не знайдено.");
    }

    private static class User {
        private String username;
        private String password;

        public User(String username, String password) throws AuthException {
            validateUsername(username);
            validatePassword(password);
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public boolean checkPassword(String inputPassword) {
            return this.password.equals(inputPassword);
        }

        private void validateUsername(String username) throws InvalidUsernameException {
            if (username.length() < 5 || username.contains(" ")) {
                throw new InvalidUsernameException("Ім'я користувача має бути не менше 5 символів і не містити пробілів.");
            }
        }

        private void validatePassword(String password) throws InvalidPasswordException {
            if (password.length() < 10 || password.contains(" ")) {
                throw new InvalidPasswordException("Пароль має бути не менше 10 символів і не містити пробілів.");
            }

            boolean hasSpecial = false;
            int digitCount = 0;
            String[] forbiddenWords = {"admin", "pass", "password", "qwerty", "ytrewq"};

            for (String word : forbiddenWords) {
                if (password.toLowerCase().contains(word)) {
                    throw new InvalidPasswordException("Пароль містить заборонене слово.");
                }
            }

            for (char c : password.toCharArray()) {
                if (Character.isDigit(c)) {
                    digitCount++;
                } else if (!Character.isLetterOrDigit(c)) {
                    hasSpecial = true;
                }
            }

            if (!hasSpecial || digitCount < 3) {
                throw new InvalidPasswordException("Пароль має містити хоча б 1 спеціальний символ і 3 цифри.");
            }
        }
    }

    private static class AuthException extends Exception {
        public AuthException(String message) {
            super(message);
        }
    }

    private static class UserLimitException extends AuthException {
        public UserLimitException(String message) {
            super(message);
        }
    }

    private static class InvalidUsernameException extends AuthException {
        public InvalidUsernameException(String message) {
            super(message);
        }
    }

    private static class InvalidPasswordException extends AuthException {
        public InvalidPasswordException(String message) {
            super(message);
        }
    }

    private static class UserNotFoundException extends AuthException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    private static class AuthenticationException extends AuthException {
        public AuthenticationException(String message) {
            super(message);
        }
    }
}
