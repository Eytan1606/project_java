package ArielBotos_EytanCabalero;

public class Doctor extends Lecturer implements Researcher {
    private String[] articles;
    private int articleCount;
    private static final int INITIAL_ART_SIZE = 4;

    public Doctor(String name, int id, Degree degree, String major, double salary) {
        super(name, id, degree, major, salary);
        this.articles = new String[INITIAL_ART_SIZE];
        this.articleCount = 0;
    }

    @Override
    public boolean addArticle(String title) {
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("Article title cannot be empty");
        if (articleCount == articles.length) {
            String[] temp = new String[articles.length * 2];
            System.arraycopy(articles, 0, temp, 0, articles.length);
            articles = temp;
        }
        articles[articleCount++] = title.trim();
        return true;
    }

    @Override
    public int getArticleCount() {
        return articleCount;
    }

    @Override
    public String[] getArticles() {
        if (articleCount == 0) {
            return new String[0];
        }
        String[] copy = new String[articleCount];
        System.arraycopy(articles, 0, copy, 0, articleCount);
        return copy;
    }

    @Override
    public String toString() {
        return String.format("%s , Articles: %d", super.toString(), articleCount);
    }
}