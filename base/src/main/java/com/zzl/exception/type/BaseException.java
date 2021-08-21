package com.zzl.exception.type;

import java.io.Serializable;

import com.zzl.exception.IResponseEnum;

public class BaseException extends RuntimeException implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 134642474708101995L;

	private IResponseEnum iResponseEnum;
	
	private Object[] args;
	
	private String message;
	
	private Throwable cause;
	
	
	public BaseException(IResponseEnum iResponseEnum, Object[] args, String message) {
		this.iResponseEnum = iResponseEnum;
		this.args = args;
		this.message = message;
	}

	public BaseException(IResponseEnum iResponseEnum, Object[] args, String message, Throwable cause) {
		this.iResponseEnum = iResponseEnum;
		this.args = args;
		this.message = message;
		this.cause = cause;
	}

	public IResponseEnum getiResponseEnum() {
		return iResponseEnum;
	}

	public void setiResponseEnum(IResponseEnum iResponseEnum) {
		this.iResponseEnum = iResponseEnum;
	}

	public Object[] getArgs() {
		return args;
	}

	public void setArgs(Object[] args) {
		this.args = args;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Throwable getCause() {
		return cause;
	}

	public void setCause(Throwable cause) {
		this.cause = cause;
	}
	
	
}
