import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    static class Quote {
        int id;
        String content;
        String author;

        Quote(int id, String content, String author) {
            this.id = id;
            this.content = content;
            this.author = author;
        }
    }

    static List<Quote> quotes = new ArrayList<>();
    static int lastId = 0;

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("== 명언 앱 ==");

        while (true) {
            System.out.print("명령) ");
            String command = sc.nextLine().trim();

            if (command.equals("종료")) {
                break;
            } else if (command.equals("등록")) {
                register(sc);
            } else if (command.equals("목록")) {
                list();
            } else if (command.startsWith("삭제?id=")) {
                delete(command);
            } else if (command.startsWith("수정?id=")) {
                modify(sc, command);
            }
        }
    }

    static void register(Scanner sc) {
        System.out.print("명언 : ");
        String content = sc.nextLine();
        System.out.print("작가 : ");
        String author = sc.nextLine();

        if (!isValid(content) || !isValid(author)) {
            System.out.println("특수문자는 입력할 수 없습니다.");
            return;
        }

        lastId++;
        quotes.add(new Quote(lastId, content, author));

        System.out.println(lastId + "번 명언이 등록되었습니다.");
    }

    static void list() {
        System.out.println("번호 / 작가 / 명언");
        System.out.println("----------------------");

        for (int i = quotes.size() - 1; i >= 0; i--) {
            Quote quote = quotes.get(i);
            System.out.println(quote.id + " / " + quote.author + " / " + quote.content);
        }
    }

    static void delete(String command) {
        int id = Integer.parseInt(command.substring("삭제?id=".length()));

        int foundIndex = -1;
        for (int i = 0; i < quotes.size(); i++) {
            if (quotes.get(i).id == id) {
                foundIndex = i;
                break;
            }
        }

        if (foundIndex == -1) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
        } else {
            quotes.remove(foundIndex);
            System.out.println(id + "번 명언이 삭제되었습니다.");
        }
    }

    static void modify(Scanner sc, String command) {
        int id = Integer.parseInt(command.substring("수정?id=".length()));

        Quote found = null;
        for (Quote quote : quotes) {
            if (quote.id == id) {
                found = quote;
                break;
            }
        }

        if (found == null) {
            System.out.println(id + "번 명언은 존재하지 않습니다.");
            return;
        }

        System.out.println("명언(기존) : " + found.content);
        System.out.print("명언 : ");
        String content = sc.nextLine();

        System.out.println("작가(기존) : " + found.author);
        System.out.print("작가 : ");
        String author = sc.nextLine();

        if (!content.isBlank()) {
            found.content = content;
        }
        if (!author.isBlank()) {
            found.author = author;
        }
    }

    static boolean isValid(String text) {
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);

            boolean ok = Character.isLetterOrDigit(c) || c == ' ' || c == '.' || c == ',' || c == '!' || c == '?';
            if (!ok) {
                return false;
            }
        }
        return true;
    }
}
