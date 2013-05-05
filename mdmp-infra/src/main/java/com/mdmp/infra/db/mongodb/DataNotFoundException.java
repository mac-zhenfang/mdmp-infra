/**
 * 
 */
package com.mdmp.infra.db.mongodb;

import com.mdmp.common.exception.MDMPException;

/**
 * @author zhefang
 * 
 */
public class DataNotFoundException extends MDMPException {

	public DataNotFoundException(String errorCode, Throwable cause) {
		super(errorCode, cause);
		// TODO Auto-generated constructor stub
	}

	public DataNotFoundException(String error) {
		super(error);
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 163565318812368753L;

}
