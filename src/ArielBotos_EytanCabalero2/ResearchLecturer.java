package ArielBotos_EytanCabalero2;

public class ResearchLecturer extends Lecturer {
    private static final int GROW = 2;
    private String[] articles;
    private int articleCount;

    public ResearchLecturer(String name, int id, Degree deg, String major, double salary) {
        super(name, id, deg, major, salary);
        this.articles = new String[2];
        this.articleCount = 0;
    }

    public boolean addArticle(String title) {
        if (title == null || title.isBlank()) return false;
        if (articleCount == articles.length) {
            String[] larger = new String[articles.length * GROW];
            for (int i = 0; i < articleCount; i++) larger[i] = articles[i];
            articles = larger;
        }
        articles[articleCount++] = title.trim();
        return true;
    }

    public int getArticleCount() { return articleCount; }
    public String[] getArticles() {
        String[] copy = new String[articleCount];
        for (int i = 0; i < articleCount; i++) copy[i] = articles[i];
        return copy;
    }

    @Override
    public String toString() {
        return super.toString() + " - Articles: " + articleCount;
    }
}