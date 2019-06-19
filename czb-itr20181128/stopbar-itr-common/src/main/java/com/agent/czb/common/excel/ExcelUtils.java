package com.agent.czb.common.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;



import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * 导出excel工具类
 * 
 * @author linkai
 */
public class ExcelUtils {

	public static void export(OutputStream os, List<String[]> headers, List<?> datas) throws IOException {
		List<String> header = new ArrayList<>(headers.size());
		List<String> fields = new ArrayList<>(headers.size());
		for (String[] strs : headers) {
			header.add(strs[1]);
			fields.add(strs[0]);
		}
		export(os, header, fields, datas);
	}

	/**
	 * 导出数据 response.setContentType("application/x-msdownload");
	 * 
	 * @param os
	 * @param header
	 * @param datas
	 * @throws IOException
	 */
	public static void export(OutputStream os, List<String> header, List<String> fields, List<?> datas)
			throws IOException {
		// 创建EXCEL文档类型
		HSSFWorkbook wb = new HSSFWorkbook();
		// 创建第一个sheet
		HSSFSheet sheet = wb.createSheet("Sheet1");
		// 设置列宽
		// sheet.setColumnWidth(2, 6000);

		// 创建单元格样式
		HSSFCellStyle style = wb.createCellStyle();
		style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		style.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中

		HSSFCellStyle style2 = wb.createCellStyle();
		style2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		style2.setAlignment(HSSFCellStyle.ALIGN_CENTER);// 水平居中

		// style2.setFillForegroundColor(HSSFColor.GOLD.index);
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		style2.setBorderBottom(HSSFCellStyle.BORDER_THIN); // 底部边框
		style2.setBorderLeft(HSSFCellStyle.BORDER_THIN); // 左边边框
		style2.setBorderRight(HSSFCellStyle.BORDER_THIN); // 右边边框
		style2.setBorderTop(HSSFCellStyle.BORDER_THIN); // 顶部边框

		style2.setFillForegroundColor((short) 13);// 设置背景色
		style2.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
		
		Font font = wb.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD); // 粗体
		style2.setFont(font);
		// style2.setWrapText(true); // 自动换行

		HSSFCellStyle style_right = wb.createCellStyle();
		style_right.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);// 垂直居中
		style_right.setAlignment(HSSFCellStyle.ALIGN_RIGHT); // 水平靠右

		HSSFCellStyle style_left = wb.createCellStyle();
		style_left.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		style_left.setAlignment(HSSFCellStyle.ALIGN_LEFT); // 水平靠左

		// 表头
		HSSFRow row = sheet.createRow(0);
		row.setHeight((short) 500);
		for (int i = 0; i < header.size(); i++) {
			HSSFCell cell = row.createCell(i);
			cell.setCellValue(header.get(i));
			cell.setCellStyle(style2);
			sheet.autoSizeColumn(i);
			sheet.setColumnWidth(i, header.get(i).getBytes().length * 2 * 256);
		}
		// 表数据
		for (int i = 0; i < datas.size(); i++) {
			row = sheet.createRow(i + 1);
			Object objModel = datas.get(i);
			Map<String, Object> maps = transBean2Map(objModel);
			/*Gson gson = new Gson();
			String json = gson.toJson(objModel);
			System.out.println("对象转json" + json);
			HashMap maps = gson.fromJson(json, HashMap.class);
			System.out.println("json转map" + maps.toString());*/
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			for (int j = 0; j < fields.size(); j++) {
				String key = fields.get(j);
				Object obj = maps.get(key);
				if (obj != null) {
					HSSFCell cell = row.createCell(j);
					cell.setCellStyle(style);
					if (obj instanceof Date) {
						cell.setCellValue(sdf.format(obj));
					} else if (obj instanceof Calendar) {
						cell.setCellValue((Calendar) obj);
					} else if (obj instanceof Double) {
						cell.setCellValue((Double) obj);
					} else {
						cell.setCellValue(obj.toString());
					}
				}
			}
		}
		wb.write(os);
		os.close();
	}

	public static Map<String, Object> transBean2Map(Object obj) {
		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					map.put(key, value);
				}
			}
		} catch (Exception e) {
			System.out.println("transBean2Map Error " + e);
		}

		return map;
	}

	/**
	 * 获取行(行数，从0开始)
	 */
	public static HSSFRow setRowHeight(HSSFSheet sheet, int num) {
		return sheet.createRow(num);
	}

	/**
	 * 设置行高
	 */
	public static HSSFRow setRowHeight(HSSFRow row, int height) {
		row.setHeight((short) height);
		return row;
	}

	/**
	 * 设置缺省行高
	 */
	public static HSSFSheet setDefaultRowHeight(HSSFSheet sheet, int height) {
		sheet.setDefaultRowHeight((short) height);
		return sheet;
	}

	/**
	 * 设置列宽
	 */
	public static HSSFSheet setColumnWidth(HSSFSheet sheet, int col, int width) {
		sheet.setColumnWidth(col, width);
		return sheet;
	}

	/**
	 * 设置缺省列宽
	 */
	public static HSSFSheet setDefaultColumnWidth(HSSFSheet sheet, int width) {
		sheet.setDefaultColumnWidth(width);
		return sheet;
	}

	public static HSSFSheet autoSizeColumn(HSSFSheet sheet, int col) {
		sheet.autoSizeColumn(col);
		return sheet;
	}

	/**
	 * 合并单元格，参数依次为起始行，结束行，起始列，结束列
	 */
	public static void addMergedRegion(HSSFSheet sheet, int firstRow, int lastRow, int firstCol, int lastCol) {
		sheet.addMergedRegion(new CellRangeAddress(firstRow, lastRow, firstCol, lastCol));
	}

	public static void main(String[] args) {

	}

}
