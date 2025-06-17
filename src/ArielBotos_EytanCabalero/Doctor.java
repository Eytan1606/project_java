package ArielBotos_EytanCabalero;

import java.io.Serializable;
import java.util.Objects;

public class Doctor extends Lecturer implements Researcher<String> , Serializable {
    private CustomArray<String> articles = new CustomArray<>();

    private static final long serialVersionUID = 1L;

    public Doctor(String name, int id, Degree degree, String major, double salary){
        super(name, id, degree, major, salary);
    }

    @Override
    public void addArticle(String title) {
        Objects.requireNonNull(title, "Article title cannot be null.");
        String trimmed = title.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Article title cannot be empty.");
        }
        if (articles.contains(trimmed)) {
            return;
        }
        articles.add(trimmed);
    }

    @Override
    public int getArticleCount() {
        return articles.size();
    }

    @Override
    public String toString() {
        return String.format(
                "%s, Articles: %d",
                super.toString(),
                getArticleCount()
        );
    }
}