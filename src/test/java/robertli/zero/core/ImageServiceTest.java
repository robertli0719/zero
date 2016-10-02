/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Robert Li
 */
public class ImageServiceTest {

    private final String TEST_PIC_DIR_PATH = "C:/Users/liliu/Desktop/img";

    private final ImageService imageService;

    public ImageServiceTest() throws IOException {
        ApplicationContext context = new ClassPathXmlApplicationContext("spring.xml");
        imageService = (ImageService) context.getBean("imageService");

    }

    public void test() throws IOException {
        String file = TEST_PIC_DIR_PATH + "/b.jpg";
        BufferedImage image = imageService.readImage(new File(file));
        image = imageService.compress(image, 600, 600);

        String types[] = {"jpg", "png", "bmp"};
        for (String type : types) {
            File outFile = new File(TEST_PIC_DIR_PATH + "/out." + type);
            imageService.writeImage(outFile, image, type);
        }
    }

    public static void main(String args[]) throws IOException {
        ImageServiceTest imageServiceTest = new ImageServiceTest();
        imageServiceTest.test();
    }
}
