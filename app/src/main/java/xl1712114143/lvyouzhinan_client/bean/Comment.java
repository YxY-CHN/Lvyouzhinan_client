package xl1712114143.lvyouzhinan_client.bean;

public class Comment {
    private Integer id;
    private String message;
    private Integer article_id;

    public Integer getId() {
        return id;
    }

    public String getMessage() {
        return message;
    }

    public Integer getArticle_id() {
        return article_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setArticle_id(Integer article_id) {
        this.article_id = article_id;
    }
}
