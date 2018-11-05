package com.techwells.applicationMarket.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.techwells.applicationMarket.dao.NoticeMapper;
import com.techwells.applicationMarket.domain.Notice;
import com.techwells.applicationMarket.domain.rs.NoticeAdmin;
import com.techwells.applicationMarket.service.NoticeService;
import com.techwells.applicationMarket.util.PagingTool;
import com.techwells.applicationMarket.util.ResultInfo;

@Service
public class NoticeServiceImpl implements NoticeService{

	@Resource
	private NoticeMapper noticeMapper;
	
	@Override
	public Object addNotice(Notice notice) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=noticeMapper.insertSelective(notice); 
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("插入失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("添加成功");
		return resultInfo;
	}

	@Override
	public Object deleteNotice(Integer noticeId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=noticeMapper.deleteByPrimaryKey(noticeId);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("删除失败");
			return resultInfo;
		}
		
		resultInfo.setMessage("删除成功");
		return resultInfo;
	}

	@Override
	public Object getNotice(Integer noticeId) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		Notice notice=noticeMapper.selectByPrimaryKey(noticeId);
		
		if (notice==null) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("该公告不存在");
			return resultInfo;
		}
		resultInfo.setResult(notice);
		resultInfo.setMessage("获取成功");
		return resultInfo;
	}

	@Override
	public Object getNoticeList(PagingTool pagingTool) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		List<NoticeAdmin> noticeAdmins=noticeMapper.selectNoticeAdmins(pagingTool);
		int count=noticeMapper.countTotal(pagingTool);
		resultInfo.setMessage("获取成功");
		resultInfo.setResult(noticeAdmins);
		resultInfo.setTotal(count);
		return resultInfo;
	}

	@Override
	public Object modifyNotice(Notice notice) throws Exception {
		ResultInfo resultInfo=new ResultInfo();
		int count=noticeMapper.updateByPrimaryKeySelective(notice);
		
		if (count==0) {
			resultInfo.setCode("-1");
			resultInfo.setMessage("修改失败");
			return resultInfo;
		}
		resultInfo.setMessage("修改成功");
		return resultInfo;
	}

	
}
