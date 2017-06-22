package cn.a52pojie.discuz.bean;

/**
 * Created by qtfreet00 on 2017/6/22.
 */

public class ForumItem {
    private String fid;
    private String name;
    private String threads;
    private String posts;
    private String todayPosts;
    private String description;

    public String getFid() {
        return fid;
    }

    public void setFid(String fid) {
        this.fid = fid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThreads() {
        return threads;
    }

    public void setThreads(String threads) {
        this.threads = threads;
    }

    public String getPosts() {
        return posts;
    }

    public void setPosts(String posts) {
        this.posts = posts;
    }

    public String getTodayPosts() {
        return todayPosts;
    }

    public void setTodayPosts(String todayPosts) {
        this.todayPosts = todayPosts;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
