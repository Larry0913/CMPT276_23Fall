package cmpt276.demo.service.impl;

import cmpt276.demo.dao.UserRepository;
import cmpt276.demo.models.User;
import cmpt276.demo.service.UserService;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public List<User> findAllUser(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        return userRepository.findAll();
    }

    @Override
    public int getUsernumber() {
        return userRepository.findAll().size();
    }

}
