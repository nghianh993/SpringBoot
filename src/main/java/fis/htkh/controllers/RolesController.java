package fis.htkh.controllers;

import fis.htkh.entities.GroupPermission;
import fis.htkh.entities.Role;
import fis.htkh.models.AjaxResult;
import fis.htkh.models.AjaxResultModel;
import fis.htkh.models.PagingModel;
import fis.htkh.models.RoleModel;
import fis.htkh.services.IGroupPermissionService;
import fis.htkh.services.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
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
public class RolesController {

    @Autowired
    IRoleService roleService;

    @Autowired
    IGroupPermissionService rolePermissionService;

    @Value("${webconfig.backend.pageSize}")
    private Integer pageSize;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = {"/admin/roles"}, method = RequestMethod.GET)
    public String Group(ModelMap model) {
        List<GroupPermission> lstDataPer = rolePermissionService.getListPermission(0L);
        Page<Role> pgRole = roleService.getListRole(0, pageSize, "", 0);
        List<Role> lstRole = pgRole.getContent();
        model.addAttribute("lstRole", lstRole);
        model.addAttribute("lstDataPer", lstDataPer);
        model.addAttribute("total", pgRole.getTotalElements());
        model.addAttribute("totalPage", pgRole.getTotalPages());
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("currentpage", 1);
        model.addAttribute("active", "roles");
        return "views/backend/roles";
    }

    @RequestMapping(value = { "api/admin/roles/loaddata" }, method = RequestMethod.POST)
    public ModelAndView GetListLocation(@ModelAttribute PagingModel model) {
        Page<Role> pgRole = roleService.getListRole(model.getPageIndex() -1, model.getPageSize(), model.getKey(), model.getDesending());
        List<Role> lstRole = pgRole.getContent();
        ModelAndView mav = new ModelAndView("views/backend/ajaxview/pagging_role");
        mav.addObject("lstRole", lstRole);
        mav.addObject("total", pgRole.getTotalElements());
        mav.addObject("totalPage", pgRole.getTotalPages());
        mav.addObject("pageSize", pageSize);
        mav.addObject("currentpage", model.getPageIndex());
        return mav;
    }

    @RequestMapping(value = {"api/admin/roles/detail"}, method = RequestMethod.POST)
    public @ResponseBody
    AjaxResultModel<RoleModel> GetActionDetail(Long id) {
        AjaxResultModel<RoleModel> result = new AjaxResultModel<RoleModel>();
        try {
            RoleModel model = roleService.getRoleDetail(id);
            result.setResult(true);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
            result.setResultData(model);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = {"api/admin/roles/edit"}, method = RequestMethod.POST)
    public @ResponseBody
    AjaxResult EditGroup(@ModelAttribute RoleModel model) {
        AjaxResult result = new AjaxResult();
        try {
            boolean check = roleService.editRole(model);
            setResultValue(result, check);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = {"api/admin/roles/add"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResult AddGroup(@ModelAttribute RoleModel model) {
        AjaxResult result = new AjaxResult();
        try {
            boolean check = roleService.addRole(model);
            setResultValue(result, check);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = {"api/admin/roles/delete"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResult DeleteGroup(long id) {
        AjaxResult result = new AjaxResult();
        try {
            boolean check = roleService.deleteRole(id);
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

    /*Help Method*/
    private void setResultValue(AjaxResult result, boolean check) {
        if(check) {
            result.setCode(1);
            result.setResult(true);
        }else {
            result.setResult(false);
            result.setCode(0);
        }
    }
}
