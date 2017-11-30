package com.wz.dao;

import com.wz.model.SysAclModule;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysAclModuleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(SysAclModule record);

    int insertSelective(SysAclModule record);

    SysAclModule selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SysAclModule record);

    int updateByPrimaryKey(SysAclModule record);

    //是否已存在同名的权限模块
    int countByNameAndParentId(@Param("parentId") Integer parentId, @Param("name") String name, @Param("id") Integer id);

    //通过level获取子权限模块
    List<SysAclModule> getChildAclModuleListByLevel(@Param("level") String level);

    //批量更新权限模块
    void batchUpdateLevel(@Param("sysAclModuleList") List<SysAclModule> sysAclModuleList);

    //获取所有权限模块
    List<SysAclModule> getAllAclModule();

    int countByParentId(@Param("aclModuleId") Integer aclModuleId);
}