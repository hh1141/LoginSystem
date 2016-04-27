package com.honghaisen.mystudyapplication;

/**
 * Created by hison7463 on 4/26/16.
 */
public class Values {
    public static final String emailPattern = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
    public static final String phonePattern = "^(\\+\\d{1,2}\\s)?\\(?\\d{3}\\)?[\\s.-]\\d{3}[\\s.-]\\d{4}$";
    public static final String DATABASE_NAME = "Users.db";
    public static final String USER_COLUMN_EMAIL = "email";
    public static final String USER_COLUMN_PASS = "password";
    public static final String USER_COLUMN_NAME = "name";
    public static final String USER_COLUMN_PHONE = "phone";
}
