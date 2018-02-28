package com.pear.commons.selector.exception;

/**
 * User: littlehui
 * Date: 2016/2/25
 * Time: 16:02
 */
public class CrudException extends RuntimeException {

    public CrudException() {
        super();
    }

    public CrudException(Exception e) {
        super(e);
    }
}
