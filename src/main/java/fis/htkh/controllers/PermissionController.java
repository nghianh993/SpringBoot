package fis.htkh.controllers;

import fis.htkh.entities.GroupPermission;
import fis.htkh.entities.Permission;
import fis.htkh.models.AjaxResult;
import fis.htkh.models.AjaxResultModel;
import fis.htkh.models.GroupPermissionModel;
import fis.htkh.models.PermissionModel;
import fis.htkh.services.IGroupPermissionService;
import fis.htkh.services.IPermissionService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Controller
public class PermissionController {
    @Autowired
    IGroupPermissionService groupPermissionService;

    @Autowired
    IPermissionService permissionService;

    @Autowired
    private MessageSource messageSource;

    @RequestMapping(value = {"/admin/permission"}, method = RequestMethod.GET)
    public String Account(ModelMap model) {
        List<GroupPermissionModel> lstData = getListPermission(0L);

        model.addAttribute("lstData", lstData);
        model.addAttribute("active", "permission");
        return "views/backend/permission";
    }

    @RequestMapping(value = { "api/admin/permission/loadgroup" }, method = RequestMethod.POST)
    public ModelAndView GetListLocation() {
        List<GroupPermissionModel> lstData = getListPermission(0L);
        ModelAndView mav = new ModelAndView("views/backend/ajaxview/group_permission");
        mav.addObject("lstData", lstData);
        return mav;
    }

    @RequestMapping(value = {"api/admin/permission/addgroup"}, method = RequestMethod.POST)
    public @ResponseBody
    AjaxResult AddGroup(String name, Long parentId) {
        AjaxResult result = new AjaxResult();
        try {
            groupPermissionService.addGroup(name, parentId);
            result.setResult(true);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = {"api/admin/permission/deletegroup"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResult deleteGroup(Long id) {
        AjaxResult result = new AjaxResult();
        try {
            boolean check = groupPermissionService.deleteGroup(id);
            if(check) {
                result.setCode(1);

            }else {
                result.setCode(0);
            }
            result.setResult(true);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = {"api/admin/permission/add"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResult AddPermission(@ModelAttribute PermissionModel model) {
        AjaxResult result = new AjaxResult();
        try {
            boolean check = permissionService.addPermission(model);
            if(check) {
                result.setCode(1);

            }else {
                result.setCode(0);
            }
            result.setResult(true);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = {"api/admin/permission/groupdetail"}, method = RequestMethod.POST)
    public @ResponseBody
    AjaxResultModel<GroupPermissionModel> GetGroupDetail(Long id) {
        AjaxResultModel<GroupPermissionModel> result = new AjaxResultModel<GroupPermissionModel>();
        try {
            GroupPermissionModel groupPermissionModel = new GroupPermissionModel();
            GroupPermission groupPermission = groupPermissionService.getDetailGroup(id);
            groupPermissionModel.setId(groupPermission.getId());
            groupPermissionModel.setName(groupPermission.getName());
            groupPermissionModel.setParentId(groupPermission.getParentid());
            result.setResult(true);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
            result.setResultData(groupPermissionModel);
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = {"api/admin/permission/updategroup"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResult UpdateGroup(@ModelAttribute GroupPermissionModel model) {
        AjaxResult result = new AjaxResult();
        try {
            GroupPermission groupPermission = new GroupPermission();
            BeanUtils.copyProperties(model, groupPermission);
            boolean check = groupPermissionService.updateGroup(groupPermission);
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


    @RequestMapping(value = {"api/admin/getpermission"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResult GetPermissionById(long id) {
        AjaxResult result = new AjaxResult();
        try {
            Optional<Permission> permission = permissionService.getPermissionById(id);
            if(permission != null) {
                result.setCode(1);
            }else {
                result.setCode(0);
            }
            result.setResult(true);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = {"api/admin/permission/delete"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResult DeletePermission(long id) {
        AjaxResult result = new AjaxResult();
        try {
            boolean check = permissionService.deletePermission(id);
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

    @RequestMapping(value = {"api/admin/permission/lock"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResult LockPermission(long id) {
        AjaxResult result = new AjaxResult();
        try {
            if(permissionService.unLockOrLockPermission(id, true)) {
                result.setCode(1);
            }else {
                result.setCode(0);
            }
            result.setResult(true);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = {"api/admin/permission/unlock"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResult UnLockPermission(long id) {
        AjaxResult result = new AjaxResult();
        try {
            if(permissionService.unLockOrLockPermission(id, false)) {
                result.setCode(1);
            }else {
                result.setCode(0);
            }
            result.setResult(true);
            result.setMessage(messageSource.getMessage("S001", null, Locale.getDefault()));
        } catch (Exception e) {
            e.printStackTrace();
            result.setResult(false);
            result.setMessage(messageSource.getMessage("E002", null, Locale.getDefault()));
            return result;
        }
        return result;
    }

    @RequestMapping(value = {"api/admin/permission/detail"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResultModel<PermissionModel> GetActionDetail(Long id) {
        AjaxResultModel<PermissionModel> result = new AjaxResultModel<PermissionModel>();
        try {
            PermissionModel model = permissionService.getPermissionDetail(id);
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

    @RequestMapping(value = {"api/admin/permission/edit"}, method = RequestMethod.POST)
    public @ResponseBody AjaxResult EditAction(@ModelAttribute PermissionModel model) {
        AjaxResult result = new AjaxResult();
        try {
            boolean check = permissionService.editPermission(model);
            if(check)
                result.setCode(1);
            else
                result.setCode(0);
            result.setResult(true);
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
    /*Lấy danh sách nhóm quyền*/
    public List<GroupPermissionModel> getListPermission(Long parentId) {
        List<GroupPermission> groupPermissions = groupPermissionService.getListPermission(parentId);
        List<GroupPermissionModel> lstData = new ArrayList<GroupPermissionModel>();
        for (GroupPermission groupPermission : groupPermissions) {
            GroupPermissionModel model = new GroupPermissionModel();
            //BeanUtils.copyProperties(groupPermission, model);
            model.setId(groupPermission.getId());
            model.setName(groupPermission.getName());
            model.setParentId(groupPermission.getParentid());
            model.setPermissions(groupPermission.getPermissions());
            model.setLstGroupPermissionModel(getListPermission(groupPermission.getId()));
            lstData.add(model);
        }
        return lstData;
    }
}
