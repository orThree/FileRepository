package com.pan.file;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.pan.file.entity.FileConfiguration;
import com.pan.file.repository.ConfigurationRepository;
import com.pan.file.repository.FileRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class FileApplicationTests {
	@Autowired
	private ConfigurationRepository configurationRepository;

	@Test
	public void contextLoads() {
		FileConfiguration fileConfiguration = configurationRepository.selectById(1);
		System.out.println(fileConfiguration);
	}

}
