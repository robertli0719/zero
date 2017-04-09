/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core.impl;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.VideoPathService;
import robertli.zero.core.WebConfiguration;

/**
 *
 * @author Robert Li
 */
@Component("videoPathService")
public class VideoPathServiceImpl implements VideoPathService {

    @Resource
    private WebConfiguration webConfiguration;

    @Override
    public String makeVideoUrl(String uuid) {
        String videoActionUrl = webConfiguration.getVideoActionUrl();
        return videoActionUrl + "/" + uuid;
    }

    private Matcher createVideoIdMatcher(String content) {
        String videoActionUrl = webConfiguration.getVideoActionUrl();
        String patternStr = videoActionUrl + "/[0-9a-z]{8}(-[0-9a-z]{4}){3}-[0-9a-z]{12}";
        Pattern pattern = Pattern.compile(patternStr);
        return pattern.matcher(content);
    }

    @Override
    public String pickVideoId(String url) {
        Matcher matcher = createVideoIdMatcher(url);
        if (matcher.find()) {
            return matcher.group().split("videos/")[1];
        }
        return null;
    }

}
