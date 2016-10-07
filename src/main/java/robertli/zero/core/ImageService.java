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
 * @version 1.0.1 2016-10-06
 * @author Robert Li
 */
public interface ImageService {

    public BufferedImage readImage(File fe) throws IOException;

    public BufferedImage readImage(byte[] data) throws IOException;

    public void writeImage(File fe, BufferedImage image, String formatName) throws IOException;

    public byte[] getImageData(BufferedImage image, String formatName) throws IOException;

    public BufferedImage compress(BufferedImage image, int maxWidth, int maxHeight);

    public BufferedImage resizeByWidth(BufferedImage image, int width);

    public BufferedImage resizeByHeight(BufferedImage image, int height);

    public BufferedImage crop(BufferedImage image, int x, int y, int width, int height);

    public BufferedImage scale(BufferedImage image, int width, int height);
}
