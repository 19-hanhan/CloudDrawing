package cn.edu.jxnu.server.service.impl;

import cn.edu.jxnu.server.dao.UserMapper;
import cn.edu.jxnu.server.domain.User;
import cn.edu.jxnu.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper mapper;

    @Override
    public int AddUser(User usr) {
        return mapper.AddUser(usr);
    }

    @Override
    public int UpdateInformationById(User usr) {
        return mapper.UpdateInformationById(usr);
    }

    @Override
    public int DeleteById(int id) {
        return mapper.DeleteById(id);
    }

    @Override
    public List<User> FindAll() {
        return mapper.FindAll();
    }

    @Override
    public List<User> FindByMultipleOptions(String nickname, String phone) {
        return mapper.FindByMultipleOptions(nickname, phone);
    }
}
