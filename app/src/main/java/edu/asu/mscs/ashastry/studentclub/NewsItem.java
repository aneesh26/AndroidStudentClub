package edu.asu.mscs.ashastry.studentclub;

import java.util.Date;

/**
 * Created by A on 4/28/2015.
 */
public class NewsItem {
    private String newsTitle;
    private Date newsDate;
    private String newsBody;

    public String getNewsTitle() {
        return newsTitle;
    }

    public NewsItem(String newsTitle, Date newsDate, String newsBody) {
        this.newsTitle = newsTitle;
        this.newsDate = newsDate;
        this.newsBody = newsBody;
    }

    public void setNewsTitle(String newsTitle) {
        this.newsTitle = newsTitle;
    }

    public Date getNewsDate() {
        return newsDate;
    }

    public void setNewsDate(Date newsDate) {
        this.newsDate = newsDate;
    }

    public String getNewsBody() {
        return newsBody;
    }

    public void setNewsBody(String newsBody) {
        this.newsBody = newsBody;
    }
}
