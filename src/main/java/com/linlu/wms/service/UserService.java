package com.linlu.wms.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.linlu.wms.domain.entity.User;
import com.linlu.wms.domain.param.PageParam;
import com.linlu.wms.domain.param.UserParam;
import com.linlu.wms.security.SecurityUserDetail;

/**
 * @author xi
 * @description 针对表【user(用户信息表)】的数据库操作Service
 * @createDate 2024-04-27 22:23:17
 */
public interface UserService extends IService<User> {
    /**
     * 根据用户名查询用户
     *
     * @param userName 用户名
     * @return User
     */
    User getUserByName(String userName);

    /**
     * 新增用户
     *
     * @param userParam 用户信息
     * @return 用户id
     */
    String add(UserParam userParam);

    /**
     * 根据id删除用户
     *
     * @param userId 用户id
     * @return true: 删除成功, false: 删除失败
     */
    int delete(String userId);

    /**
     * 校验用户名是否唯一
     *
     * @param user 用户信息
     * @return true: 不唯一, false: 唯一
     */
    boolean checkUserNameUnique(User user);

    /**
     * 校验电话号码是否唯一
     *
     * @param user 用户信息
     * @return true: 不唯一, false: 唯一
     */
    boolean checkPhoneUnique(User user);

    /**
     * 获取当前登陆的用户
     *
     * @return user
     */
    SecurityUserDetail getCurrentUser();

    /**
     * 检查用户是否有操作权限
     *
     * @param user 用户
     */
    void checkerAllowed(User user);

    /**
     * 更新用户信息
     *
     * @param user 用户信息
     * @return 结果
     */
    int edit(User user);

    /**
     * 查询所有用户
     *
     * @param current 当前页
     * @param size 记录数
     * @return 用户列表
     */
    Page<User> all(Integer current, Integer size);

    /**
     * 根据id查询用户信息
     *
     * @param id 用户id
     * @return user
     */
    User infoById(String id);
}
