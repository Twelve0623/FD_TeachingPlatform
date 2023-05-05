package com.teaching.common.core;

/** 时间格式化 **/
public enum DateFormat implements StringEnum {
    ShortNumDate("yyMMdd"),

    NumDate("yyyyMMdd"),

    StrikeDate("yyyy-MM-dd"),

    SplitterYMD("yyyy/MM/dd"),

    NumDateTime("yyyyMMddHHmmss"),

    NumDateMinute("yyyyMMddHHmm"),
    NumDateHour("yyyyMMddHH"),

    TwoYearNumDateTime("yyMMddHHmmss"),

    StrikeDateTime("yyyy-MM-dd HH:mm:ss"),

    DoubleDateTime("yyyyMMddHHmmss.SSS"),

    MillisecondTime("yyyy-MM-dd HH:mm:ss SSS"),

    TimeStamp("yyyy-MM-dd HH:mm:ss.SSS"),

    NumTime("HHmmss"),

    RFC3339("yyyy-MM-dd'T'HH:mm:ss"),
    RFC3340("yyyy-MM-dd'T'HH:mm:ss'Z'"),

    ColonTime("HH:mm:ss"),
    noMillisecondTime("HH:mm"),

    ChineseYearMonthDayTime("yyyy年MM月dd日 HH:mm:ss"),

    ChineseYearMonthTime("yyyy年MM月"),
    YearMonth("yyyy-MM"),
    ChineseMonthDayTime("MM月dd日"),
    PointYearMonthTime("yyyy.mm"),
    YearMonthNumDate("yyyyMM"),

    ;
    DateFormat(String value) {
	    changeNameTo(this, value);
    }
}
