package com.pear.commons.selector.mybatis.util;

import org.apache.log4j.Logger;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


public class ClassUtil {

	private static final Logger log = Logger.getLogger(ClassUtil.class) ;

	public static boolean isValueType(Class<?> clz) {
		try {
			if (clz != null) {
				if (clz.isPrimitive() || Number.class.isAssignableFrom(clz)
						|| Character.class.isAssignableFrom(clz)
						|| String.class.isAssignableFrom(clz)
						|| Date.class.isAssignableFrom(clz)
						|| Boolean.class.isAssignableFrom(clz)) {
					return true;
				} else {
					return false;
				}
			} else {
				throw new RuntimeException("Class can't be null");
			}
		} catch (Exception e) {
			log.error("���ж��Ƿ���ֵ���͵�ʱ��������쳣��" , e) ;
			return false;
		}
	}


	public static Object newInstance(String className) {
		Object result = null;
		try {
			result = newInstance(Class.forName(className));
		} catch (Exception e) {
			RuntimeException ex = new RuntimeException();
			ex.initCause(e);
			throw ex;
		}
		return result;
	}

	@SuppressWarnings("rawtypes")
	public static Object newInstance(Class c) {
		Object result = null;
		try {
			result = c.newInstance();
		} catch (Exception e) {
			RuntimeException ex = new RuntimeException();
			ex.initCause(e);
			throw ex;
		}
		return result;
	}


	@SuppressWarnings("rawtypes")
	public static Field getField(Class clz, String fieldName) {
		return getField(clz, fieldName, true);
	}

	/**
	 * ����Field
	 */
	private final static Map<String,Field> fieldCache =  new ConcurrentHashMap<String,Field>();

	@SuppressWarnings("rawtypes")
	public static Field getField(Class clz, String fieldName, boolean exception) {
		String key = clz.getName()+" - " +fieldName;
		Field f = fieldCache.get(key);
		if (f != null) {
			return f;
		}

		for (; clz != Object.class ; clz = clz.getSuperclass()){
			try {
				if (!Object.class.getName().equals(clz.getName())) {
					Field field = clz.getDeclaredField(fieldName);
					fieldCache.put(key, field);
					return field;
				}
			} catch (NoSuchFieldException e) {
				//DO NOTHING
			}
		}

		if(exception){
			throw new RuntimeException("no such field in " + clz.getName());
		}
		return null;
	}

	@SuppressWarnings("rawtypes")
	public static Type[] getActualTypes(Type type) {
		if (type instanceof ParameterizedType) {
			ParameterizedType t = (ParameterizedType) type;
			Type[] types = t.getActualTypeArguments();
			if (ObjectUtil.isEmpty(types)) {
				throw new RuntimeException(((Class) type).getName() + " not have ActualType.");
			}
			return types;
		}else if(type instanceof Class){
			Type[] types = null;
			if(isCGLibProxy((Class) type)){//�Ƿ���CGLib�������
				Class proxyType = (Class) ((Class) type).getGenericSuperclass(); //������
				types = ((ParameterizedType) proxyType.getGenericSuperclass()).getActualTypeArguments();
			}else{
				types = ((ParameterizedType) ((Class) type).getGenericSuperclass()).getActualTypeArguments();
			}
			if (ObjectUtil.isEmpty(types)) {
				throw new RuntimeException(((Class) type).getName() + " not have ActualType.");
			}
			return types;
		} else {
			throw new RuntimeException("type error.");
		}
	}

	@SuppressWarnings("rawtypes")
	public static boolean isCGLibProxy(Class type){
		//FIXME ���ַ�������, ���ǱȽ���
    	return type.getName().contains("Enhancer");
    }


	@SuppressWarnings("unchecked")
	public static <T> Class<T> getActualType(Type type) {
		return (Class<T>) getActualTypes(type)[0];
	}

	@SuppressWarnings("rawtypes")
	public static boolean actualTypeHasProperty(Class clz, String propertyName){
		try {
			Field field = getField(getActualType(clz), propertyName ,false);
			if (field != null) {
				return true;
			}
		} catch (Exception e) {
			log.error("�ڷ��Ͷ������Ƿ���ָ�����Եķ����г������쳣��", e) ;
		}
		return false;
	}

	@SuppressWarnings("rawtypes")
	public static boolean hasProperty(Class clz, String propertyName){
		try {
			Field f = getField(clz, propertyName,false);
			if (f != null) {
				return true;
			}
		} catch (Exception e) {
			log.error("���Ƿ���ָ�����Եķ����г������쳣��" , e) ;

		}
		return false;
	}

    public static boolean isProperty(Class clz , String property) {
        try {
            PropertyDescriptor descriptor = getProperty(Introspector.getBeanInfo(clz) , property);
            if (descriptor != null) {
                return true;
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

	@SuppressWarnings("rawtypes")
	public static Class getPropertyType(BeanInfo beanInfo, String property){
		return getProperty(beanInfo, property).getPropertyType();
	}

	public static PropertyDescriptor getProperty(BeanInfo beanInfo, String property){
		PropertyDescriptor[] propertys = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : propertys) {
			if(propertyDescriptor.getName().equals(property) && !"class".equals(property)){
				return propertyDescriptor;
			}
		}
		return null;
	}
	/**
	 * ��ȡ�����ֶ�
	 * @param beanClass
	 * @param property
	 * @return
	 * @author yangz
	 * @date 2012-10-12 ����04:52:20
	 */
	public static PropertyDescriptor getProperty(Class<?> beanClass, String property){
		try {
			return getProperty(Introspector.getBeanInfo(beanClass), property);
		} catch (IntrospectionException e) {
			throw new RuntimeException(e);
		}
	}
	/**
	 * ��ȡ����ֵ
	 * @param property
	 * @return
	 * @author yangz
	 * @date 2012-10-12 ����04:55:14
	 */
	public static Object readProperty(Object obj, String property){
		try {
			return getProperty(obj.getClass(), property).getReadMethod().invoke(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
