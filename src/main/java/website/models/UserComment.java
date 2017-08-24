package website.models;

import java.util.Date;

/**
 * Created by Kyung on 7/3/2017.
 */
public class UserComment {
    private Date date = new Date();
    private String commenterId;
    private String content;

    public String getCommenterId()
    {
        return this.commenterId;
    }
    public void setCommenterId(String commenterId)
    {
        this.commenterId = commenterId;
    }
    public String getContent()
    {
        return this.content;
    }
    public void setContent(String content)
    {
        this.content = content;
    }
    public Date getDate()
    {
        return this.date;
    }
    public void setDate(Date date)
    {
        this.date = date;
    }
}
