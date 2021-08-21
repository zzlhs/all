package com.zzl.exception;

import com.zzl.exception.type.BaseException;
import com.zzl.exception.type.BusinessExceptionAssert;

public enum ResponseEnum implements BusinessExceptionAssert {
	
	 /**
     * Bad licence type
     */
    BAD_LICENCE_TYPE(7001, "Bad licence type."),
    /**
     * Licence not found
     */
    LICENCE_NOT_FOUND(7002, "Licence not found.");
	

    /**
     * 返回码
     */
    private int code;
    /**
     * 返回消息
     */
    private String message;
    
	private ResponseEnum(int code, String message) {
		this.code = code;
		this.message = message;
	}
	

	@Override
	public int getCode() {
		return this.getCode();
	}

	@Override
	public String getMessage() {
		return this.message;
	}

	@Override
	public BaseException newException(Object... args) {
		return null;
	}

	@Override
	public BaseException newException(Throwable t, Object... args) {
		// TODO Auto-generated method stub
		return null;
	}
}
