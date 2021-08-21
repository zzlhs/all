package com.zzl.exception;

public class Main {
	
	public static void main(String[] args) {
		
		ResponseEnum.BAD_LICENCE_TYPE.assertNotNull(null);
	}
}
