package com.linkgenerator.controller;

import com.linkgenerator.model.OriginalLink;
import com.linkgenerator.model.PassedUserInfo;
import com.linkgenerator.model.ShortLink;
import com.linkgenerator.service.LinkRedisService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

@RestController
public class ShortLinkController {
    private final LinkRedisService originalLinkService;

    public ShortLinkController(LinkRedisService originalLinkService) {
        this.originalLinkService = originalLinkService;
    }

    @PostMapping("/api/generate")
    public ShortLink generateShortLink(@RequestBody OriginalLink originalLink) {
        if (originalLinkService.get(originalLink).isEmpty()) {
            originalLinkService.save(originalLink);
        } else {
            originalLinkService.updateCountOfShortening(originalLink);
        }
        ShortLink shortLink = originalLinkService.generateShortLink();
        shortLink.setOriginalId(originalLink.getLink());
        originalLinkService.save(shortLink);

        return shortLink;
    }

    @GetMapping("/{name}/{short-name}")
    public void redirectToOriginalLink(@PathVariable(name = "short-name") String shortName,
                                       @PathVariable(name = "name") String name,
                                       HttpServletResponse response,
                                       HttpServletRequest request) throws IOException {
        String linkName = "/" + name + "/" + shortName;
        ShortLink shortLink = originalLinkService.getShortLink(linkName);
        originalLinkService.save(shortLink);
        originalLinkService.addToSet(linkName, createUserInfoFromRequest(request));

        response.sendRedirect(shortLink.getOriginalId());
    }

    @GetMapping("/{name}/{short-name}/info")
    public Set<PassedUserInfo> getUserInfo(@PathVariable(name = "short-name") String shortName,
                                           @PathVariable(name = "name") String name) {
        String linkName = "/" + name + "/" + shortName;
        ShortLink shortLink = originalLinkService.getShortLink(linkName);
        return originalLinkService.getPassedUserInfoByLink(shortLink);
    }

    private PassedUserInfo createUserInfoFromRequest(HttpServletRequest request) {
        PassedUserInfo userInfo = new PassedUserInfo();
        userInfo.setIpAddress(request.getRemoteAddr());
        userInfo.setBrowser(request.getHeader("User-Agent"));
        return userInfo;
    }
}
