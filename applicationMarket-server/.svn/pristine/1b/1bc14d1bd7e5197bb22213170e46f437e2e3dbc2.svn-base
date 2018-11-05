package com.techwells.applicationMarket.controller;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.omg.CosNaming.NamingContextPackage.NotEmpty;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.techwells.applicationMarket.domain.Notice;
import com.techwells.applicationMarket.service.NoticeService;
import com.techwells.applicationMarket.util.ApplicationMarketConstants;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;
import com.techwells.applicationMarket.util.WangEditorDomin;

/**
 * 消息的controller
 * @author Administrator
 *
 */
@RestController
public class NoticeController {
	@Resource
	private NoticeService noticeService;

	/**
	 * 发布公告
	 * 
	 * @param content
	 *            文本内容
	 * @param htmlContent
	 *            html内容
	 * @param publishId
	 *            发布人Id
	 * @param title
	 *            公告标题
	 * @param request
	 * @return
	 */
	@RequestMapping("/notice/addNotice")
	public Object addNotice(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String htmlContent = request.getParameter("htmlContent");
		String content = request.getParameter("content");
		String publishId = request.getParameter("publishId");
		String title = request.getParameter("title");

		// 参数校验
		if (StringUtils.isEmpty(htmlContent)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("html不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(content)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("文本内容不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(publishId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("发布人Id不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(title)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("标题不能为空");
			return resultInfo;
		}

		// 封装数据
		Notice notice = new Notice();
		notice.setPublishId(Integer.parseInt(publishId));
		notice.setContent(content);
		notice.setHtmlContent(htmlContent);
		notice.setTitle(title);
		notice.setCreateDate(new Date()); // 发布时间

		// 调用service层中的方法
		try {
			Object object = noticeService.addNotice(notice);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("发布异常");
			return resultInfo;
		}

	}

	/**
	 * WangEditor上传图片
	 * 
	 * @param files
	 *            上传的图片文件 这里的@RequestParam("myFile")一定要和前端设置的名字一样，否则将会接受不到
	 * @param files
	 * @return
	 */
	@RequestMapping("/notice/uploadImage")
	public Object uploadImage(@RequestParam("myFile") MultipartFile[] files) {
		WangEditorDomin wangEditorDomin = new WangEditorDomin(); // 封装结果集
		List<String> data = new ArrayList<String>(); // 存储图片的url
		// 遍历图片
		for (MultipartFile file : files) {
			String fileName = System.currentTimeMillis()
					+ file.getOriginalFilename(); // 图片的名字
			String filepath = ApplicationMarketConstants.UPLOAD_PATH
					+ "notice-image\\";
			String imageUrl = ApplicationMarketConstants.UPLOAD_URL
					+ "notice-image/" + fileName; // 图片的访问路径
			File targetFile = new File(filepath, fileName); // 文件的路径

			// 如果文件夹不存在，那么创建一个即可
			if (!targetFile.getParentFile().exists()) {
				targetFile.getParentFile().mkdirs(); // 递归创建文件夹
			}

			try {
				file.transferTo(targetFile); // 保存图片
				data.add(imageUrl); // 添加url
			} catch (Exception e) {
				wangEditorDomin.setErrno("-1"); // 上传失败
				return wangEditorDomin;
			}
		}

		// 执行到了这里，说明上传成功了，那么返回即可
		wangEditorDomin.setData(data);
		return wangEditorDomin;
	}

	/**
	 * 获取公告的列表
	 * 
	 * @param pageNum
	 *            当前页数
	 * @param pageSize
	 *            每页显示的数量
	 * @param request
	 * @return
	 */
	@RequestMapping("/notice/getNoticeList")
	public Object getNoticeList(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String pageNum = request.getParameter("pageNum");
		String pageSize = request.getParameter("pageSize");

		// 校验参数
		if (StringUtils.isEmpty(pageNum)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("当前页数不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(pageSize)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("每页显示的大小不能为空");
			return resultInfo;
		}

		PagingTool pagingTool = new PagingTool(Integer.parseInt(pageNum),
				Integer.parseInt(pageSize));

		// 调用service层的方法

		try {
			Object object = noticeService.getNoticeList(pagingTool);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}
	}

	/**
	 * 删除公告
	 * 
	 * @param noticeId
	 * @param request
	 * @return
	 */
	@RequestMapping("/notice/deleteNotice")
	public Object deleteNotice(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String noticeId = request.getParameter("noticeId");

		if (StringUtils.isEmpty(noticeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("公告Id不能为空");
			return resultInfo;
		}

		try {
			Object object = noticeService.deleteNotice(Integer
					.parseInt(noticeId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除异常");
			return resultInfo;
		}
	}

	/**
	 * 获取公告详细信息
	 * 
	 * @param noticeId
	 * @param request
	 * @return
	 */
	@RequestMapping("/notice/getNotice")
	public Object getNotice(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String noticeId = request.getParameter("noticeId");
		if (StringUtils.isEmpty(noticeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("公告Id不能为空");
			return resultInfo;
		}

		try {
			Object object = noticeService.getNotice(Integer.parseInt(noticeId));
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("获取异常");
			return resultInfo;
		}

	}

	/**
	 * 修改公告
	 * @param noticeId  公告Id 
	 * @param htmlContent html内容
	 * @param content  文本内容
	 * @param title  标题
	 * @param request
	 * @return
	 */
	@RequestMapping("/notice/modifyNotice")
	public Object modifyNotice(HttpServletRequest request) {
		ResultInfo resultInfo = new ResultInfo();
		String noticeId = request.getParameter("noticeId");
		String htmlContent = request.getParameter("htmlContent");
		String content = request.getParameter("content");
		String title = request.getParameter("title");
		// 参数校验
		if (StringUtils.isEmpty(htmlContent)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("html不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(content)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("文本内容不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(noticeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("公告Id不能为空");
			return resultInfo;
		}

		if (StringUtils.isEmpty(title)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("标题不能为空");
			return resultInfo;
		}
		
		Notice notice=new Notice();
		notice.setContent(content);
		notice.setNoticeId(Integer.parseInt(noticeId));
		notice.setHtmlContent(htmlContent);
		notice.setTitle(title);
		
		try {
			Object object=noticeService.modifyNotice(notice);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改异常");
			return resultInfo;
		}
	}
	
	
	
	/**
	 * 公告置顶 ，其实就是修改top和topTime的值
	 * @param noticeId  Id
	 * @param request
	 * @return
	 */
	@RequestMapping("/notice/topNotice")
	public Object topNotice(HttpServletRequest request){
		ResultInfo resultInfo=new ResultInfo();
		String noticeId=request.getParameter("noticeId");
		
		if (StringUtils.isEmpty(noticeId)) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("公告Id不能为空");
			return resultInfo;
		}
		
		Notice notice=new Notice();
		notice.setNoticeId(Integer.parseInt(noticeId));
		notice.setTop(1);
		notice.setTopTime(new Date());
		
		try {
			Object object=noticeService.modifyNotice(notice);
			return object;
		} catch (Exception e) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("置顶异常");
			return resultInfo;
		}
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
