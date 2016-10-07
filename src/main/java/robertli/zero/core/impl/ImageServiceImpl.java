/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.core.impl;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.springframework.stereotype.Component;
import robertli.zero.core.ImageService;

/**
 * Because we include com.twelvemonkeys.imageio in pom, ImageIO can support CMYK
 * for JPG.
 *
 * @version 1.0.1 2016-10-06
 * @author Robert Li
 */
@Component("imageService")
public class ImageServiceImpl implements ImageService {

    @Override
    public BufferedImage readImage(File fe) throws IOException {
        return ImageIO.read(fe);
    }

    @Override
    public BufferedImage readImage(byte[] data) throws IOException {
        InputStream in = new ByteArrayInputStream(data);
        return ImageIO.read(in);
    }

    @Override
    public void writeImage(File fe, BufferedImage image, String formatName) throws IOException {
        try (FileOutputStream out = new FileOutputStream(fe)) {
            ImageIO.write(image, formatName, out);
        }
    }

    @Override
    public byte[] getImageData(BufferedImage image, String formatName) throws IOException {
        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            ImageIO.write(image, formatName, baos);
            return baos.toByteArray();
        }
    }

    @Override
    public BufferedImage compress(BufferedImage image, int maxWidth, int maxHeight) {
        int width = image.getWidth();
        int height = image.getHeight();
        if (width <= maxWidth && height <= maxHeight) {
            return image;
        }

        if (1.0 * width / height > 1.0 * maxWidth / maxHeight) {
            return resizeByWidth(image, maxWidth);
        } else {
            return resizeByHeight(image, maxHeight);
        }
    }

    private BufferedImage createBufferedImage(Image img) {
        int width = img.getWidth(null);
        int height = img.getHeight(null);
        BufferedImage out = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = out.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);
        g2.setColor(Color.WHITE);
        g2.fillRect(0, 0, width, height);
        g2.drawImage(img, 0, 0, null);
        g2.dispose();
        return out;
    }

    @Override
    public BufferedImage resizeByWidth(BufferedImage image, int width) {
        double w = image.getWidth();
        double h = image.getHeight();
        double p = w / h;
        int height = (int) (width / p);
        Image img = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return createBufferedImage(img);
    }

    @Override
    public BufferedImage resizeByHeight(BufferedImage image, int height) {
        double w = image.getWidth();
        double h = image.getHeight();
        double p = w / h;
        int width = (int) (p * height);
        Image img = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return createBufferedImage(img);
    }

    @Override
    public BufferedImage crop(BufferedImage image, int x, int y, int width, int height) {
        return image.getSubimage(x, y, width, height);
    }

    @Override
    public BufferedImage scale(BufferedImage image, int width, int height) {
        Image img = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return createBufferedImage(img);
    }

}
