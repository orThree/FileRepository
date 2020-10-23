package com.pan.file.utils;

import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName FileUtil
 * @Description TODO
 * @Version 1.0
 */
public class OflFileBean {
	private static Logger LOGGER = LoggerFactory.getLogger(FileUtil.class);

	/**
	 * @param response
	 * @param newFileName        文件存放名称
	 * @param uploadPath         文件地址
	 * @param isOriginalFileName true 获取旧文件名，false 不获取旧文件名
	 * @return void
	 * @Description //TODO 下载文件
	 **/
	public static void downFile(HttpServletResponse response, String newFileName, String uploadPath, boolean isOriginalFileName) {
		//下載文件
		String filePath = uploadPath + File.separator + newFileName;
		String fileName = "";
		//获得上传原始文件名
		if (isOriginalFileName) {
			fileName = getFileName(newFileName);
		} else {
			fileName = newFileName;
		}
		//判断文件是否存在
		File file = new File(filePath);
		if (!file.exists()) {
			throw new RuntimeException("文件不存在");
		}
		FileInputStream fis = null;
		OutputStream myout = null;
		BufferedInputStream buff = null;
		try {
			if (file.exists()) {
				response.setHeader("Content-Disposition",
						"attachment;filename=" + new String((fileName).getBytes("UTF-8"), "ISO8859-1"));
				response.setContentLength((int) file.length());
				//暴露Header
				response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
				// 定义输出类型
				response.setContentType("application/octet-stream");
				fis = new FileInputStream(file);
				buff = new BufferedInputStream(fis);
				// 相当于我们的缓存
				byte[] b = new byte[1024];
				// 该值用于计算当前实际下载了多少字节
				long k = 0;
				// 从response对象中得到输出流,准备下载
				myout = response.getOutputStream();
				// 开始循环下载
				while (k < file.length()) {
					int j = buff.read(b, 0, 1024);
					k += j;
					myout.write(b, 0, j);
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("文件下载流错误，错误原因：" + e);
		} finally {
			if (fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					throw new RuntimeException("流关闭异常，错误原因：" + e);
				}
			}
			if (myout != null) {
				try {
					myout.flush();
					myout.close();
				} catch (IOException e) {
					throw new RuntimeException("流关闭异常，错误原因：" + e);
				}
			}
			if (buff != null) {
				try {
					buff.close();
				} catch (IOException e) {
					throw new RuntimeException("流关闭异常，错误原因：" + e);
				}
			}
		}
	}

	/**
	 * 把接受的全部文件打成压缩包
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
	 *
	 * @param inputFile
	 * @param ouputStream
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
	 * @param filePath 文件地址
	 * @return boolean
	 * @Description //TODO 删除文件
	 **/
	public static boolean deleteFileByPath(String filePath) {
		File file = new File(filePath);
		if (!file.exists()) {
			System.out.println(("删除文件失败:" + filePath + "不存在！"));
			return false;
		} else {
			if (file.isFile()) {
				return deleteFile(filePath);
			} else {
				return deleteDirectory(filePath);
			}
		}
	}

	/**
	 * @param filePath 文件地址
	 * @return boolean
	 * @Description //TODO 删除单个文件
	 * @Param
	 **/
	public static boolean deleteFile(String filePath) {
		File file = new File(filePath);
		// 如果文件路径所对应的文件存在，并且是一个文件，则直接删除
		if (file.exists() && file.isFile()) {
			if (file.delete()) {
				// LOGGER.info("删除单个文件" + filePath + "成功！");
				return true;
			} else {
				System.out.println(("删除单个文件" + filePath + "失败！"));
				return false;
			}
		} else {
			System.out.println(("删除单个文件失败：" + filePath + "不存在！"));
			return false;
		}
	}

	/**
	 * @param dir 地址
	 * @return boolean
	 * @Description //TODO 删除整个目录
	 **/
	public static boolean deleteDirectory(String dir) {
		// 如果dir不以文件分隔符结尾，自动添加文件分隔符
		if (!dir.endsWith(File.separator)) {
			dir = dir + File.separator;
		}
		File dirFile = new File(dir);
		// 如果dir对应的文件不存在，或者不是一个目录，则退出
		if ((!dirFile.exists()) || (!dirFile.isDirectory())) {
			System.out.println(("删除目录失败：" + dir + "不存在！"));
			return false;
		}
		boolean flag = true;
		// 删除文件夹中的所有文件包括子目录
		File[] files = dirFile.listFiles();
		for (int i = 0; i < files.length; i++) {
			// 删除子文件
			if (files[i].isFile()) {
				flag = deleteFile(files[i].getAbsolutePath());
				if (!flag) {
					break;
				}
			}
			// 删除子目录
			else if (files[i].isDirectory()) {
				flag = deleteDirectory(files[i]
						.getAbsolutePath());
				if (!flag) {
					break;
				}
			}
		}
		if (!flag) {
			System.out.println(("删除目录" + dir + "失败！"));
			return false;
		}
		// 删除当前目录
		if (dirFile.delete()) {
			System.out.println(("删除目录" + dir + "成功！"));
			return true;
		} else {
			return false;
		}
	}

	/**
	 * @param files    文件集合
	 * @param filePath 文件上传路径
	 * @return java.util.List<java.util.Map < java.lang.String, java.lang.Object>>
	 * @Description //TODO  文件上传
	 **/
	public static List<Map<String, Object>> FileUpload(List<MultipartFile> files, String filePath) {
		if (files.isEmpty()) {
			throw new RuntimeException("当前上传文件为空");
		}
		//上传地址
		List<Map<String, Object>> fileNameList = new ArrayList<>();
		Map map = null;
		for (MultipartFile file : files) {
			map = new HashMap();
			String fileName = file.getOriginalFilename();
			//旧文件名
			map.put("originalFileName", fileName);
			//新文件名
			String newFileName = getUniqueFileName(fileName);
			map.put("newFileName", newFileName);
			if (file.isEmpty()) {
				throw new RuntimeException("当前上传文件为空！");
			} else {
				File dest = new File(filePath + "/" + newFileName);
				if (!dest.getParentFile().exists()) { //判断文件父目录是否存在
					dest.getParentFile().mkdir();
				}
				try {
					file.transferTo(dest);
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					throw new RuntimeException("上传文件错误");
				}
			}
			fileNameList.add(map);
		}
		return fileNameList;
	}


	/**
	 * <p>获取文件唯一名字工具类</p>
	 *
	 * @param fileName 文件名称
	 * @return 文件名称
	 */
	public static String getUniqueFileName(String fileName) {
		Timestamp d = new Timestamp(System.currentTimeMillis());
		SimpleDateFormat s = new SimpleDateFormat("yyyyMMddHHmmss");
		String time = s.format(d);
		return time + "_" + fileName;
	}

	/**
	 * <p>获取到原文件名</p>
	 *
	 * @param uploadFileName 上传的文件名
	 * @return 原文件名
	 */
	public static String getFileName(String uploadFileName) {
		String filename = null;
		if (uploadFileName.indexOf("_") > 0) {
			filename = uploadFileName.substring(uploadFileName.indexOf("_") + 1);
		}
		return filename;
	}
}