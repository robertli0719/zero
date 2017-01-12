/*
 * Copyright 2016 Robert Li.
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
import robertli.zero.core.ImagePathService;
import robertli.zero.core.WebConfiguration;

@Component("imagePathService")
public class ImagePathServiceImpl implements ImagePathService {

    @Resource
    private WebConfiguration webConfiguration;

    private Matcher createImageIdMatcher(String content) {
        String imageActionUrl = webConfiguration.getImageActionUrl();
        String patternStr = imageActionUrl + "/[0-9a-z]{8}(-[0-9a-z]{4}){3}-[0-9a-z]{12}";
        Pattern pattern = Pattern.compile(patternStr);
        return pattern.matcher(content);
    }

    @Override
    public String makeImageUrl(String uuid) {
        String imageActionUrl = webConfiguration.getImageActionUrl();
        return imageActionUrl + "/" + uuid;
    }

    @Override
    public String pickImageId(String url) {
        Matcher matcher = createImageIdMatcher(url);
        if (matcher.find()) {
            return matcher.group().split("images/")[1];
        }
        return null;
    }

    @Override
    public Set<String> pickImageIdSet(String content) {
        Matcher matcher = createImageIdMatcher(content);
        Set<String> set = new HashSet<>();
        while (matcher.find()) {
            String url = matcher.group();
            String uuid = url.split("images/")[1];
            set.add(uuid);
        }
        return set;
    }

}
