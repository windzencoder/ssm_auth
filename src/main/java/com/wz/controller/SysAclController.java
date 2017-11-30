package com.wz.controller;

import com.google.common.collect.Maps;
import com.wz.beans.PageQuery;
import com.wz.common.JsonData;
import com.wz.model.SysRole;
import com.wz.param.AclParam;
import com.wz.service.SysAclService;
import com.wz.service.SysRoleAclService;
import com.wz.service.SysRoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * 权限点控制器
 */

@Controller
@RequestMapping("/sys/acl")
@Slf4j
public class SysAclController {

    @Resource
    private SysAclService sysAclService;

    @Resource
    private SysRoleService sysRoleService;

    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclParam param) {
        sysAclService.save(param);
        return JsonData.success();
    }

    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclParam param) {
        sysAclService.update(param);
        return JsonData.success();
    }

    /**
     * 根据权限模块id获取权限列表
     * @param aclModuleId
     * @param pageQuery
     * @return
     */
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData list(@RequestParam("aclModuleId") Integer aclModuleId, PageQuery pageQuery) {
        return JsonData.success(sysAclService.getPageByAclModuleId(aclModuleId, pageQuery));
    }

    @RequestMapping("/acls.json")
    @ResponseBody
    public JsonData acls(@RequestParam("aclId") int aclId)
    {
        Map<String, Object> map = Maps.newHashMap();

        List<SysRole> roleList = sysRoleService.getRoleListByAclId(aclId);

        map.put("roles", roleList);//权限已分配的角色
        map.put("users", sysRoleService.getUserListByRoleList(roleList));//权限已分配的用户

        return JsonData.success(map);
    }


}
