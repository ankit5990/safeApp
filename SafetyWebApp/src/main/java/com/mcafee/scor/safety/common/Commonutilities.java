package com.mcafee.scor.safety.common;

import java.util.Collection;

public class Commonutilities {
	public static <T> boolean isNullOrEmpty(Collection<T> collection){
		return (collection == null || collection.isEmpty());
	}
}
