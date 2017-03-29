/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller.api.v1;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import robertli.zero.controller.RestException;
import robertli.zero.core.ImagePathService;
import robertli.zero.core.ImageService;
import robertli.zero.service.StorageService;

/**
 *
 * @version 2016-12-27 1.0
 * @author Robert Li
 */
@RestController
@RequestMapping("api/v1/images")
public class ImageController {

    private static final long ONE_MONTH = 31 * 24 * 3600 * 1000;

    @Resource
    private StorageService storageService;

    @Resource
    private ImageService imageService;

    @Resource
    private ImagePathService imagePathService;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public void get(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        long now = (new Date()).getTime();
        long expire = now + ONE_MONTH;
        long lastModifiedMillis = now;

        if (ifModifiedSince > 0 && ifModifiedSince <= lastModifiedMillis) {
            response.setStatus(304);
            response.flushBuffer();
            return;
        }
        String contentType = storageService.getContentType(id);
        if (contentType == null) {
            String errorDetail = "can't found image id:" + id;
            throw new RestException("NOT_FOUND", "Image not found", errorDetail, HttpStatus.NOT_FOUND);
        }
        response.setDateHeader("Date", now);
        response.setDateHeader("Expires", expire);
        response.setDateHeader("Retry-After", expire);
        response.setHeader("Cache-Control", "public");
        response.setDateHeader("Last-Modified", lastModifiedMillis);
        response.setContentType(contentType);

        byte[] data = storageService.get(id);
        response.getOutputStream().write(data);
        response.getOutputStream().flush();
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<String> post(@RequestParam("file") List<MultipartFile> fileList) throws IOException {
        List<String> urlString = new ArrayList<>();
        for (MultipartFile file : fileList) {
            String filename = file.getOriginalFilename();
            String type = file.getContentType();
            String uuid = storageService.register(filename, type);
            byte data[] = file.getBytes();
            storageService.store(uuid, data);
            String url = imagePathService.makeImageUrl(uuid);
            urlString.add(url);
        }
        storageService.clean();
        return urlString;
    }

    private byte[] cropImageData(byte[] content, String fileType, int x, int y, int width, int height) throws IOException {
        BufferedImage image = imageService.readImage(content);
        image = imageService.crop(image, x, y, width, height);
        return imageService.getImageData(image, fileType);
    }

    @RequestMapping(path = "cropper/{id}", method = RequestMethod.POST)
    public String cropper(@PathVariable String id, int x, int y, int width, int height) throws IOException {
        final String name = storageService.getFileName(id);
        byte[] oldData = storageService.get(id);
        String uuid = storageService.register(name, "image/jpeg");
        storageService.store(uuid, cropImageData(oldData, "jpg", x, y, width, height));
        String url = imagePathService.makeImageUrl(uuid);
        return url;
    }

    private byte[] fixImageData(byte[] content, String fileType, int x, int y, int width, int height, int fixedWidth, int fixedHeight) throws IOException {
        BufferedImage image = imageService.readImage(content);
        image = imageService.crop(image, x, y, width, height);
        image = imageService.scale(image, fixedWidth, fixedHeight);
        return imageService.getImageData(image, fileType);
    }

    @RequestMapping(path = "fixer/{id}", method = RequestMethod.POST)
    public String fixer(@PathVariable String id, int x, int y, int width, int height, int fixedWidth, int fixedHeight) throws IOException {
        final String name = storageService.getFileName(id);
        byte[] oldData = storageService.get(id);
        String uuid = storageService.register(name, "image/jpeg");
        storageService.store(uuid, fixImageData(oldData, "jpg", x, y, width, height, fixedWidth, fixedHeight));
        String url = imagePathService.makeImageUrl(uuid);
        return url;
    }
}
