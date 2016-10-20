/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

import java.util.Set;

/**
 * ImagePathService can process URL for ImageAction
 *
 * @see robertli.zero.action.file.ImageAction
 * @version 1.0 2016-10-19
 * @author Robert Li
 */
public interface ImagePathService {

    public String makeImageUrl(String uuid);

    public String pickImageId(String url);

    public Set<String> pickImageIdSet(String content);
}
