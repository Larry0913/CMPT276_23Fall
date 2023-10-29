package cmpt276.demo.utils;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * Uploading file
 */
public class FileUploadUtil {

    public static String uploadPahtFile(MultipartFile uploadFile, HttpServletRequest req, String fileSavePath) {
        if ("".equals(uploadFile.getOriginalFilename())) {
            return "";
        }
        String filePath = "";

        // fix path
        File folder = new File(fileSavePath);
        // if directory doesn't exist, then create
        if (!folder.isDirectory()) {
            folder.mkdirs();// create directory
        }
        // uploading filename
        String oldName = uploadFile.getOriginalFilename();
        // new filename
        String newName = UUID.randomUUID().toString() +
                oldName.substring(oldName.lastIndexOf("."), oldName.length());
        try {
            uploadFile.transferTo(new File(folder, newName));
        } catch (IOException e) {
            e.printStackTrace();
            return "Upload fail! ";
        }
        return newName;
    }
}