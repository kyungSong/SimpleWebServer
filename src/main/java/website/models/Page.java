package website.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Kyung on 7/3/2017.
 */
public class Page {
    private Long id;
    private Date date = new Date();
    private String title;
    private String body;
    private Set<UserComment> comments = new HashSet<>();

    public Long getId()
    {
        return id;
    }
    public void setId(Long id)
    {
        this.id = id;
    }
    public String getTitle()
    {
        return this.title;
    }
    public void setTitle(String title)
    {
        this.title = title;
    }
    public String getBody()
    {
        return this.body;
    }
    public void setBody(String body)
    {
        this.body = body;
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
