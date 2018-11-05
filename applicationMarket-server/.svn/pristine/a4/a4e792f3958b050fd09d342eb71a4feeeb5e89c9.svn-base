package com.techwells.applicationMarket.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.techwells.applicationMarket.domain.Wallet;

public interface WalletMapper {
    int deleteByPrimaryKey(Integer walletId);

    int insert(Wallet record);

    int insertSelective(Wallet record);

    Wallet selectByPrimaryKey(Integer walletId);

    int updateByPrimaryKeySelective(Wallet record);

    int updateByPrimaryKey(Wallet record);
    
    /**
     * 根据用户Id和钱包类型查询对应的钱包
     * @param userId  用户Id
     * @param type  钱包类型
     * @return
     */
    Wallet selectWallet(@Param("userId")Integer userId,@Param("type")Integer type);
    
    
    /**
     * 根据用户Id获取钱包
     * @param userId
     * @return
     */
    List<Wallet> selectWallets(Integer userId);
    
    
    
}