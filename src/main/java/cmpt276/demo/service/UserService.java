package cmpt276.demo.service;

import cmpt276.demo.models.User;

import java.util.List;

public interface UserService {

    List<User> findAllUser(int pageNum, int pageSize);

    public int getUsernumber();

}