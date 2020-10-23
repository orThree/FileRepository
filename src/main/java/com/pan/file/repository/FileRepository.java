package com.pan.file.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pan.file.entity.Files;
import org.springframework.stereotype.Repository;

@Repository
public interface FileRepository extends BaseMapper<Files>{
}
