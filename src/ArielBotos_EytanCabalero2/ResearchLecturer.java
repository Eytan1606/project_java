package ArielBotos_EytanCabalero2;

public class ResearchLecturer extends Lecturer {
    private static final int GROW = 2;
    private String[] articles;
    private int articleCount;

    public ResearchLecturer(String name, int id, Degree deg, String major, double salary) {
        super(name, id, deg, major, salary);
        this.articles = new String[4];
        this.articleCount = 0;
    }

    public boolean addArticle(String title) {
        if (title == null || title.trim().isEmpty())
            throw new IllegalArgumentException("Article title cannot be empty");
        if (articleCount == articles.length) {
            String[] temp = new String[articles.length * GROW];
            System.arraycopy(articles, 0 , temp, 0 ,articles.length);
            articles = temp;
        }
        articles[articleCount++] = title.trim();
        return true;
    }

    public int getArticleCount() { return articleCount; }



    public String[] getArticles() {
        if (articleCount == 0){
            return new String[0];
        }
        String[] copy = new String[articleCount];
        System.arraycopy(articles, 0, copy, 0, articleCount);
        return copy;
    }


    @Override
    public String toString() {
        return String.format("%s , Articles: %d", super.toString(), getArticleCount());
    }
}