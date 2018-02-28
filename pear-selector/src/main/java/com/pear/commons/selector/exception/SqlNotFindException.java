package com.pear.commons.selector.exception;

/**
 * Created by littlehui on 2016/10/19.
 */
public class SqlNotFindException extends RuntimeException {

    public SqlNotFindException() {
        super("未找到的sqlName");
    }

    public SqlNotFindException(String message) {
        super(message);
    }
}
