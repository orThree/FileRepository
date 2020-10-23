package com.pan.file.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.pan.file.entity.Member;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberRepository extends BaseMapper<Member> {
}
