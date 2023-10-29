package cmpt276.demo.service;

import cmpt276.demo.models.Files;
import cmpt276.demo.models.User;

import java.util.List;

public interface FileService {

    List<Files> findAllFile(int pageNum, int pageSize);

}
