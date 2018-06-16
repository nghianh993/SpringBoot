package fis.htkh.services;

import fis.htkh.entities.User;
import fis.htkh.models.UserModel;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface IUserService {
    Page<User> getListUser(int pageIndex, int pageSize, String key, Integer desending);

    List<User> getAllData();

    List<String> getListPermissonByUserEMail(String email);

    Set<User> findByInventoryIdIn(List<Long> ids);

    boolean checkExistEmail(String email);

    boolean checkExistUsername(String username);

    boolean createUser(UserModel model);

    boolean upateUser(UserModel model);

    boolean lockOrUnlockAccount(List<Long> ids, boolean islock);

    boolean deleteUser(List<Long> ids);

    UserModel getDetailById(Long id);
}
