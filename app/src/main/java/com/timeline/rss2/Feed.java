package com.timeline.rss2;

public class Feed {
    String Title="";
    String Content="";
    String Pubdate = "";
    String imgurl="";
    String link="";

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getImgurl() {
        return imgurl;
    }

    public void setImgurl(String imgurl) {
        this.imgurl = imgurl;
    }


    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getPubdate() {
        return Pubdate;
    }

    public void setPubdate(String pubdate) {
        Pubdate = pubdate;
    }
}
