package com.techwells.applicationMarket.dao;

import java.util.List;

import javax.xml.soap.Detail;

import org.apache.ibatis.annotations.Param;

import com.techwells.applicationMarket.domain.WalletDetail;
import com.techwells.applicationMarket.domain.rs.WalletDetailVos;
import com.techwells.applicationMarket.util.PagingTool;

public interface WalletDetailMapper {
    int deleteByPrimaryKey(Integer detailId);

    int insert(WalletDetail record);

    int insertSelective(WalletDetail record);

    WalletDetail selectByPrimaryKey(Integer detailId);

    int updateByPrimaryKeySelective(WalletDetail record);

    int updateByPrimaryKey(WalletDetail record);
    
    /**
     * 获取所有区块高度为null的信息
     * @return
     */
    List<WalletDetail> selectListByBlock();
    
    /**
     * 获取最新的几条明细
     * @param limit
     * @return
     */
    List<WalletDetail> selectListLimit(@Param("walletId")Integer walletId,@Param("limit")Integer limit);
    
    /**
     * 分页获取钱包明细
     * @param pagingTool
     * @return
     */
    List<WalletDetail> selectWalletDetails(PagingTool pagingTool);
    
    
    /**
     * 获取钱包明细的数量
     * @param pagingTool
     * @return
     */
    int countTotalWalletDetail(PagingTool pagingTool);
    
    
    /**
     * 获取分页详情的年月信息
     * @param pagingTool
     * @return
     */
    List<WalletDetailVos> selectDetailVosList(PagingTool pagingTool);
    
    
    /**
     * 根据年月查询钱包明细
     * @param yearMonth
     * @return
     */
    WalletDetail selectWalletDetail(@Param("yearMonth")String yearMonth,@Param("limit")Integer limit);
    
    /**
     * 分页获取钱包明细 后台
     * @param pagingTool
     * @return
     */
    List<WalletDetail> selectWalletDetailsBack(PagingTool pagingTool);
    
    int countTotalWalletDetailsBack(PagingTool pagingTool);
    
    
    
    
    
}