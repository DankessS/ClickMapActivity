package com.academy.web.config;

/**
 * Created by Daniel Palonek on 2016-09-05.
 */
public class RedirectUrls {

    public static final String HOME = "http://localhost:8080/#/";
    public static final String ERROR = "error/";
    public static final String ERROR_LOGIN = HOME + ERROR + "login";
    public static final String ERROR_USER_EXIST = HOME + ERROR + "userExisted";
    public static final String ERROR_COULD_NOT_UPLOAD_SUBPAGE = HOME + ERROR + "subpageNotUploaded";

}