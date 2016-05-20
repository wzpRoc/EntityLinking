package wzp.newsML;

/**
 * Created with IntelliJ IDEA.
 * User: WZP
 * Date: 15-12-19
 * Time: 下午6:55
 * To change this template use File | Settings | File Templates.
 */
public class News {
    String title;
    String headline;
    String byline;
    String dateline;
    String text;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getHeadline() {
        return headline;
    }

    public void setHeadline(String headline) {
        this.headline = headline;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getDateline() {
        return dateline;
    }

    public void setDateline(String dateline) {
        this.dateline = dateline;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
