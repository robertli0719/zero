/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * This service is for process image files.<br>
 *
 * @version 1.0.2 2017-04-07
 * @author Robert Li
 */
public interface ImageService {

    public BufferedImage readImage(File fe) throws IOException;

    public BufferedImage readImage(InputStream inputStream) throws IOException;

    public void writeImage(File fe, BufferedImage image, String formatName) throws IOException;

    public void writeImage(OutputStream outputStream, BufferedImage image, String formatName) throws IOException;

    public BufferedImage compress(BufferedImage image, int maxWidth, int maxHeight);

    public BufferedImage resizeByWidth(BufferedImage image, int width);

    public BufferedImage resizeByHeight(BufferedImage image, int height);

    public BufferedImage crop(BufferedImage image, int x, int y, int width, int height);

    public BufferedImage scale(BufferedImage image, int width, int height);
}
