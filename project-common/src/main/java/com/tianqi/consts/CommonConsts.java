package com.tianqi.consts;

/**
 * 通用常量类
 * @author yuantianqi
 */
public class CommonConsts {
    /**
     * 全局会话redis的key前缀
     */
    public static final String SESSION_KEY_PREFIX = "user_session:";

    /**
     * 全局门票redis的key前缀
     */
    public static final String TICKET_PREFIX = "user_ticket:";

    /**
     * 临时门条redis的key前缀
     */
    public static final String TMP_TICKET_PREFIX = "user_tmp_ticket:";
}
