package cmpt276.demo.controllers;

import cmpt276.demo.dao.FileRepository;
import cmpt276.demo.models.Files;
import cmpt276.demo.models.User;
import cmpt276.demo.service.FileService;
import cmpt276.demo.utils.FileUploadUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("file")
public class FileController {
    @Autowired
    private FileRepository fileRepo;

    @Autowired
    private FileService fileService;

    // public static String fileSavePath =
    // "C:\\Users\\Ali\\projects\\EIMS\\cmpt276-springboot-render\\src\\main\\resources\\uploadFile\\";
    public static String fileSavePath = "D:\\CMPT276\\cmpt276-springboot-render\\src\\main\\resources\\uploadFile\\";

    @RequestMapping(value = "/toFilePage", method = { RequestMethod.POST, RequestMethod.GET })
    public String toFilePage(HttpSession session, Model model) {
        User user = (User) session.getAttribute("user");
        List<Files> files = fileRepo.findAll();
        model.addAttribute("files", files);
        model.addAttribute("user", user);

        return "/files/files";

    }

    @PostMapping(value = "/fileList")
    @ResponseBody
    public Map fileList(HttpServletRequest request) {
        int page = Integer.parseInt(request.getParameter("page"));
        int pageSzie = Integer.parseInt(request.getParameter("rows"));// pageSzie
        int startRecord = (page - 1) * pageSzie + 1;
        int total = fileRepo.findAll().size();
        List<Files> userinforlist = fileService.findAllFile(startRecord, pageSzie);
        Map resultMap = new HashMap();
        resultMap.put("total", total);
        resultMap.put("rows", userinforlist);
        return resultMap;
    }

    /**
     * uploading file
     *
     * @return
     */
    @RequestMapping(value = "/upload", produces = "application/json; charset=utf-8")
    public String upload(MultipartHttpServletRequest multipartHttpServletRequest, HttpServletRequest request,
            MultipartFile file) throws IOException {
        String s = FileUploadUtil.uploadPahtFile(file, request, fileSavePath);

        Files files = new Files();
        files.setFileName(s);
        files.setCreateTime(new Date());
        fileRepo.save(files);
        return "redirect:/file/toFilePage";
    }

    /***
     * delete file
     *
     */
    @PostMapping(value = "/remove_file")
    @ResponseBody
    public Map<String, String> removeUsers(@RequestParam("id") Integer id, HttpSession session) {
        Map<String, String> result = new HashMap<>();
        fileRepo.deleteById(id);
        result.put("success", "true");
        System.out.println("delete  Id: " + id);
        return result;
    }

    @GetMapping("/download")
    public ResponseEntity<FileSystemResource> downloadFile(String fileName) {
        return export(new File(fileSavePath + fileName));
    }

    public ResponseEntity<FileSystemResource> export(File file) {
        if (file == null) {
            return null;
        }
        HttpHeaders headers = new HttpHeaders();
        headers.add("Cache-Control", "no-cache, no-store, must-revalidate");
        headers.add("Content-Disposition", "attachment; filename=" + file.getName());
        headers.add("Pragma", "no-cache");
        headers.add("Expires", "0");
        headers.add("Last-Modified", new Date().toString());
        headers.add("ETag", String.valueOf(System.currentTimeMillis()));
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("application/octet-stream"))
                .body(new FileSystemResource(file));
    }
}
