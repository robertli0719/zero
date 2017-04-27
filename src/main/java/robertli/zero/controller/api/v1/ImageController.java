/*
 * Copyright 2017 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.controller.api.v1;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
import robertli.zero.core.PathService;
import robertli.zero.core.ImageService;
import robertli.zero.dto.FileRecordDto;
import robertli.zero.service.StorageService;

/**
 *
 * @version 2017-04-07 1.0.1
 * @author Robert Li
 */
@RestController
@RequestMapping("api/v1/images")
public class ImageController {

    private static final long ONE_MONTH = 31 * 24 * 3600 * 1000;
    private static final int BUFFER_SIZE = 1024 * 1024;//1M

    @Resource
    private StorageService storageService;

    @Resource
    private ImageService imageService;

    @Resource
    private PathService pathService;

    private boolean shouldFlushBuffer(HttpServletRequest request, long now) {
        final long ifModifiedSince = request.getDateHeader("If-Modified-Since");
        return ifModifiedSince > 0 && ifModifiedSince <= now;
    }

    private void setHeader(HttpServletResponse response, long now, String contentType) {
        final long expire = now + ONE_MONTH;
        response.setDateHeader("Date", now);
        response.setDateHeader("Expires", expire);
        response.setDateHeader("Retry-After", expire);
        response.setDateHeader("Last-Modified", now);
        response.setHeader("Cache-Control", "public");
        response.setContentType(contentType);
    }

    private void sendData(InputStream in, OutputStream out) throws IOException {
        final byte[] buffer = new byte[BUFFER_SIZE];
        int len;
        while ((len = in.read(buffer)) >= 0) {
            out.write(buffer, 0, len);
        }
        out.flush();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public void get(@PathVariable String id, HttpServletRequest request, HttpServletResponse response) throws IOException {
        final long now = (new Date()).getTime();
        final FileRecordDto fileRecord = storageService.getFileRecord(id);

        if (fileRecord == null) {
            String errorDetail = "can't found image id:" + id;
            throw new RestException("NOT_FOUND", "Image not found", errorDetail, HttpStatus.NOT_FOUND);
        }
        if (shouldFlushBuffer(request, now)) {
            response.setStatus(304);
            response.flushBuffer();
            return;
        }

        final String contentType = fileRecord.getContentType();
        setHeader(response, now, contentType);
        final InputStream in = storageService.getInputStream(id, 0);
        final OutputStream out = response.getOutputStream();
        try {
            sendData(in, out);
        } finally {
            in.close();
        }
    }

    @RequestMapping(method = RequestMethod.POST)
    public List<String> post(@RequestParam("file") List<MultipartFile> fileList) throws IOException {
        List<String> urlString = new ArrayList<>();
        for (MultipartFile file : fileList) {
            final String filename = file.getOriginalFilename();
            final String type = file.getContentType();
            final long size = file.getSize();
            final String uuid = storageService.register(filename, type, size);
            final InputStream in = file.getInputStream();
            storageService.store(uuid, in, size);
            String url = pathService.makeImageUrl(uuid);
            urlString.add(url);
        }
        storageService.clean();
        return urlString;
    }

    private byte[] cropImageData(InputStream inputStream, String fileType, int x, int y, int width, int height) throws IOException {
        BufferedImage image = imageService.readImage(inputStream);
        image = imageService.crop(image, x, y, width, height);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        imageService.writeImage(out, image, fileType);
        return out.toByteArray();
    }

    @RequestMapping(path = "cropper/{id}", method = RequestMethod.POST)
    public String cropper(@PathVariable String id, int x, int y, int width, int height) throws IOException {
        final FileRecordDto fileRecord = storageService.getFileRecord(id);
        final String name = fileRecord.getFileName();
        final InputStream inputStream = storageService.getInputStream(id, 0L);
        final byte[] data = cropImageData(inputStream, "jpg", x, y, width, height);
        final long size = data.length;
        final ByteArrayInputStream in = new ByteArrayInputStream(data);
        final String uuid = storageService.register(name, "image/jpeg", size);
        storageService.store(uuid, in, data.length);
        return pathService.makeImageUrl(uuid);
    }

    private byte[] fixImageData(InputStream inputStream, String fileType, int x, int y, int width, int height, int fixedWidth, int fixedHeight) throws IOException {
        BufferedImage image = imageService.readImage(inputStream);
        image = imageService.crop(image, x, y, width, height);
        image = imageService.scale(image, fixedWidth, fixedHeight);
        final ByteArrayOutputStream out = new ByteArrayOutputStream();
        imageService.writeImage(out, image, fileType);
        return out.toByteArray();
    }

    @RequestMapping(path = "fixer/{id}", method = RequestMethod.POST)
    public String fixer(@PathVariable String id, int x, int y, int width, int height, int fixedWidth, int fixedHeight) throws IOException {
        final FileRecordDto fileRecord = storageService.getFileRecord(id);
        final String name = fileRecord.getFileName();
        final InputStream inputStream = storageService.getInputStream(id, 0L);
        final byte[] data = fixImageData(inputStream, "jpg", x, y, width, height, fixedWidth, fixedHeight);
        final long size = data.length;
        final ByteArrayInputStream in = new ByteArrayInputStream(data);
        final String uuid = storageService.register(name, "image/jpeg", size);
        storageService.store(uuid, in, data.length);
        return pathService.makeImageUrl(uuid);
    }
}
