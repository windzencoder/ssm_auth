package com.wz.service;

import com.google.common.base.Preconditions;
import com.wz.beans.PageQuery;
import com.wz.beans.PageResult;
import com.wz.common.RequestHolder;
import com.wz.dao.SysUserMapper;
import com.wz.exception.ParamException;
import com.wz.model.SysUser;
import com.wz.param.UserParam;
import com.wz.util.BeanValidator;
import com.wz.util.IpUtil;
import com.wz.util.MD5Util;
import com.wz.util.PasswordUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysUserService {

    @Resource
    private SysUserMapper sysUserMapper;


    /**
     * 保存用户
     * @param param
     */
    public void save(UserParam param){
        BeanValidator.check(param);
        if (checkTelephoneExist(param.getTelephone(),param.getId())){
            throw new ParamException("电话已被占用");
        }
        if (checkEmailExist(param.getMail(),param.getId())){
            throw new ParamException("邮箱已被占用");
        }
        String password = PasswordUtil.randomPassword();
        //TODO:
        password = "12345678";
        String encryptedPassword = MD5Util.encrypt(password);

        SysUser user = SysUser.builder().username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .password(encryptedPassword).deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();

        user.setOperator(RequestHolder.getCurrentUser().getUsername());
        user.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        user.setOperateTime(new Date());

        // TODO: sendEmail



        sysUserMapper.insertSelective(user);

    }

    /**
     * 在后台更新用户
     * @param param
     */
    public void update(UserParam param) {
        BeanValidator.check(param);
        if(checkTelephoneExist(param.getTelephone(), param.getId())) {
            throw new ParamException("电话已被占用");
        }
        if(checkEmailExist(param.getMail(), param.getId())) {
            throw new ParamException("邮箱已被占用");
        }
        SysUser before = sysUserMapper.selectByPrimaryKey(param.getId());//更新之前的用户
        Preconditions.checkNotNull(before, "待更新的用户不存在");
        //更新后的user
        SysUser after = SysUser.builder().id(param.getId()).username(param.getUsername()).telephone(param.getTelephone()).mail(param.getMail())
                .deptId(param.getDeptId()).status(param.getStatus()).remark(param.getRemark()).build();

        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        sysUserMapper.updateByPrimaryKeySelective(after);

    }


    /**
     * 检查邮箱是否存在
     * @param email
     * @param userId
     * @return
     */
    public boolean checkEmailExist(String email, Integer userId){
        return  sysUserMapper.countByMail(email, userId) > 0;
    }

    /**
     * 检查电话是否存在
     * @param telephone
     * @param userId
     * @return
     */
    public boolean checkTelephoneExist(String telephone,Integer userId){
        return  sysUserMapper.countByTelephone(telephone, userId) > 0;
    }

    /**
     * 通过mail or telephone 登录
     * @param keyword
     * @return
     */
    public SysUser findByKeyword(String keyword){
        return sysUserMapper.findByKeyword(keyword);
    }


    /**
     * 分页查询获取user
     * @param deptId
     * @param pageQuery
     * @return
     */
    public PageResult<SysUser> getPageByDeptId(int deptId, PageQuery pageQuery){
        BeanValidator.check(pageQuery);
        int count = sysUserMapper.countByDeptId(deptId);
        if (count > 0){
            List<SysUser> list = sysUserMapper.getPageByDeptId(deptId, pageQuery);
            return PageResult.<SysUser>builder().total(count).data(list).build();
        }
        return PageResult.<SysUser>builder().build();
    }

    /**
     * 获取所有用户
     * @return
     */
    public List<SysUser> getAll(){
        return sysUserMapper.getAll();
    }

}
