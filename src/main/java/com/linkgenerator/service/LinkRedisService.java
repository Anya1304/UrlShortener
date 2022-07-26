package com.linkgenerator.service;

import com.linkgenerator.StringGenerator;
import com.linkgenerator.model.OriginalLink;
import com.linkgenerator.model.PassedUserInfo;
import com.linkgenerator.model.ShortLink;
import com.linkgenerator.repository.OriginalLinkRepository;
import com.linkgenerator.repository.ShortLinkRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class LinkRedisService {
    private final ShortLinkRepository shortLinkRepository;
    private final StringGenerator stringGenerator;
    private final OriginalLinkRepository linkRepository;
    private final RedisTemplate<String, PassedUserInfo> redisTemplate;

    private final static int LENGTH_OF_GENERATING_STRING = 10;

    public LinkRedisService(ShortLinkRepository shortLinkRepository,
                            StringGenerator stringGenerator,
                            OriginalLinkRepository linkRepository,
                            RedisTemplate<String, PassedUserInfo> redisTemplate) {
        this.shortLinkRepository = shortLinkRepository;
        this.stringGenerator = stringGenerator;
        this.linkRepository = linkRepository;
        this.redisTemplate = redisTemplate;
    }

    public void save(OriginalLink originalLink) {
        linkRepository.save(originalLink);
    }

    public void save(ShortLink link) {
        link.setCountOfTransitions(link.getCountOfTransitions() + 1);
        shortLinkRepository.save(link);
    }

    public Optional<OriginalLink> get(OriginalLink originalLink) {
        return linkRepository.findById(originalLink.getLink());
    }

    public void updateCountOfShortening(OriginalLink originalLink) {
        originalLink.setCountOfShortening(originalLink.getCountOfShortening() + 1);
        linkRepository.save(originalLink);
    }

    public ShortLink getShortLink(String linkName) {
        Optional<ShortLink> shortLinkOptional = shortLinkRepository.findById(linkName);
        return shortLinkOptional.orElseThrow();
    }

    public ShortLink generateShortLink() {
        ShortLink shortLink = new ShortLink();
        shortLink.setLink("/l/" + stringGenerator.generateString(LENGTH_OF_GENERATING_STRING));
        return shortLink;
    }

    public void addToSet(String key, PassedUserInfo userInfo) {
        redisTemplate.opsForSet().add(key, userInfo);
    }

    public Set<PassedUserInfo> getPassedUserInfoByLink(ShortLink shortLink) {
        return redisTemplate.opsForSet().members(shortLink.getLink());
    }
}
