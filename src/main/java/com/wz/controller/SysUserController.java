package com.wz.controller;

import com.wz.beans.PageQuery;
import com.wz.beans.PageResult;
import com.wz.common.JsonData;
import com.wz.model.SysUser;
import com.wz.param.DeptParam;
import com.wz.param.UserParam;
import com.wz.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

@Controller
@RequestMapping("/sys/user")
public class SysUserController {

    @Resource
    private SysUserService sysUserService;

    /**
     * 保存user
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(UserParam param){

        sysUserService.save(param);
        return JsonData.success();

    }

    /**
     * 更新user
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(UserParam param){

        sysUserService.update(param);
        return JsonData.success();

    }

    /**
     * 分页获取用户列表
     * @param deptId
     * @param pageQuery
     * @return
     */
    @RequestMapping("/page.json")
    @ResponseBody
    public JsonData page(@RequestParam("deptId") int deptId, PageQuery pageQuery)
    {
        PageResult<SysUser> result = sysUserService.getPageByDeptId(deptId, pageQuery);
        return JsonData.success(result);
    }

}
