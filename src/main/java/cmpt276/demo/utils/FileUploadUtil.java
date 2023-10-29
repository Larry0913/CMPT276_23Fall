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
 * 文件上传
 */
public class FileUploadUtil {




    public static String uploadPahtFile(MultipartFile uploadFile, HttpServletRequest req,String fileSavePath) {
        if ("".equals(uploadFile.getOriginalFilename())){
            return "";
        }
        String filePath = "";

        //固定物理路径
        File folder = new File(fileSavePath);
        //如果文件夹不存在则创建
        if (!folder.isDirectory()) {
            folder.mkdirs();//创建文件夹
        }
        //上传的文件名
        String oldName = uploadFile.getOriginalFilename();
        //新的文件名
        String newName = UUID.randomUUID().toString() +
                oldName.substring(oldName.lastIndexOf("."), oldName.length());
        try {
            uploadFile.transferTo(new File(folder, newName));
        } catch (IOException e) {
            e.printStackTrace();
            return "upload fail! ";
        }
        return newName;
    }
}