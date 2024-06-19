package com.monitor.master.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.monitor.master.domain.entity.UserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Repository
@Mapper
public interface UserMapper extends BaseMapper<UserEntity> {

}
