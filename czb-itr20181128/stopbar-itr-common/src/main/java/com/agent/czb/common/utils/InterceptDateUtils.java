package com.agent.czb.common.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2016/11/15.
 */
public class InterceptDateUtils {

   public static void main(String[] args) throws Exception
    {
        Calendar cal = Calendar.getInstance();
        String start = "2016-11-17 14:03:13";
        String end = "2016-11-18 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date dBegin = sdf.parse(start);
        Date dEnd = sdf.parse(end);
        List<String> lDate = getInfo(dBegin, dEnd, 1);
        for(int i =0 ;i<lDate.size();i++){
            System.out.println(lDate.get(i));
        }
    }
    public static List<Date> findDates2(Date dBegin, Date dEnd)
    {
        List lDate = new ArrayList();
        lDate.add(dBegin);
        Calendar calBegin = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calBegin.setTime(dBegin);
        Calendar calEnd = Calendar.getInstance();
        // 使用给定的 Date 设置此 Calendar 的时间
        calEnd.setTime(dEnd);
        // 测试此日期是否在指定日期之后
        while (dEnd.after(calBegin.getTime()))
        {
            // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
            calBegin.add(Calendar.DAY_OF_MONTH, 0);
            lDate.add(calBegin.getTime());
        }
        return lDate;
    }


    public static List<String> getInfo(Date dBegin, Date dEnd, int type) {
        List<String> dateList = new ArrayList<>();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        List<Date> Datelist = findDates(dBegin, dEnd);

        Calendar cal = Calendar.getInstance();

        switch (type) {
            case 1://全日
                dateList.clear();
                for (Date date : Datelist) {
                    dateList.add(sdf.format(date));
                }
                break;
            case 2: //周一到周五
                dateList.clear();
                for (Date date : Datelist) {
                    cal.setTime(date);
                    if (cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY && cal.get(Calendar.DAY_OF_WEEK) != Calendar.SUNDAY) {
                        dateList.add(sdf.format(date));
                    }
                }
                break;
            case 3://周六，周日
                dateList.clear();
                for (Date date : Datelist) {
                    cal.setTime(date);
                    if (cal.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY || cal.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
                        dateList.add(sdf.format(date));
                    }
                }
                break;
            default:
                dateList.clear();
                break;
        }

        return dateList;

    }

    private static List<Date> findDates(Date dBegin, Date dEnd) {
        List lDate = new ArrayList();

        if(dEnd.before(dBegin)){// if(dBegin.before(dEnd)){
            return  lDate;
        }else {
            lDate.add(dBegin);
            Calendar calBegin = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calBegin.setTime(dBegin);
            Calendar calEnd = Calendar.getInstance();
            // 使用给定的 Date 设置此 Calendar 的时间
            calEnd.setTime(dEnd);
            // 测试此日期是否在指定日期之后
            while (dEnd.after(calBegin.getTime())) {
                // 根据日历的规则，为给定的日历字段添加或减去指定的时间量
                calBegin.add(Calendar.DAY_OF_MONTH, 1);
                lDate.add(calBegin.getTime());

            }
            lDate.remove(lDate.size()-1);
        }
        return lDate;
    }
}
