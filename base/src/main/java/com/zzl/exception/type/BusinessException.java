package com.zzl.exception.type;

import com.zzl.exception.IResponseEnum;

/**
 * 业务异常
 * @author zyf
 *
 */
public class BusinessException extends BaseException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -9179428838766612579L;

	public BusinessException(IResponseEnum iResponseEnum, Object[] args, String message) {
		super(iResponseEnum, args, message);
	}

	public BusinessException(IResponseEnum iResponseEnum, Object[] args, String message, Throwable cause) {
		super(iResponseEnum, args, message, cause);
	}

}
