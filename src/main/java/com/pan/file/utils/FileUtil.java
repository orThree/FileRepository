package com.pan.file.utils;

import com.pan.file.entity.Files;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.taskdefs.Expand;
import org.apache.tools.ant.util.FileUtils;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipFile;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Enumeration;
import java.util.List;

public class FileUtil {

    /**
     * （一）上传文件（普通文件）
     */
    public static void uploadFile(String filePath, String fileName, MultipartFile multipartFile) throws Exception {

        // 上传文件
        File file = new File(filePath + fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        multipartFile.transferTo(file);
    }

    /**
     * （一）下载文件（普通文件）
     */
    public static void downloadFile(File file, OutputStream output) {

        FileInputStream fileInput = null;
        BufferedInputStream inputStream = null;
        try {
            fileInput = new FileInputStream(file);
            inputStream = new BufferedInputStream(fileInput);

            byte[] buffer = new byte[8192];//1024*8
            int i;
            while ((i = inputStream.read(buffer)) != -1) {
                output.write(buffer, 0, i);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (fileInput != null)
                    fileInput.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * （二）上传文件并压缩
     */
    public static void uploadFileZip(String filePath, String fileName, MultipartFile multipartFile) throws Exception {

        // 上传文件
        File file = new File(filePath + fileName);
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        multipartFile.transferTo(file);

        // 把上传文件压缩
        FileOutputStream fileOutput;
        ZipOutputStream zipOutput;
        BufferedOutputStream bufferedOutput;
        FileInputStream fileInput = null;
        BufferedInputStream bufferedInput = null;
        try {
            String zipNameStr = fileName.substring(0, fileName.indexOf("."));
            File zipPath = new File(filePath, zipNameStr + ".zip");
            fileOutput = new FileOutputStream(zipPath);//输出流，压缩包所在路径
            bufferedOutput = new BufferedOutputStream(fileOutput);
            zipOutput = new ZipOutputStream(bufferedOutput);

            File filePathDemo = new File(filePath + fileName);//待压缩文件路径
            //压缩条目
            ZipEntry entry = new ZipEntry(fileName);

            //读取待压缩的文件并写进压缩包里
            fileInput = new FileInputStream(filePathDemo);
            bufferedInput = new BufferedInputStream(fileInput);
            zipOutput.putNextEntry(entry);

            //官方API文档推荐大小8192
            byte[] buffer = new byte[8192];
            int num;
            while ((num = bufferedInput.read(buffer)) != -1) {
                zipOutput.write(buffer, 0, num);
            }
            //不能写成  int i = bufferedInput.read(buffer);while(i != -1);否则形成死循环，一直写入

            zipOutput.closeEntry();
            fileInput.close();
            bufferedInput.close();
            zipOutput.close();
            FileUtils.delete(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * （二）解压实现
     *
     * @param srcZipFile 输入的压缩文件
     * @param basePath   解压文件存放的父路径
     */
    public static String unpackOne(File srcZipFile, String basePath) {

        try {
            ZipFile zipFile = new ZipFile(srcZipFile);

            Enumeration entries = zipFile.getEntries();
            //迭代枚举
            while (entries.hasMoreElements()) {

                ZipEntry zipEntry = (ZipEntry) entries.nextElement();
                //输出路径
                String outPath = basePath + File.separatorChar + zipEntry.getName();
                outPath = outPath.replace('\\', '/').replace('/', File.separatorChar);
                //创建父文件(关键),因为可能会出现像这样的情况"D:/文件夹/",导致无法找到父路径
                File parentFile = new File(outPath.substring(0, outPath.lastIndexOf(File.separatorChar)));
                // 判断父路径是否存在,不存在则创建文件路径
                if (!parentFile.exists()) {
                    parentFile.mkdirs();
                }
                // 判断文件全路径是否为文件夹,如果是,不需要解压
                File outFile = new File(outPath);
                if (outFile.isDirectory()) {
                    if (!outFile.exists()) {
                        outFile.mkdirs();
                    }
                    continue;
                }
                //解压
                try (InputStream in = zipFile.getInputStream(zipEntry);
                     BufferedInputStream bis = new BufferedInputStream(in);
                     FileOutputStream fos = new FileOutputStream(outFile);
                     BufferedOutputStream bos = new BufferedOutputStream(fos)) {
                    outputData(bis, bos);
                }
                return outPath;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 把字节输入流数据写给输出流(或内存)
     *
     * @param inputStream  字节输入流
     * @param outputStream 字节输出流
     */
    private static void outputData(InputStream inputStream, OutputStream outputStream) {
        try {

            byte[] bytes = new byte[4096];
            int length;
            while ((length = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes, 0, length);
                outputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * （三）多文件压缩
     * @param files   文件集合
     * @param zipPath Zip包生成地址
     */
    public static void zipFile(List files, String zipPath) {
        //创建文件输出流
        FileOutputStream fileOutputStream = null;
        //创建ZIP文件流
        ZipOutputStream zipOutputStream = null;
        try {
            File fileA = new File(zipPath);
            if (!fileA.exists()) {
                fileA.createNewFile();
            }
            fileOutputStream = new FileOutputStream(fileA);
            zipOutputStream = new ZipOutputStream(fileOutputStream);
            int size = files.size();
            for (int i = 0; i < size; i++) {
                File file = (File) files.get(i);
                zipFile(file, zipOutputStream);
            }
        } catch (IOException e) {
            throw new RuntimeException("生成Zip包失败：" + e);
        } finally {
            if (zipOutputStream != null) {
                try {
                    zipOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("流关闭异常，错误原因：" + e);
                }
            }
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    throw new RuntimeException("流关闭异常，错误原因：" + e);
                }
            }
        }
    }

    /**
     * 根据输入的文件与输出流对文件进行打包
     */
    private static void zipFile(File inputFile, ZipOutputStream ouputStream) {
        try {
            if (inputFile.exists()) {
                if (inputFile.isFile()) {
                    FileInputStream IN = new FileInputStream(inputFile);
                    BufferedInputStream bins = new BufferedInputStream(IN, 512);
                    ZipEntry entry = new ZipEntry(inputFile.getName());
                    ouputStream.putNextEntry(entry);
                    // 向压缩文件中输出数据
                    int nNumber;
                    byte[] buffer = new byte[512];
                    while ((nNumber = bins.read(buffer)) != -1) {
                        ouputStream.write(buffer, 0, nNumber);
                    }
                    // 关闭创建的流对象
                    bins.close();
                    IN.close();
                } else {
                    try {
                        File[] files = inputFile.listFiles();
                        for (int i = 0; i < files.length; i++) {
                            zipFile(files[i], ouputStream);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * （三）解压缩
     * @param sourceFile 要解压缩的文件的路径
     * @param destDir 解压缩后的目录路径
     * @throws Exception
     */
    public static void deCompress(String sourceFile,String destDir) throws Exception{
        //创建需要解压缩的文件对象
        File file = new File(sourceFile);
        if (!file.exists()){
            throw new RuntimeException(sourceFile + "不存在！");
        }
        //创建解压缩的文件目录对象
        File destDiretory  = new File(destDir);
        if(!destDiretory.exists()){
            destDiretory.mkdirs();
        }
        /*
         * 保证文件夹路径最后是"/"或者"\"
         * charAt()返回指定索引位置的char值
         */
        char lastChar = destDir.charAt(destDir.length()-1);
        if(lastChar!='/'&&lastChar!='\\'){
            //在最后加上分隔符
            destDir += File.separator;
        }
        unzip(sourceFile, destDir);
    }

    /**
     * 解压方法
     * 需要ant.jar
     */
    private static void unzip(String sourceZip,String destDir) throws Exception{
        try{
            Project p = new Project();
            Expand e = new Expand();
            e.setProject(p);
            e.setSrc(new File(sourceZip));
            e.setOverwrite(false);
            e.setDest(new File(destDir));
            e.execute();
        }catch(Exception e){
            throw e;
        }
    }

    /**
     * 压缩文件（用于下载后台普通文件的压缩包）
     */
    public static void zipFile(File zipPath, List<Files> filesList) {

        FileOutputStream fileOutput;
        ZipOutputStream zipOutput;
        BufferedOutputStream bufferedOutput;
        FileInputStream fileInput = null;
        BufferedInputStream bufferedInput = null;
        try {
            fileOutput = new FileOutputStream(zipPath);//输出流，压缩包所在路径
            bufferedOutput = new BufferedOutputStream(fileOutput);
            zipOutput = new ZipOutputStream(bufferedOutput);

            for (int i = 0; i < filesList.size(); i++) {
                Files files = filesList.get(i);
                File filePath = new File(files.getUrl());//待压缩文件路径
                //压缩条目
                ZipEntry entry = new ZipEntry(i + "." + filePath.getName());

                //读取待压缩的文件并写进压缩包里
                fileInput = new FileInputStream(filePath);
                bufferedInput = new BufferedInputStream(fileInput);
                zipOutput.putNextEntry(entry);

                //官方API文档推荐大小8192
                byte[] buffer = new byte[8192];
                int num;
                while ((num = bufferedInput.read(buffer)) != -1) {
                    zipOutput.write(buffer, 0, num);
                }
                //不能写成  int i = bufferedInput.read(buffer);while(i != -1);否则形成死循环，一直写入
            }

            zipOutput.closeEntry();
            fileInput.close();
            bufferedInput.close();
            zipOutput.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
