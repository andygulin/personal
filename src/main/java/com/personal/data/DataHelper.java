package com.personal.data;

import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;

public class DataHelper {

	public static final String USER_AGENT = "Mozilla/4.0 (compatible; MSIE 9.0; Windows NT 6.1; Trident/5.0)";

	public static final String FILE_ENCODING = "UTF-8";

	public static final int DEFAULT_PAGE_SIZE = 1;
	public static final String NO_PHOTO = "http://x.autoimg.cn/car/images/grey-bg.gif";

	public static final String PARSE_URL = "http://www.qiushibaike.com/month/page/";

	public static final int TIMEOUT = Integer.MAX_VALUE;

	public static final long WAIT_TIMEOUT = 30L;
	public static final TimeUnit WAIT_TIMEUNIT = TimeUnit.SECONDS;

	public static final String USERNAME = "andygulin";
	public static final String PASSWORD = DigestUtils.md5Hex(USERNAME);

}
