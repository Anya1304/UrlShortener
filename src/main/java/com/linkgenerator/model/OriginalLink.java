package com.linkgenerator.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash
public class OriginalLink implements Serializable {
    @Id
    private String link;
    private int countOfShortening;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getCountOfShortening() {
        return countOfShortening;
    }

    public void setCountOfShortening(int count) {
        this.countOfShortening = count;
    }
}
