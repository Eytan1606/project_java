package ArielBotos_EytanCabalero;

public interface Researcher<T> {
    boolean addArticle(T item);

    int     getArticleCount();
    String[] getArticles();

}
