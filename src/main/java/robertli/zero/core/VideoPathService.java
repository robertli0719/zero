/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

/**
 * VideoPathService can process URL for VideoController
 *
 * @version 1.0 2017-04-05
 * @author Robert Li
 */
public interface VideoPathService {

    public String makeVideoUrl(String uuid);

    public String pickVideoId(String url);

}
