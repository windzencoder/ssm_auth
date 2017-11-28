package com.wz.controller;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.wz.common.JsonData;
import com.wz.model.SysRole;
import com.wz.model.SysUser;
import com.wz.param.RoleParam;
import com.wz.service.SysRoleAclService;
import com.wz.service.SysRoleService;
import com.wz.service.SysTreeService;
import com.wz.service.SysUserService;
import com.wz.util.StringUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/sys/role")
public class SysRoleController {

    @Resource
    private SysRoleService sysRoleService;

    @Resource
    private SysTreeService sysTreeService;

    @Resource
    private SysRoleAclService sysRoleAclService;

//    @Resource
//    private SysRoleUserService sysRoleUserService;
//    @Resource
//    private SysUserService sysUserService;

    /**
     * 角色页面
     * @return
     */
    @RequestMapping("role.page")
    public ModelAndView page() {
        return new ModelAndView("role");
    }

    /**
     * 保存角色
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveRole(RoleParam param) {
        sysRoleService.save(param);
        return JsonData.success();
    }

    /**
     * 更新角色
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateRole(RoleParam param) {
        sysRoleService.update(param);
        return JsonData.success();
    }

    /**
     * 角色列表
     * @return
     */
    @RequestMapping("/list.json")
    @ResponseBody
    public JsonData list() {
        return JsonData.success(sysRoleService.getAll());
    }

    /**
     * 角色权限树
     * @param roleId
     * @return
     */
    @RequestMapping("/roleTree.json")
    @ResponseBody
    public JsonData roleTree(@RequestParam("roleId") int roleId) {
        return JsonData.success(sysTreeService.roleTree(roleId));
    }

    /**
     * 修改角色与权限的关系
     * @param roleId
     * @param aclIds
     * @return
     */
    @RequestMapping("/changeAcls.json")
    @ResponseBody
    public JsonData changeAcls(@RequestParam("roleId") int roleId, @RequestParam(value = "aclIds", required = false, defaultValue = "") String aclIds) {
        List<Integer> aclIdList = StringUtil.splitToListInt(aclIds);
        sysRoleAclService.changeRoleAcls(roleId, aclIdList);
        return JsonData.success();
    }
//
//    @RequestMapping("/changeUsers.json")
//    @ResponseBody
//    public JsonData changeUsers(@RequestParam("roleId") int roleId, @RequestParam(value = "userIds", required = false, defaultValue = "") String userIds) {
//        List<Integer> userIdList = StringUtil.splitToListInt(userIds);
//        sysRoleUserService.changeRoleUsers(roleId, userIdList);
//        return JsonData.success();
//    }
//
//    @RequestMapping("/users.json")
//    @ResponseBody
//    public JsonData users(@RequestParam("roleId") int roleId) {
//        List<SysUser> selectedUserList = sysRoleUserService.getListByRoleId(roleId);
//        List<SysUser> allUserList = sysUserService.getAll();
//        List<SysUser> unselectedUserList = Lists.newArrayList();
//
//        Set<Integer> selectedUserIdSet = selectedUserList.stream().map(sysUser -> sysUser.getId()).collect(Collectors.toSet());
//        for(SysUser sysUser : allUserList) {
//            if (sysUser.getStatus() == 1 && !selectedUserIdSet.contains(sysUser.getId())) {
//                unselectedUserList.add(sysUser);
//            }
//        }
//        // selectedUserList = selectedUserList.stream().filter(sysUser -> sysUser.getStatus() != 1).collect(Collectors.toList());
//        Map<String, List<SysUser>> map = Maps.newHashMap();
//        map.put("selected", selectedUserList);
//        map.put("unselected", unselectedUserList);
//        return JsonData.success(map);
//    }
}
