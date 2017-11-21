package com.wz.controller;

import com.wz.common.JsonData;
import com.wz.param.DeptParam;
import com.wz.param.UserParam;
import com.wz.service.SysUserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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


}
