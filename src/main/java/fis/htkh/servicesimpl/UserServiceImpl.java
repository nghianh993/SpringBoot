package fis.htkh.servicesimpl;

import fis.htkh.entities.Organization;
import fis.htkh.entities.Permission;
import fis.htkh.entities.Role;
import fis.htkh.entities.User;
import fis.htkh.models.UserModel;
import fis.htkh.repositories.UsersRepository;
import fis.htkh.services.IOrganizationService;
import fis.htkh.services.IRoleService;
import fis.htkh.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    UsersRepository usersRepository;

    @Autowired
    IRoleService roleService;

    @Autowired
    IOrganizationService organizationService;

    @Override
    public Page<User> getListUser(int pageIndex, int pageSize, String key, Integer desending) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        return usersRepository.findAllAccount(key, pageable);
    }

    @Override
    public List<String> getListPermissonByUserEMail(String email) {
        User userItem = usersRepository.findByEmail(email);
        if(userItem == null) {
            return null;
        }
        List<Role> lstRole = new ArrayList<Role>(userItem.getRoles());
        List<String> lstPermission = new ArrayList<String>();
        for (Role role : lstRole) {
            if(role.getPermissions() != null && role.getPermissions().size() > 0) {
                for (Permission permission : role.getPermissions()) {
                    lstPermission.add(permission.getLink());
                }
            }
        }
        return lstPermission;
    }

    @Override
    public List<User> getAllData() {
        return usersRepository.findAll();
    }

    @Override
    public Set<User> findByInventoryIdIn(List<Long> ids) {
        return usersRepository.findByInventoryIdIn(ids);
    }

    @Override
    public boolean checkExistEmail(String email) {
        User user = usersRepository.findByEmail(email);
        if(user != null)
            return false;
        return true;
    }

    @Override
    public boolean checkExistUsername(String username) {
        User user = usersRepository.findByUsername(username);
        if(user != null)
            return false;
        return true;
    }

    @Override
    public boolean createUser(UserModel model) {
        Organization organization = organizationService.GetDetailById(model.getOrganizationid());
        List<Role> lstRole = roleService.getListRoleByListId(model.getLstRole());
        if(organization == null)
            return false;
        Calendar cal = Calendar.getInstance();
        User user = new User();
        user.setEmail(model.getEmail());
        user.setPassword(new BCryptPasswordEncoder().encode(model.getPassword()));
        user.setFullname(model.getFullname());
        user.setAddress(model.getAddress());
        user.setPhone(model.getPhone());
        user.setIslock(model.isIslock());
        user.setDatecreate(cal.getTime());
        user.setOrganization(organization);
        user.setDob(model.getDob());
        user.setRoles(lstRole);
        user.setUsername(model.getUsername());

        usersRepository.save(user);

        return true;
    }

    @Override
    public boolean upateUser(UserModel model) {
        Organization organization = organizationService.GetDetailById(model.getOrganizationid());
        List<Role> lstRole = roleService.getListRoleByListId(model.getLstRole());
        if(organization != null) {
            Optional<User> userop = usersRepository.findById(model.getId());
            if(userop.isPresent()) {
                User user = userop.get();
                Calendar cal = Calendar.getInstance();
                user.setAddress(model.getAddress());
                user.setFullname(model.getFullname());
                user.setAddress(model.getAddress());
                user.setPhone(model.getPhone());
                user.setIslock(model.isIslock());
                user.setOrganization(organization);
                user.setDob(model.getDob());
                user.setRoles(lstRole);
                usersRepository.save(user);
                return true;
            }
        }

        return false;
    }

    @Override
    public boolean lockOrUnlockAccount(List<Long> ids, boolean islock) {
        try {
            Set<User> userop = usersRepository.findByInventoryIdIn(ids);
            for (User user : userop) {
                user.setIslock(islock);
                usersRepository.save(user);
            }
            return true;
        }catch (Exception ex) {
            return false;
        }
    }

    @Override
    public boolean deleteUser(List<Long> ids) {
        Set<User> lstUser = usersRepository.findByInventoryIdIn(ids);
        if(lstUser != null && !lstUser.isEmpty()) {
            for (User user : lstUser) {
                usersRepository.delete(user);
            }
            return true;
        }
        return false;
    }

    @Override
    public UserModel getDetailById(Long id) {
        Optional<User> userop = usersRepository.findById(id);
        if(userop.isPresent())
        {
            User user = userop.get();
            UserModel model = new UserModel();

            model.setUsername(user.getUsername());
            model.setDatemodify(user.getDatemodify());
            model.setEmail(user.getEmail());
            model.setFullname(user.getFullname());
            model.setAddress(user.getAddress());
            model.setPhone(user.getPhone());
            model.setIslock(user.getIslock());
            model.setOrganizationid(user.getOrganization().getId());
            model.setDob(user.getDob());
            model.setLstRole(user.getRoles().stream().map(Role::getId).collect(Collectors.toList()));
            return model;
        }
        return null;
    }
}
