package com.agent.czb.common.spring;

import org.springframework.util.StringUtils;

import java.beans.PropertyEditorSupport;
import java.text.DateFormat;
import java.text.ParseException;
import java.util.Date;

/**
 * Created by Administrator on 2016/4/28.
 */
public class MyCustomDateEditor extends PropertyEditorSupport {
    private final DateFormat[] dateFormat;

    public MyCustomDateEditor(DateFormat... dateFormat) {
        this.dateFormat = dateFormat;
    }

    public void setAsText(String text) throws IllegalArgumentException {
        if (!StringUtils.hasText(text)) {
            this.setValue(null);
        } else {
            Date date = null;
            for (DateFormat format : dateFormat) {
                try {
                    date = format.parse(text);
                    break;
                } catch (ParseException ignored) {
                }
            }
            if (date == null) {
                throw new IllegalArgumentException("Could not parse date: " + text);
            }
            this.setValue(date);
        }

    }

    public String getAsText() {
        Date value = (Date) this.getValue();
        String text = null;
        if (value != null) {
            for (DateFormat format : dateFormat) {
                try {
                    text = format.format(value);
                    break;
                } catch (Exception ignored) {
                }
            }
        }
        if (text == null) {
            text = "";
        }
        return text;
    }

}
