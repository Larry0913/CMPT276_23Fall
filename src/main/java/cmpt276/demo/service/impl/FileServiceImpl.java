package cmpt276.demo.service.impl;

import cmpt276.demo.dao.FileRepository;
import cmpt276.demo.dao.UserRepository;
import cmpt276.demo.models.Files;
import cmpt276.demo.models.User;
import cmpt276.demo.service.FileService;
import cmpt276.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FileServiceImpl implements FileService {

    @Autowired
    FileRepository fileRepository;

    @Override
    public List<Files> findAllFile(int pageNum, int pageSize){
        PageHelper.startPage(pageNum, pageSize);
        return fileRepository.findAll();
    }


}
