package com.pan.file;

import com.pan.file.repository.ConfigurationRepository;
import com.pan.file.utils.FileProperties;
import com.pan.file.utils.FileUtil;
import com.pan.file.utils.OflFileBean;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class Test1 {
    @Autowired
    private ConfigurationRepository configurationRepository;

    /**
     * deCompress测试解压缩多个文件
     */
    @Test
    public void test4() {
        String zipPath = "D:\\Charlin_project\\picture\\demo.zip";
        String basePath = "D:\\Charlin_project\\picture\\unZip";
        try {
            FileUtil.deCompress(zipPath,basePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * unpackOne解压缩一个文件
     */
    @Test
    public void test3() {
        File zipFile = new File("D:\\Charlin_project\\picture\\demo.zip");
        String basePath = "D:\\Charlin_project\\picture\\unZip";
        FileUtil.unpackOne(zipFile,basePath);
    }

    /**
     * zipFile压缩多个文件
     */
    @Test
    public void test2(){
        File file0 = new File("D:\\Charlin_project\\picture\\cry.png");
        File file1 = new File("D:\\Charlin_project\\picture\\hey.png");
        File file2 = new File("D:\\Charlin_project\\picture\\out.png");
        List<File> files = new ArrayList<>();
        files.add(file0);
        files.add(file1);
        files.add(file2);
        String zipPath = "D:\\Charlin_project\\picture\\demo.zip";
        FileUtil.zipFile(files,zipPath);
    }

    /**
     * split字符串切割
     */
    @Test
    public void test1() {
        String s = "文件内容为空 ！,文件大小限制1M ！,文件后缀名有误 ！,提交成功！,提交失败，请与工作人员联系";
        for (String s1 : s.split(",")) {
            System.out.println(s1);
        }
    }
}
