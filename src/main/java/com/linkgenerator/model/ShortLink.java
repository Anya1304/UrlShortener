package com.linkgenerator.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;

@RedisHash
public class ShortLink implements Serializable {
    @Id
    private String link;
    private int countOfTransitions;
    private String originalId;

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public int getCountOfTransitions() {
        return countOfTransitions;
    }

    public void setCountOfTransitions(int count) {
        this.countOfTransitions = count;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }
}
