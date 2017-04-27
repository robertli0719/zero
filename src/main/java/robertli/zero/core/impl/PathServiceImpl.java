/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core.impl;

import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.PathService;
import robertli.zero.core.WebConfiguration;

/**
 * PathService can process URL for Storage
 *
 * @version 1.0.0 2017-04-12
 * @author Robert Li
 */
@Component("pathService")
public class PathServiceImpl implements PathService {

    @Resource
    private WebConfiguration webConfiguration;

    private Matcher createIdMatcher(String patternUrl, String content) {
        final String patternStr = patternUrl + "/[0-9a-z]{8}(-[0-9a-z]{4}){3}-[0-9a-z]{12}";
        final Pattern pattern = Pattern.compile(patternStr);
        return pattern.matcher(content);
    }

    private Matcher createImageIdMatcher(String content) {
        final String imageActionUrl = webConfiguration.getImageActionUrl();
        return createIdMatcher(imageActionUrl, content);
    }

    private Matcher createVideoIdMatcher(String content) {
        final String videoActionUrl = webConfiguration.getVideoActionUrl();
        return createIdMatcher(videoActionUrl, content);
    }

    @Override
    public String makeImageUrl(String uuid) {
        if (uuid == null) {
            return null;
        }
        final String imageActionUrl = webConfiguration.getImageActionUrl();
        return imageActionUrl + "/" + uuid;
    }

    @Override
    public String pickImageId(String url) {
        if (url == null) {
            return null;
        }
        final Matcher matcher = createImageIdMatcher(url);
        if (matcher.find()) {
            return matcher.group().split("images/")[1];
        }
        return null;
    }

    @Override
    public Set<String> pickImageIdSet(String content) {
        final Set<String> set = new HashSet<>();
        if (content == null) {
            return set;
        }
        final Matcher matcher = createImageIdMatcher(content);
        while (matcher.find()) {
            String url = matcher.group();
            String uuid = url.split("images/")[1];
            set.add(uuid);
        }
        return set;
    }

    @Override
    public String makeVideoUrl(String uuid) {
        if (uuid == null) {
            return null;
        }
        final String videoActionUrl = webConfiguration.getVideoActionUrl();
        return videoActionUrl + "/" + uuid;
    }

    @Override
    public String pickVideoId(String url) {
        if (url == null) {
            return null;
        }
        final Matcher matcher = createVideoIdMatcher(url);
        if (matcher.find()) {
            return matcher.group().split("videos/")[1];
        }
        return null;
    }

}
