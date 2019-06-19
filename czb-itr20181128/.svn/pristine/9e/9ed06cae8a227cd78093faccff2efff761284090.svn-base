package com.agent.czb.service.sys.restful;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.agent.czb.common.rest.PageHelp;
import com.agent.czb.common.rest.ResultHelp;
import com.agent.czb.common.rest.restful.BasisRestful;
import com.agent.czb.common.spring.SpringContextUtil;
import com.agent.czb.common.utils.DateUtils;
import com.agent.czb.common.utils.FrameUtils;
import com.agent.czb.common.utils.SendMsgUtils;
import com.agent.czb.core.park.enums.ParkWhiteEnum;
import com.agent.czb.core.park.model.ParkOrderInfoModel;
import com.agent.czb.core.park.model.ParkSysInfoModel;
import com.agent.czb.core.park.model.ParkWhiteListModel;
import com.agent.czb.core.park.service.ParkOrderInfoService;
import com.agent.czb.core.park.service.ParkSysInfoService;
import com.agent.czb.core.park.service.ParkWhiteListService;
import com.agent.czb.core.sys.enums.CarportInfoEnums;
import com.agent.czb.core.sys.enums.FileInfoEnums;
import com.agent.czb.core.sys.enums.LocaInfoEnums;
import com.agent.czb.core.sys.model.CarportBuyLogModel;
import com.agent.czb.core.sys.model.CarportInfoModel;
import com.agent.czb.core.sys.model.LocaInfoModel;
import com.agent.czb.core.sys.model.UserInfoModel;
import com.agent.czb.core.sys.service.CarportBuyLogService;
import com.agent.czb.core.sys.service.CarportCollectService;
import com.agent.czb.core.sys.service.CarportInfoService;
import com.agent.czb.core.sys.service.FileUpdateInfoService;
import com.agent.czb.core.sys.service.LocaInfoService;
import com.agent.czb.core.sys.service.PayOrderLogService;
import com.agent.czb.core.sys.service.PointInfoService;
import com.agent.czb.core.sys.service.UserInfoService;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;

/**
 * 车位信息Restful接口
 */
@RestController
@RequestMapping("/services/carportInfo")
public class CarportInfoRestful extends BasisRestful {
    private static Logger log = LoggerFactory.getLogger(CarportInfoRestful.class);
    

    @Autowired
    private CarportInfoService carportInfoService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private LocaInfoService locaInfoService;

    @Autowired
    private FileUpdateInfoService fileUpdateInfoService;

    @Autowired
    private PayOrderLogService payOrderLogService;

    @Autowired
    private CarportBuyLogService carportBuyLogService;

    @Autowired
    private CarportCollectService carportCollectService;

    @Autowired
    private ParkSysInfoService parkSysInfoService;
    
    @Autowired
    private ParkWhiteListService parkWhiteListService;
    
    @Autowired
    private PointInfoService pointInfoService;

    @RequestMapping(value = "/locaList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResultHelp locaList(HttpServletRequest request, String mapLng, String mapLat) {
        if (StringUtils.isEmpty(mapLng)) {
            return ResultHelp.newFialResult("经度不为空");
        }
        if (StringUtils.isEmpty(mapLat)) {
            return ResultHelp.newFialResult("纬度不为空");
        }
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.params().put("type", LocaInfoEnums.Type.CARPORT.toValue());
        List<LocaInfoModel> list = locaInfoService.locaList(pageHelp.params());
        List<Long> list2 = new ArrayList<>();
        for (LocaInfoModel model : list) {
            list2.add(model.getRefId());
        }
        List<CarportInfoModel> byIds = carportInfoService.findByIds(list2);
        for (int i = 0; i < byIds.size(); i++) {
            CarportInfoModel model = byIds.get(i);
            model.setDistance(list.get(i).getDistance());
            model.setMapLng(list.get(i).getMapLng());
            model.setMapLat(list.get(i).getMapLat());
            setInfo(model, request);
        }
        pageHelp.setData(byIds);
        return ResultHelp.newResult(pageHelp);
    }
/////////////////////////////////////////////
    @RequestMapping(value = "/pageList", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pageList(HttpServletRequest request) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(carportInfoService);
        List<CarportInfoModel> list= (List<CarportInfoModel>) pageHelp.getData();
        for (CarportInfoModel model : list) {
            setInfo(model, request);
        }
        return ResultHelp.newResult(pageHelp);
    }

