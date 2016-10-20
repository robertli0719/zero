/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.file;

import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import org.json.JSONArray;
import org.json.JSONObject;
import robertli.zero.core.ImagePathService;
import robertli.zero.core.ImageService;
import robertli.zero.core.JsonService;
import robertli.zero.entity.FileRecord;
import robertli.zero.service.StorageService;
import robertli.zero.struts2.FileResultSupport;
import robertli.zero.struts2.TextResultSupport;

/**
 * This Action is for uploading and downloading images.<br>
 * We can set the limit of upload in ImageUploadValidationInterceptor
 *
 * @see robertli.zero.struts2.ImageUploadValidationInterceptor
 * @version 1.0 2016-09-29
 * @author Robert Li
 */
public class ImageAction implements FileResultSupport, TextResultSupport {

    @Resource
    private StorageService storageService;

    @Resource
    private ImageService imageService;

    @Resource
    private ImagePathService imagePathService;

    @Resource
    private JsonService jsonService;

    public static final long IMAGE_SIZE_LIMIT = 5 * 1024 * 1024;//5Mb
    public static final int MAX_WIDTH = 600;
    public static final int MAX_HEIGHT = 600;

    private String id;
    private byte[] content;
    private String contentType;
    private String textResult;

    private File img[];
    private String imgContentType[];
    private String imgFileName[];
    private int x;
    private int y;
    private int width;
    private int height;
    private int fixedWidth;
    private int fixedHeight;

    public String execute() {
        FileRecord record = storageService.getFileRecord(id);
        contentType = record.getType();
        content = storageService.get(id);
        return FILE;
    }

    public String log() {
        FileRecord record = storageService.getFileRecord(id);
        String name = record.getName();
        contentType = record.getType();
        JSONObject result = new JSONObject();
        result.put("name", name);
        result.put("type", contentType);
        textResult = result.toString();
        return TEXT;
    }

    private String createImageProcessResult(String uuid) {
        String url = imagePathService.makeImageUrl(uuid);
        JSONObject result = jsonService.createSuccessResult();
        result.put("url", url);
        result.put("uuid", uuid);
        return result.toString();
    }

    private String createUploadResult(List<String> urlList) {
        JSONArray array = new JSONArray();
        urlList.stream().forEach((url) -> {
            array.put(url);
        });
        JSONObject result = jsonService.createSuccessResult();
        result.put("urlList", urlList);
        return result.toString();
    }

    private byte[] readFile(File fe) throws IOException {
        byte[] result;
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(new FileInputStream(fe)))) {
            int size = in.available();
            result = new byte[size];
            in.read(result);
        }
        return result;
    }

    private ArrayList<String> uploadProcess() throws IOException {
        ArrayList<String> urlList = new ArrayList<>();
        for (int i = 0; i < img.length; i++) {
            String uuid = storageService.register(imgFileName[i], imgContentType[i]);
            content = readFile(img[i]);
            storageService.store(uuid, content);
            String url = imagePathService.makeImageUrl(uuid);
            urlList.add(url);
        }
        storageService.clean();
        return urlList;
    }

    public String upload() throws IOException {
        ArrayList<String> urlList = uploadProcess();
        textResult = createUploadResult(urlList);
        return TEXT;
    }

    private byte[] cropImageData(byte[] content, String fileType) throws IOException {
        BufferedImage image = imageService.readImage(content);
        image = imageService.crop(image, x, y, width, height);
        return imageService.getImageData(image, fileType);
    }

    public String crop() {
        FileRecord record = storageService.getFileRecord(id);
        byte[] oldData = storageService.get(id);
        try {
            String uuid = storageService.register(record.getName(), "image/jpeg");
            storageService.store(uuid, cropImageData(oldData, "jpg"));
            textResult = createImageProcessResult(uuid);
        } catch (IOException ex) {
            textResult = jsonService.createFailResult(ex.getMessage()).toString();
            Logger.getLogger(ImageAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return TEXT;
    }

    private byte[] fixImageData(byte[] content, String fileType) throws IOException {
        BufferedImage image = imageService.readImage(content);
        image = imageService.crop(image, x, y, width, height);
        image = imageService.scale(image, fixedWidth, fixedHeight);
        return imageService.getImageData(image, fileType);
    }

    public String fix() {
        FileRecord record = storageService.getFileRecord(id);
        byte[] oldData = storageService.get(id);
        try {
            String uuid = storageService.register(record.getName(), "image/jpeg");
            storageService.store(uuid, fixImageData(oldData, "jpg"));
            textResult = createImageProcessResult(uuid);
        } catch (IOException ex) {
            textResult = jsonService.createFailResult(ex.getMessage()).toString();
            Logger.getLogger(ImageAction.class.getName()).log(Level.SEVERE, null, ex);
        }
        return TEXT;
    }

    @Override
    public byte[] getFileInBytes() {
        return content;
    }

    @Override
    public String getContentType() {
        return contentType;
    }

    @Override
    public String getTextResult() {
        return textResult;
    }

    public void setId(String id) {
        this.id = id;
    }

    public File[] getImg() {
        return img;
    }

    public void setImg(File[] img) {
        this.img = img;
    }

    public String[] getImgContentType() {
        return imgContentType;
    }

    public void setImgContentType(String[] imgContentType) {
        this.imgContentType = imgContentType;
    }

    public String[] getImgFileName() {
        return imgFileName;
    }

    public void setImgFileName(String[] imgFileName) {
        this.imgFileName = imgFileName;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getFixedWidth() {
        return fixedWidth;
    }

    public void setFixedWidth(int fixedWidth) {
        this.fixedWidth = fixedWidth;
    }

    public int getFixedHeight() {
        return fixedHeight;
    }

    public void setFixedHeight(int fixedHeight) {
        this.fixedHeight = fixedHeight;
    }

}
