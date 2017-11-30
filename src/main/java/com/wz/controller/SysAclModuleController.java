package com.wz.controller;

import com.wz.common.JsonData;
import com.wz.dto.AclModuleLevelDto;
import com.wz.dto.DeptLevelDto;
import com.wz.param.AclModuleParam;
import com.wz.service.SysAclModuleService;
import com.wz.service.SysTreeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

/**
 * 权限模块控制器
 */

@Controller
@RequestMapping("/sys/aclModule")
@Slf4j
public class SysAclModuleController {

    @Resource
    private SysAclModuleService sysAclModuleService;

    @Resource
    private SysTreeService sysTreeService;

    /**
     * 返回页面
     * @return
     */
    @RequestMapping("/acl.page")
    public ModelAndView page(){
        return new ModelAndView("acl");
    }

    /**
     * 保存权限模块
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveAclModule(AclModuleParam param){
        sysAclModuleService.save(param);
        return JsonData.success();
    }

    /**
     * 更新权限模块
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateAclModule(AclModuleParam param){
        sysAclModuleService.update(param);
        return JsonData.success();
    }

    /**
     * 获取权限模块tree
     * @return
     */
    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        List<AclModuleLevelDto> aclModuleList = sysTreeService.aclModuleTree();
        return JsonData.success(aclModuleList);
    }


    /**
     * 删除权限模块
     * @param id
     * @return
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData delete(@RequestParam("id") int id){
        sysAclModuleService.delete(id);
        return JsonData.success();

    }


}
