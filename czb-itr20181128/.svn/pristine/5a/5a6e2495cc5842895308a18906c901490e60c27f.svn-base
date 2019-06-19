package com.agent.czb.core.sys.service;

import com.agent.czb.common.rest.service.BasisService;
import com.agent.czb.common.utils.DateUtils;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.core.sys.enums.WalletInfoEnums;
import com.agent.czb.core.sys.mapper.TransmaticLogMapper;
import com.agent.czb.core.sys.model.TransmaticLogModel;
import com.esotericsoftware.minlog.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/15.
 */
@Service("tranLogService")
public class TransmaticLogService extends BasisService<TransmaticLogMapper ,TransmaticLogModel>{
    @Autowired
    private WalletInfoService walletInfoService;

    public void  updateMoney(){
        Map<String, String> params = FrameUtils.newMap();
        params.put("isDel","1");
        List<TransmaticLogModel> list = pageList(params);
        for(int i = 0 ; i<list.size();i++){
            if(DateUtils.isToday(list.get(i).getStartDate())){
                Long sellerId = list.get(i).getSellerId();  //卖家ID
                Long price= list.get(i).getPrice();  //单价 ，一天的价钱
               /* Log.info(sellerId+"===============----"+price);*/
                                                                    //扣除30%手续费
                Long row=walletInfoService.addMoney(sellerId, Double.valueOf(price *(1-0.3)).longValue(),
                        WalletInfoEnums.Type.SELL_CARPORT.toValue(),
                        // 购买日志ID
                        list.get(i).getRefId());
                if(row>0){//扣款成功后修改订单状态
                    //修改自动转账订单状态
                    list.get(i).setIsDel(0);
                    list.get(i).setUpdateTime(new Date());
                    updateBySelective(list.get(i));
                }

            }
        }

    }



    public void  updateMoney(Long seller_Id , Long carportBuyLogId){
        Map<String, String> params = FrameUtils.newMap();
        params.put("isDel","1");
        params.put("sellerID",seller_Id.toString());
        params.put("refId",String.valueOf(carportBuyLogId));
        Log.info("seller_Id.toString():"+seller_Id.toString()+"================="+"String.valueOf(carportBuyLogId):"+String.valueOf(carportBuyLogId));
        List<TransmaticLogModel> list = pageList(params);
        if(list.size()>0){
            Log.info(list.get(0).getStartDate()+"==========");
            if(DateUtils.isToday(list.get(0).getStartDate())){
                Long sellerId = list.get(0).getSellerId();  //卖家ID
                Long price= list.get(0).getPrice();  //单价 ，一天的价钱
               /* Log.info(sellerId+"===============----"+price);*/

                Log.info("Double.valueOf(price *(1-0.3)).longValue():::"+Double.valueOf(price *(1-0.3)).longValue());
                Log.info("list.get(0).getRefId():::"+list.get(0).getRefId());
                //扣除30%手续费
                Long row =walletInfoService.addMoney(sellerId, Double.valueOf(price *(1-0.3)).longValue(),
                        WalletInfoEnums.Type.SELL_CARPORT.toValue(),
                        // 购买日志ID
                        list.get(0).getRefId());
                if(row>0){//扣款成功后修改订单状态
                    //修改自动转账订单状态
                    list.get(0).setIsDel(0);
                    list.get(0).setUpdateTime(new Date());
                    updateBySelective(list.get(0));
                }

            }

        }
    }
}
