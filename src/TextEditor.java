import java.util.Scanner;
import java.util.Stack;

public class TextEditor {
    private StringBuilder content;
    private Stack<Action> actionStack;

    public TextEditor() {
        content = new StringBuilder();
        actionStack = new Stack<>();
    }

    public void addText(String text) {
        content.append(text);
        actionStack.push(new Action(ActionType.ADD, text));
    }

    public void deleteText(int length) {
        if (length > content.length()) {
            length = content.length();
        }
        String deletedText = content.substring(content.length() - length);
        content.delete(content.length() - length, content.length());
        actionStack.push(new Action(ActionType.DELETE, deletedText));
    }

    public void undo() {
        if (!actionStack.isEmpty()) {
            Action lastAction = actionStack.pop();
            switch (lastAction.type) {
                case ADD:
                    content.delete(content.length() - lastAction.text.length(), content.length());
                    break;
                case DELETE:
                    content.append(lastAction.text);
                    break;
            }
        }
    }

    public void displayContent() {
        System.out.println("Current Content: " + content.toString());
    }

    public static void main(String[] args) {
        TextEditor editor = new TextEditor();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nChoose an action: add, delete, undo, display, exit");
            String action = scanner.nextLine();

            switch (action.toLowerCase()) {
                case "add":
                    System.out.print("Enter text to add: ");
                    String textToAdd = scanner.nextLine();
                    editor.addText(textToAdd);
                    break;
                case "delete":
                    System.out.print("Enter number of characters to delete: ");
                    int charsToDelete = Integer.parseInt(scanner.nextLine());
                    editor.deleteText(charsToDelete);
                    break;
                case "undo":
                    editor.undo();
                    break;
                case "display":
                    editor.displayContent();
                    break;
                case "exit":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid action. Please choose again.");
                    break;
            }
        }

        scanner.close();
    }
}
