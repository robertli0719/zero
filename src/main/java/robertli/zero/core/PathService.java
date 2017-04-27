/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

import java.util.Set;

/**
 * PathService can process URL for Storage
 *
 * @version 1.0.0 2017-04-12
 * @author Robert Li
 */
public interface PathService {

    public String makeImageUrl(String uuid);

    public String pickImageId(String url);

    public Set<String> pickImageIdSet(String content);

    public String makeVideoUrl(String uuid);

    public String pickVideoId(String url);
}
