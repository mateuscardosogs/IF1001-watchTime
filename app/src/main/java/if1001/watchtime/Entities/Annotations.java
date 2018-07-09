package if1001.watchtime.Entities;

import java.io.Serializable;

public class Annotations implements Serializable {

    private static final long serialVersionUID = 1L;

    String content;
    String title;
    String priority;
    String category;
    String deadline;
    String datetime;


    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getCategory() { return category; }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) { this.deadline = deadline; }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) { this.datetime = datetime; }

}
