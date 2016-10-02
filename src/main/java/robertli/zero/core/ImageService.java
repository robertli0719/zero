/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * This service is for process image files.<br>
 *
 * @version 1.0 2016-10-01
 * @author Robert Li
 */
public interface ImageService {

    public BufferedImage readImage(File fe) throws IOException;

    public void writeImage(File fe, BufferedImage image, String formatName) throws IOException;

    public BufferedImage compress(BufferedImage image, int maxWidth, int maxHeight);

    public BufferedImage resizeByWidth(BufferedImage image, int width);

    public BufferedImage resizeByHeight(BufferedImage image, int height);

}
