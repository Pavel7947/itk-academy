import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class StringBuilderWithUndo {
    private List<Character> value;
    private final Stack<Memento> history;

    public StringBuilderWithUndo() {
        value = new ArrayList<>();
        history = new Stack<>();
    }

    // Внутренний класс для хранения состояния
    private record Memento(List<Character> state) {
    }

    // Создание снапшота текущего состояния
    private void saveToHistory() {
        history.push(new Memento(List.copyOf(value)));
    }

    // Метод undo
    public void undo() {
        if (!history.isEmpty()) {
            Memento memento = history.pop();
            value = new ArrayList<>(memento.state);
        }
    }

    public StringBuilderWithUndo append(String str) {
        saveToHistory(); // Сохраняем текущее состояние перед изменением
        for (char ch : str.toCharArray()) {
            value.add(ch);
        }
        return this;
    }

    public StringBuilderWithUndo delete(int start, int end) {
        saveToHistory();
        if (end > start) {
            value.subList(start, end).clear();
        }
        return this;
    }

    public String toString() {
        char[] charArray = new char[value.size()];
        for (int i = 0; i < value.size(); i++) {
            charArray[i] = value.get(i);
        }
        return new String(charArray);
    }

    // Дополнительные методы можно добавить по аналогии
}
