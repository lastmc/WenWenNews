package com.java.yandifei.network;

public class NewsContentEntry {
    public String id;
    public String title;
    public String content;
    public String source;
    public String time;

    public NewsContentEntry(String id,String title,String content,String source,String time){
        this.id = id;
        this.title = title;
        this.content = content;
        this.source = source;
        this.time = time;
    }

    public static NewsContentEntry getNewsById(String id){
        return new NewsContentEntry("-1","烤面筋","香香的口味\n你尝过没",
                "面筋哥","tomorrow");
    }
}
