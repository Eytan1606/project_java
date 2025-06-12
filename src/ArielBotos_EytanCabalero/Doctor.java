package ArielBotos_EytanCabalero;

import java.util.Objects;

public class Doctor extends Lecturer implements Researcher<String> {
    private CustomArray<String> articles = new CustomArray<>();

    public Doctor(String name, int id, Degree degree, String major, double salary){
        super(name, id, degree, major, salary);
    }

    @Override
    public boolean addArticle(String title) {
        Objects.requireNonNull(title, "Article title cannot be null.");
        String trimmed = title.trim();
        if (trimmed.isEmpty()) {
            throw new IllegalArgumentException("Article title cannot be empty.");
        }
        if (articles.contains(trimmed)) {
            return false;
        }
        articles.add(trimmed);
        return true;
    }

    @Override
    public int getArticleCount() {
        return articles.size();
    }

    @Override
    public String[] getArticles() {
        return articles.toArray(new String[0]);
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