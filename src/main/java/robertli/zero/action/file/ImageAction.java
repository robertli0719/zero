/*
 * Copyright 2016 Robert Li.
 * Released under the MIT license
 * https://opensource.org/licenses/MIT
 */
package robertli.zero.action.file;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;
import org.json.JSONArray;
import org.json.JSONObject;
import robertli.zero.core.WebConfiguration;
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
    private WebConfiguration webConfiguration;

    private static final long IMAGE_SIZE_LIMIT = 5 * 1024 * 1024;//5Mb

    private String id;
    private byte[] content;
    private String contentType;
    private String textResult;

    private File img[];
    private String imgContentType[];
    private String imgFileName[];

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

    private JSONObject createSuccessResult() {
        JSONObject json = new JSONObject();
        json.put("result", "success");
        return json;
    }

    private JSONObject createFailResult(String errorString) {
        JSONObject json = new JSONObject();
        json.put("result", "fail");
        json.put("errorString", errorString);
        return json;
    }

    private String createImageUrl(String uuid) {
        String urlHeader = webConfiguration.getImageActionUrl();
        return urlHeader + "?id=" + uuid;
    }

    private String createUploadResult(List<String> urlList) {
        JSONArray array = new JSONArray();
        urlList.stream().forEach((url) -> {
            array.put(url);
        });
        JSONObject result = createSuccessResult();
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
            String url = createImageUrl(uuid);
            urlList.add(url);
        }
        storageService.clean();
        return urlList;
    }

    private String validateUpload() {
        if (img == null || img.length == 0) {
            JSONObject result = createFailResult("not found img in params");
            return result.toString();
        }
        for (String type : imgContentType) {
            if (type.startsWith("image/") == false) {
                JSONObject result = createFailResult("wrong file type");
                return result.toString();
            }
        }
        for (File fe : img) {
            if (fe.length() > IMAGE_SIZE_LIMIT) {
                JSONObject result = createFailResult("the file is bigger than the limit in ImageAction");
                return result.toString();
            }
        }
        return null;
    }

    public String upload() throws IOException {
        textResult = validateUpload();
        if (textResult != null) {
            return TEXT;
        }
        ArrayList<String> urlList = uploadProcess();
        textResult = createUploadResult(urlList);
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

    public void setImg(File[] img) {
        this.img = img;
    }

    public void setImgContentType(String[] imgContentType) {
        this.imgContentType = imgContentType;
    }

    public void setImgFileName(String[] imgFileName) {
        this.imgFileName = imgFileName;
    }

}
