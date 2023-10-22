package com.example.caculator02;

import android.net.Uri;
import android.provider.BaseColumns;


public class EquationInfoContent implements BaseColumns {

    public static final String AUTHORITIES = "com.example.server01.provider.EquationInfoProvider";

    // 访问内容提供器的URI
    public static final Uri CONTENT_URI = Uri.parse("content://" + AUTHORITIES + "/user");

    // 下面是该表的各个字段名称
    public static final String EQUATION_STR = "str";
    public static final String EQUATION_ANS = "ans";

}