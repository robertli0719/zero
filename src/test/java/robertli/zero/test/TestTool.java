/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.test;

import java.io.IOException;
import java.util.Random;
import javax.annotation.Resource;
import org.springframework.stereotype.Component;
import robertli.zero.core.ImagePathService;
import robertli.zero.core.RandomCodeCreater;
import robertli.zero.service.StorageService;

/**
 *
 * @author Robert Li
 */
@Component("testTool")
public class TestTool {

    @Resource
    private RandomCodeCreater randomCodeCreater;

    @Resource
    private StorageService storageService;

    @Resource
    private ImagePathService imagePathService;

    public String imitateImageUpload() throws IOException {
        final byte data[] = new byte[1024 * 5];
        final String filename = randomCodeCreater.createRandomCode(10, RandomCodeCreater.CodeType.MIX) + "jpg";
        final String type = "image/jpeg";
        final Random rand = new Random();
        rand.nextBytes(data);

        String uuid = storageService.register(filename, type);
        storageService.store(uuid, data);
        String imgUrl = imagePathService.makeImageUrl(uuid);
        return imgUrl;
    }

    public RandomCodeCreater getRandomCodeCreater() {
        return randomCodeCreater;
    }

}
