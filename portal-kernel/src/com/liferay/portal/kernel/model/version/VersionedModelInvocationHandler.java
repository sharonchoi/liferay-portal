/**
 * Copyright (c) 2000-present Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.portal.kernel.model.version;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import java.util.Map;

/**
 * @author Preston Crary
 */
public class VersionedModelInvocationHandler implements InvocationHandler {

	public VersionedModelInvocationHandler(
		VersionModel<?> versionModel, Map<Method, Method> methods) {

		_versionModel = versionModel;
		_methods = methods;
	}

	@Override
	public Object invoke(Object proxy, Method method, Object[] arguments)
		throws Throwable {

		Method mappedMethod = _methods.get(method);

		if (mappedMethod == null) {
			String methodName = method.getName();

			if (methodName.equals("getHeadId")) {
				return -_versionModel.getVersionedModelId();
			}

			if (methodName.equals("isDraft")) {
				return false;
			}

			throw new UnsupportedOperationException(methodName);
		}

		try {
			return mappedMethod.invoke(_versionModel, arguments);
		}
		catch (InvocationTargetException ite) {
			throw ite.getTargetException();
		}
	}

	private final Map<Method, Method> _methods;
	private final VersionModel<?> _versionModel;

}