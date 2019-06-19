package com.agent.czb.web.system.utils;

import org.apache.commons.lang3.time.DateUtils;
import org.apache.log4j.Logger;
import org.springframework.core.convert.converter.Converter;

import java.text.ParseException;
import java.util.Date;

public class DateConverter implements Converter<String, Date> {

	@Override
	public Date convert(String source) {
		try {
			return DateUtils.parseDate(source, "yyyy-MM-dd", "yyyy-MM-dd hh:mm:ss");
		} catch (ParseException e) {
			Logger.getLogger(this.getClass()).error(e.getMessage(), e);
			return null;
		}
	}
}
