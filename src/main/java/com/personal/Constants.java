package com.personal;

import org.apache.commons.io.FileUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

public class Constants {
    public static final String DEFAULT_PAGE_SIZE = "10";

    public static final String SEARCH_PREFIX = "search_";

    public static final String SESSION_USER = "PERSON_SESSION_USER";

    public static final String SYSTEM_TEMP_PATH = FileUtils.getTempDirectoryPath();

    public static final String ZIP_FILE_EXT = ".zip";

    public static final int DEFAULT_IMAGE_WIDTH = 800;
    public static final int DEFAULT_IMAGE_HEIGHT = 600;
    public static final String DEFAULT_IMAGE_EXT = ".jpg";

    public static void setImageContentType(String extName, HttpHeaders headers) {
        if (extName.equals("png")) {
            headers.setContentType(MediaType.IMAGE_PNG);
        } else if (extName.equals("gif")) {
            headers.setContentType(MediaType.IMAGE_GIF);
        } else {
            headers.setContentType(MediaType.IMAGE_JPEG);
        }
    }
}
