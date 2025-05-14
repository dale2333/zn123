package com.wut.zn.utils;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class DateTimeConverter {

    // 将 LocalDateTime 转换为 Date
    public static Date toDate(LocalDateTime localDateTime) {
        return Date.from(localDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    // 将 Date 转换为 LocalDateTime（如需逆向转换）
    public static LocalDateTime toLocalDateTime(Date date) {
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }
}