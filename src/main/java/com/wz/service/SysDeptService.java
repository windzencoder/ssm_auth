package com.wz.service;

import com.google.common.base.Preconditions;
import com.wz.common.RequestHolder;
import com.wz.dao.SysDeptMapper;
import com.wz.dao.SysUserMapper;
import com.wz.exception.ParamException;
import com.wz.model.SysDept;
import com.wz.param.DeptParam;
import com.wz.util.BeanValidator;
import com.wz.util.IpUtil;
import com.wz.util.LevelUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class SysDeptService {

    @Resource
    private SysDeptMapper sysDeptMapper;

    @Resource
    private SysUserMapper sysUserMapper;

    @Resource
    private SysLogService sysLogService;

    /**
     * 保存部门
     * @param param
     */
    public void save(DeptParam param){
        BeanValidator.check(param);
        if(checkExist(param.getParentId(), param.getName(), param.getId())){
            throw  new ParamException("同一层级下存在相同名称的部门");
        }
        SysDept dept = SysDept.builder().name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();
        /**
         * 设置level层级
         * 获取父id的level+父id
         * 例如 getParentId() 为 1， getParentId() 对应的level为 1.1，则当前level 1.1.1
         */
        dept.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));

        dept.setOperator(RequestHolder.getCurrentUser().getUsername());
        dept.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        dept.setOperateTime(new Date());

        sysDeptMapper.insertSelective(dept);
        sysLogService.saveDeptLog(null, dept);
    }

    /**
     * 更新部门
     * @param param
     */
    public void update(DeptParam param){
        BeanValidator.check(param);
        if(checkExist(param.getParentId(), param.getName(), param.getId())){
            throw  new ParamException("同一层级下存在相同名称的部门");
        }
        SysDept before = sysDeptMapper.selectByPrimaryKey(param.getId());
        Preconditions.checkNotNull(before, "待更新的部门不存在");

        if(checkExist(param.getParentId(), param.getName(), param.getId())) {
            throw new ParamException("同一层级下存在相同名称的部门");
        }

        SysDept after = SysDept.builder().id(param.getId()).name(param.getName()).parentId(param.getParentId())
                .seq(param.getSeq()).remark(param.getRemark()).build();
        after.setLevel(LevelUtil.calculateLevel(getLevel(param.getParentId()), param.getParentId()));

        after.setOperator(RequestHolder.getCurrentUser().getUsername());
        after.setOperateIp(IpUtil.getRemoteIp(RequestHolder.getCurrentRequest()));
        after.setOperateTime(new Date());

        updateWithChild(before, after);
        //记录日志
        sysLogService.saveDeptLog(before, after);

    }

    /**
     * 更新子部门
     * @param before
     * @param after
     */
    @Transactional
    private void updateWithChild(SysDept before, SysDept after) {
        String newLevelPrefix = after.getLevel();
        String oldLevelPrefix = before.getLevel();
        if (!after.getLevel().equals(before.getLevel())) {//是否需要做子部门的更新，检查level是否相同
            //取出level对应的所有的子部门
            List<SysDept> deptList = sysDeptMapper.getChildDeptListByLevel(before.getLevel());
            if (CollectionUtils.isNotEmpty(deptList)) {
                for (SysDept dept : deptList) {
                    String level = dept.getLevel();
                    if (level.indexOf(oldLevelPrefix) == 0) {//以oldLevelPrefix开头
                        level = newLevelPrefix + level.substring(oldLevelPrefix.length());
                        dept.setLevel(level);
                    }
                }
                //批量更新level
                sysDeptMapper.batchUpdateLevel(deptList);
            }
        }
        sysDeptMapper.updateByPrimaryKey(after);
    }


    /**
     * 部门是否已经存在
     * @param parentId
     * @param deptName
     * @param deptId
     * @return
     */
    private boolean checkExist(Integer parentId, String deptName, Integer deptId){
        return sysDeptMapper.countByNameAndParentId(parentId, deptName, deptId) > 0;
    }

    /**
     * 获取层级
     * @param deptId
     * @return
     */
    private String getLevel(Integer deptId){
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        if (dept == null){
            return null;
        }
        return  dept.getLevel();
    }


    /**
     * 删除部门
     * @param deptId
     */
    public void delete(int deptId){
        SysDept dept = sysDeptMapper.selectByPrimaryKey(deptId);
        Preconditions.checkNotNull(dept, "待删除部门不存在，无法删除");

        if(sysDeptMapper.countByParentId(dept.getId()) > 0){
            throw new ParamException("当前部门下有子部门，无法删除");
        }

        if(sysUserMapper.countByDeptId(dept.getId()) > 0){
            throw new ParamException("当前部门下有用户，无法删除");
        }

        //删除
        sysDeptMapper.deleteByPrimaryKey(deptId);

    }

}
