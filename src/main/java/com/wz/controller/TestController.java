package com.wz.controller;

import com.wz.common.JsonData;
import com.wz.exception.ParamException;
import com.wz.exception.PermissionException;
import com.wz.param.TestVO;
import com.wz.util.BeanValidator;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

@Controller
@RequestMapping("/test")
@Slf4j
public class TestController {

    @RequestMapping("/hello.json")
    @ResponseBody
    public JsonData hello(){
        log.info("hello");
        throw  new PermissionException("test exception");
        //return JsonData.success("hello, permissions");
    }

    @RequestMapping("/validate.json")
    @ResponseBody
    public JsonData validate(TestVO vo) throws ParamException{
        log.info("validate");
        /*
        try{
            Map<String, String> map = BeanValidator.validateObject(vo);
            if (MapUtils.isNotEmpty(map)){
                for (Map.Entry<String, String> entry : map.entrySet()) {
                    log.info("{}->{}", entry.getKey(), entry.getValue());
                }
            }
        }catch (Exception e){

        }
        */

        BeanValidator.check(vo);

        return  JsonData.success("test valdiate");
    }

}
