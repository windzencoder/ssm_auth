package com.wz.dao;

import com.wz.beans.PageQuery;
import com.wz.model.SysUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysUser record);

    int insertSelective(SysUser record);

    SysUser selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysUser record);

    int updateByPrimaryKey(SysUser record);

    /**
     * 通过keyword来查询用户，即email或者telephone
     * @param keyword
     * @return
     */
    SysUser findByKeyword(@Param("keyword") String keyword);

    /**
     * 是否存在mail的用户
     * @param mail
     * @param id
     * @return
     */
    int countByMail(@Param("mail") String mail, @Param("id") Integer id);

    /**
     * 是否存在telephone的用户
     * @param telephone
     * @param id
     * @return
     */
    int countByTelephone(@Param("telephone") String telephone, @Param("id") Integer id);

    /**
     * 通过部门id查询user数量
     * @param deptId
     * @return
     */
    int countByDeptId(@Param("deptId") int deptId);

    /**
     * 分页获取user
     * @param deptId
     * @param page
     * @return
     */
    List<SysUser> getPageByDeptId(@Param("deptId") int deptId, @Param("page")PageQuery page);


    /**
     * 用户id列表获取用户
     * @param idList
     * @return
     */
    List<SysUser> getByIdList(@Param("idList") List<Integer> idList);

    /**
     * 获取所有用户
     * @return
     */
    List<SysUser> getAll();

}