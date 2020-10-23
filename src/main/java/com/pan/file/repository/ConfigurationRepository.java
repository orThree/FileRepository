package com.pan.file.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pan.file.entity.FileConfiguration;
import org.springframework.stereotype.Repository;

@Repository
public interface ConfigurationRepository  extends BaseMapper<FileConfiguration> {
}
