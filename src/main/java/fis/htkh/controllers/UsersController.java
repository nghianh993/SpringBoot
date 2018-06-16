package fis.htkh.controllers;

import fis.htkh.entities.Role;
import fis.htkh.entities.User;
import fis.htkh.models.*;
import fis.htkh.services.IOrganizationService;
import fis.htkh.services.IRoleService;
import fis.htkh.services.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.core.env.Environment;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Locale;

@Controller
public class UsersController {
    @Autowired
    IUserService usersService;

    @Autowired
    IRoleService roleService;

    @Autowired
    IOrganizationService organizationService;

    @Autowired
    private MessageSource messageSource;

    @Value("${webconfig.backend.pageSize}")
    private Integer pageSize;

    @RequestMapping(value = {"/admin/users"}, method = RequestMethod.GET)
    public String userList(ModelMap model) {
        Page<User> pgUser = usersService.getListUser(0, pageSize, "", 0);
        List<User> lstUser = pgUser.getContent();
        List<Role> lstRole = roleService.getListRole();
        List<OrganizationModel> lstOrganizationModels = organizationService.ListOrganization();

        model.addAttribute("lstUser", lstUser);
        model.addAttribute("lstOrganizationModels", lstOrganizationModels);
        model.addAttribute("lstRole", lstRole);
        model.addAttribute("total", pgUser.getTotalElements());
        model.addAttribute("totalPage", pgUser.getTotalPages());
        model.addAttribute("currentpage", 1);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("active", "users");
        return "views/backend/users";
    }

    @RequestMapping(value = { "api/admin/users/loaddata" }, method = RequestMethod.POST)
    public ModelAndView getListLocation(@ModelAttribute PagingModel model) {

        Page<User> pgUser = usersService.getListUser(model.getPageIndex() -1, model.getPageSize(), model.getKey(), model.getDesending());
        List<User> lstUser = pgUser.getContent();
        ModelAndView mav = new ModelAndView("views/backend/ajaxview/pagging_users");
        mav.addObject("lstUser", lstUser);
        mav.addObject("total", pgUser.getTotalElements());
        mav.addObject("totalPage", pgUser.getTotalPages());
        mav.addObject("currentpage", model.getPageIndex());
        mav.addObject("pageSize", pageSize);
        return mav;
    }

    @RequestMapping(value = "/api/admin/users/add", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResult creatUser(@ModelAttribute UserModel model) {
        AjaxResult result = new AjaxResult();
        try {
            boolean check = usersService.createUser(model);
            result.setResult(check);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/api/admin/users/edit", method = RequestMethod.POST)
    public @ResponseBody AjaxResult updateUser(@ModelAttribute UserModel model) {
        AjaxResult result = new AjaxResult();
        try {
            boolean check = usersService.upateUser(model);
            result.setResult(check);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/api/admin/users/delete", method = RequestMethod.POST)
    public @ResponseBody AjaxResult deleteUser(@ModelAttribute UserModel model) {
        AjaxResult result = new AjaxResult();
        try {
            boolean check = usersService.deleteUser(model.getIds());
            result.setResult(check);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/api/admin/users/lockuser", method = RequestMethod.POST)
    public @ResponseBody AjaxResult lockUser(@ModelAttribute UserModel model) {
        AjaxResult result = new AjaxResult();
        try {
            boolean check = usersService.lockOrUnlockAccount(model.getIds(), true);
            result.setResult(check);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/api/admin/users/unlockuser", method = RequestMethod.POST)
    public @ResponseBody AjaxResult unlockUser(@ModelAttribute UserModel model) {
        AjaxResult result = new AjaxResult();
        try {
            boolean check = usersService.lockOrUnlockAccount(model.getIds(), false);
            result.setResult(check);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = "/api/admin/users/getdetail", method = RequestMethod.POST)
    public @ResponseBody
    AjaxResultModel<UserModel> getDetail(@ModelAttribute UserModel model) {
        AjaxResultModel<UserModel> result = new AjaxResultModel<UserModel>();
        try {
            UserModel user = usersService.getDetailById(model.getId());
            result.setResult(true);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
            result.setResultData(user);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = {"api/admin/users/checkemail"}, method = RequestMethod.POST)
    public @ResponseBody boolean checkEmail(String email) {
        try {
            boolean check = usersService.checkExistEmail(email);
            return check;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping(value = {"api/admin/users/checkusername"}, method = RequestMethod.POST)
    public @ResponseBody boolean checkUsername(String username) {
        try {
            boolean check = usersService.checkExistUsername(username);
            return check;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
