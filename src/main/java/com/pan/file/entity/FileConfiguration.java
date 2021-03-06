package com.pan.file.entity;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * 文件配置类，配置上传路径、下载路径、文件名等参数
 */
@Data
public class FileConfiguration implements Serializable {
    @TableId
    private Integer id;
    //指定下载的文件名字
    private String downloadFileName; //eg:file.docx
    //指定生成的zip压缩包名字
    private String downloadZipFileName; //eg:file.zip
    //指定文件上传类型
    private String uploadFileType; //eg:.txt,.docx,.doc 注意：逗号为英文符
    //提示信息
    private String tips; //eg：文件内容为空 ！,文件大小限制1M ！,文件后缀名有误 ！,提交成功！,提交失败，请与工作人员联系 注意：逗号为英文符
    //指定文件上传的位置
    private String uploadFilePath; //eg：windows:D:/uploadFies Linux:/file/uploadFiles
    //指定要下载文件的所在路径
    private String downLoadFilePath; //eg：windows:D:/file Linux:/file
}
