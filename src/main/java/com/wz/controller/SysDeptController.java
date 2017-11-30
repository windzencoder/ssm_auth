package com.wz.controller;

import com.wz.common.JsonData;
import com.wz.dto.DeptLevelDto;
import com.wz.param.DeptParam;
import com.wz.service.SysDeptService;
import com.wz.service.SysTreeService;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.List;

@Controller
@RequestMapping("/sys/dept")
public class SysDeptController {

    @Resource
    private SysDeptService sysDeptService;

    @Resource
    private SysTreeService sysTreeService;

    /**
     * 进入页面
     * @return
     */
    @RequestMapping("/dept.page")
    public ModelAndView page(){
        return new ModelAndView("dept");
    }


    /**
     * 保存部门
     * @param param
     * @return
     */
    @RequestMapping("/save.json")
    @ResponseBody
    public JsonData saveDept(DeptParam param){

        sysDeptService.save(param);
        return JsonData.success();

    }

    /**
     * 获取部门tree
     * @return
     */
    @RequestMapping("/tree.json")
    @ResponseBody
    public JsonData tree(){
        List<DeptLevelDto> dtoList = sysTreeService.deptTree();
        return JsonData.success(dtoList);
    }

    /**
     * 更新部门
     * @param param
     * @return
     */
    @RequestMapping("/update.json")
    @ResponseBody
    public JsonData updateDept(DeptParam param){

        sysDeptService.update(param);
        return JsonData.success();

    }

    /**
     * 删除部门
     * @param id
     * @return
     */
    @RequestMapping("/delete.json")
    @ResponseBody
    public JsonData deleteDept(@RequestParam("id") int id){
        sysDeptService.delete(id);
        return JsonData.success();
    }

}