        /**
         * 创建人:qany.
         * 创建时间:2016/12/6:14:23.
         * 描述: 跨域请求
         */
    @RequestMapping(value = "/pageListJsop", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public void pageListJsop(HttpServletRequest request,HttpServletResponse response) {
        PageHelp pageHelp = PageHelp.newPageHelp(request);
        pageHelp.page(carportInfoService);
        String jsonp=request.getParameter("callback");
        response.setContentType("application/x-javascript");
        response.setCharacterEncoding("UTF-8");
        response.setHeader("P3P", "CP=CURa ADMa DEVa PSAo PSDo OUR BUS UNI PUR INT DEM STA PRE COM NAV OTC NOI DSP COR");
        List<CarportInfoModel> list= (List<CarportInfoModel>) pageHelp.getData();
        for (CarportInfoModel model : list) {
            setInfo(model, request);
        }

        String jsoncallback = jsonp + "("+ JSONObject.toJSON(ResultHelp.newResult(pageHelp))+")";

        PrintWriter out = null;
        try {
            out = response.getWriter();
        } catch (IOException e) {
            e.printStackTrace();
        }

        out.print(jsoncallback);

        out.flush();
        out.close();
    }


    @RequestMapping(value = "/get", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp get(Long id) {
        CarportInfoModel model = carportInfoService.selectById(id);
        setInfo(model);
        return ResultHelp.newResult(model);
    }

    public void setInfo(CarportInfoModel model) {
        setInfo(model, null);
    }

    public void setInfo(CarportInfoModel model, HttpServletRequest request) {
        if (request != null) {
            String showImgs = request.getParameter("showImgs");
            if (StringUtils.equals(showImgs, "true") || StringUtils.equals(showImgs, "1")) {
                List<String> strings = fileUpdateInfoService.selectUrlsByTypeRvId(model.getId(), FileInfoEnums.RvType.CARPORT.name());
                model.setImgs(strings);
            }
            if (model.getState() != null) {
                model.setStateStr(CarportInfoEnums.State.getDesc(model.getState()));
            }
            String showBuyer = request.getParameter("showBuyer");
            if (StringUtils.isNotEmpty(showBuyer)) {
                Map<String, String> params = FrameUtils.newMap();
                params.put("carportId", model.getId().toString());
                params.put("pagerLimit", "limit 0, 1");
                params.put("pagerOrder", "order by id desc");
                List<CarportBuyLogModel> carportBuyLogList = carportBuyLogService.pageList(params);
                if (carportBuyLogList.size() > 0) {
                    CarportBuyLogModel carportBuyLog = carportBuyLogList.get(0);
                    UserInfoModel userInfoModel = userInfoService.selectById(carportBuyLog.getUserId());
                    model.setBuyer(userInfoModel);
                }
            }
            if(request.getParameter("id") != null && !request.getParameter("id").equals("")){
                //修改浏览次数
                CarportInfoModel  carportInfoModel =  carportInfoService.selectById(request.getParameter("id"));
                int v = carportInfoModel.getVisits();
                carportInfoModel.setVisits(v+1);
                carportInfoService.update(carportInfoModel);
            }
        }
        UserInfoModel userInfo = userInfoService.selectById(model.getUserId());
        if (userInfo != null) {
            model.setUserName(userInfo.getNickName());
            model.setUserInfo(userInfo);
        }

        ParkSysInfoModel parkSysInfo= parkSysInfoService.selectByParkCode(model.getParkCode());
        if(parkSysInfo !=null){
            model.setParkSysAddr(parkSysInfo.getAddr());
        }
        //设置收藏数 ，
        //只查询state = 1 （收藏状态）的
        Map<String, String> dataMap = FrameUtils.newMap("state", CarportInfoEnums.CollectState.COLLECT.toValue());
        dataMap.put("carportId", model.getId().toString());
        model.setCollectNum(carportCollectService.pageCount(dataMap));

    }
    
    /**
     * 发布信息
     *//////////////////////////////////////////////////////////////////
    @RequestMapping(value = "/release", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp release(CarportInfoModel model, String fileUrls, String mapLng, String mapLat,Integer type) {
        if (model.getUserId() == null) {
            return ResultHelp.newFialResult("用户标识不能为空");
        }else{
        	
        	 //发布时判断是否有支付的订单
            ParkOrderInfoService parkOrderInfoService=SpringContextUtil.getBean(ParkOrderInfoService.class);
    		List<ParkOrderInfoModel> list = parkOrderInfoService.selectByUserId(model.getUserId());
    		for (ParkOrderInfoModel parkOrderInfoModel : list) {
    				if (parkOrderInfoModel.getState().equals("401")||parkOrderInfoModel.getState().equals("403")) {
    					return ResultHelp.newFialResult("您有未支付的订单，请您及时去支付再发布车位");
    				}
    		}
        }
        if (StringUtils.isEmpty(model.getTitle())) {
            return ResultHelp.newFialResult("车位标题称不能为空");
        }
        if (StringUtils.isEmpty(model.getAddr())) {
            return ResultHelp.newFialResult("车位地址不能为空");
        }
        if (StringUtils.isEmpty(mapLng)) {
            return ResultHelp.newFialResult("经度不能为空");
        }
        if (StringUtils.isEmpty(mapLat)) {
            return ResultHelp.newFialResult("纬度不能为空");
        }
        String[] urls = null;
        if (StringUtils.isNotEmpty(fileUrls)) {
            urls = fileUrls.split(",");
        }
        ParkWhiteListModel parkWhiteList = parkWhiteListService.findBySateAndCode(ParkWhiteEnum.State.车位.toValue(), model.getParkCode(), model.getCode(),model.getId());
        long carportTime = model.getEffTime().getTime();
        long parkWhiteTime = parkWhiteList.getEndTime().getTime();
        if (carportTime>parkWhiteTime) {
        	return ResultHelp.newFialResult("分享车位时间不能大于车位到期时间，到期时间："+DateUtils.F_YMDHMS_.format(parkWhiteTime));
		}
        
        //   OPEN("0", "上线"), BUY("1", "已购买"), CLOSE("2", "下线"),ADMOFFLINE("3","管理员手动下线"),USEROFFLINE("4","用户手动下线"),EXAMINE("5","待审核");
        model.setState(CarportInfoEnums.State.OPEN.toValue());
        model.setIsDel(0);
//        model.setParkCode("PD_001");
        model.setVisits(0);
        model.setCreateTime(new Date());
        model.setUpdateTime(new Date());
        carportInfoService.releaseCarport(model, urls, mapLng, mapLat);
        return ResultHelp.newResult(model);
    }

    /**
     * 发布信息
     */
    @RequestMapping(value = "/offline", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp release(Long carportId, Long userId) {
        carportInfoService.offline(carportId, userId);
        return ResultHelp.newResult("下线成功");
    }


         /**
         * 购买车位(生成订单)
         */
    @RequestMapping(value = "/buy", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp buy( Long carportId, Long userId, String plateNum,
                          Long price, Integer time,
                          String remark, Date endTime,Integer publishType) {

        if (carportId == null || carportId == 0) {
            return ResultHelp.newFialResult("车位标识不能为空");
        }
        if (userId == null || userId == 0) {
            return ResultHelp.newFialResult("用户标识不能为空");
        }
        if ((price == null || price == 0) && publishType==0) {
            return ResultHelp.newFialResult("购买单价不能为空");
        }
        if ((time == null || time == 0) && publishType==0) {
            return ResultHelp.newFialResult("购买月数不能为空");
        }
        CarportInfoModel carportInfo = carportInfoService.selectById(carportId);
        if (carportInfo == null) {
            return ResultHelp.newFialResult("车位不存在");
        }
        UserInfoModel userInfo = userInfoService.selectById(userId);
        if (userInfo == null) {
            return ResultHelp.newFialResult("用户不存在");
        }else{
        	//查出该车的停车场
    		ParkWhiteListModel parkWhiteListModel= parkWhiteListService.selectByCarportId(carportId);
    		String parkCode = parkWhiteListModel.getParkCode();
    		List<ParkWhiteListModel> list = parkWhiteListService.selectByUserId(userId);
    		Date currentDate=new Date();
    		for (ParkWhiteListModel model : list) {
    			if (model.getParkCode().equals(parkCode)&&model.getPlateNum().equals(plateNum)) {
    				//共享的人购买
    				if (model.getState().equals("1") && currentDate.before(model.getEndTime())) {
    					return  ResultHelp.newFialResult("您的车牌在本停车场已经购买了一个车位，请使用完再进行购买");
    				//车主	
    				}if (model.getState().equals("0")&&model.getCarportId()!=null) {
    					CarportInfoModel carportInfoModel = carportInfoService.selectById(model.getCarportId());
    					if (currentDate.before(carportInfoModel.getEffTime())) {
    						return ResultHelp.newFialResult("您的车牌在本停车场发布了车位，暂时不能购买该停车场的其他车位");
						}
    				}
    		}
    	  }
    		//购买时判断该用户是否有未支付的订单
    		ParkOrderInfoService parkOrderInfoService=SpringContextUtil.getBean(ParkOrderInfoService.class);
    		List<ParkOrderInfoModel> list2 = parkOrderInfoService.selectByUserId(userId);
    		for (ParkOrderInfoModel parkOrderInfoModel : list2) {
    				if (parkOrderInfoModel.getState().equals("401")||parkOrderInfoModel.getState().equals("403")) {
    					return ResultHelp.newFialResult("您有未支付的订单，请您及时去支付再进行购买");
					}
			}
    		
    	}
        ParkOrderInfoModel parkOrderInfo= carportInfoService.buy(userId,carportInfo, userInfo, plateNum, price, time, remark, endTime);
        return ResultHelp.newResult(parkOrderInfo);
    }


    @RequestMapping(value = "/updateState", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp updateState(Long userId, Long carportId, String state) {
        if (carportId == null || carportId == 0) {
            return ResultHelp.newFialResult("车位标识不能为空");
        }
        if (StringUtils.isEmpty(state)) {
            return ResultHelp.newFialResult("状态不能为空");
        }
        CarportInfoEnums.State state1 = CarportInfoEnums.State.valueOf(state);
        if (state1 == null) {
            return ResultHelp.newFialResult("状态不正确");
        }
        if (state.equals(CarportInfoEnums.State.BUY.toValue())) {
            return ResultHelp.newFialResult("已购买状态的车位不能修改状态");
        }
        CarportInfoModel carportInfo = new CarportInfoModel();
        carportInfo.setId(carportId);
        carportInfo.setState(state1.toValue());
        carportInfo.setUpdateTime(new Date());
        int rows = carportInfoService.updateBySelective(carportInfo);
        return ResultHelp.newResult(rows);
    }

    @RequestMapping(value = "/del", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp del(Long id) {
        int rows = carportInfoService.deleteAndLocaInfo(id);
        return ResultHelp.newResult(rows);
    }


    @RequestMapping(value = "/pass", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp pass(Long carportId, Long userId) {
        carportInfoService.pass(carportId, userId);
        return ResultHelp.newResult("车位通过成功");
    }

    @RequestMapping(value = "/admOffline", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp admOffline(Long carportId, Long userId) {
        carportInfoService.admOffline(carportId, userId);
        return ResultHelp.newResult("管理员下架成功");
    }

    @RequestMapping(value = "/isdel", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp isdel(Long carportId, Long userId) {
        carportInfoService.isDel(carportId, userId);
        return ResultHelp.newResult("管理员删除成功");

    }
	/**
	 * 取消发布信息
	 * @param carportId 车位ID
	 * @return
	 */
	@RequestMapping(value = "/cancelPublish", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.OK)
    public ResultHelp cancelPublish(Long carportId) {
		int carportInfoNum=carportInfoService.selectCount(carportId);
    	 if (carportInfoNum==1) {
    		CarportInfoModel cancelPublish = carportInfoService.cancelPublish(carportId);
    		 if (cancelPublish.getState().equals("1")||cancelPublish.getState().equals("6")) {
    			 return ResultHelp.newFialResult("您的车位已被购买，无法取消！");	
    			}else{
    				carportInfoService.delete(cancelPublish.getId());
    				ParkWhiteListModel parkWhiteListModel=parkWhiteListService.findByCarportId(cancelPublish.getId());
     	    		parkWhiteListModel.setIsExp(null);
     	    		parkWhiteListModel.setCarportId(null);
     	            parkWhiteListService.update(parkWhiteListModel);
    			}
         }else {
			return ResultHelp.newFialResult("停车场中没有该车位信息！");
		}
    	      return ResultHelp.newResult("取消发布成功");
    } 
	public static void main(String[] args) {
		/*String str="1234:234";
		String str2=" 0"+str;
		System.out.println("输出是："+str.substring(4,5));
		System.out.println("输出是1："+str2);*/
		try {
			SendMsgUtils.sendMsgRemind("17371319633", "我的sfsdf", "364636", "数据没dfafds有问题");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

