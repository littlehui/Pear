package com.pear.commons.selector.mybatis.util;

import org.apache.ibatis.logging.Log;
import org.apache.ibatis.logging.LogFactory;

import java.beans.*;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * 
 * @Description: ���󹤾���
 * @Company : cyou
 * @author yangz
 * @date   2012-9-26 ����02:53:41
 * @version V1.0
 */
public class ObjectUtil {

	private static final Log log = LogFactory.getLog(ObjectUtil.class);

	/**
	 * One of the following conditions isEmpty = true, else = false :
	 * ��������һ��������Ϊ��<br>
	 * 1. null : ��<br>
	 * 2. "" or " " : �մ�<br>
	 * 3. no item in [] or all item in [] are null : ������û��Ԫ��, ����������Ԫ��Ϊ��<br>
	 * 4. no item in (Collection, Map, Dictionary) : ������û��Ԫ��<br>
	 *
	 * @param value
	 * @return
	 * @author yangz
	 * @date May 6, 2010 4:21:56 PM
	 */
	public static boolean isEmpty(Object value) {
		if (value == null) {
			return true;
		}
		if ((value instanceof String)
				&& ((((String) value).trim().length() <= 0) || "null".equalsIgnoreCase((String) value))) {
			return true;
		}
		if ((value instanceof Object[]) && (((Object[]) value).length <= 0)) {
			return true;
		}
		if (value instanceof Object[]) { // all item in [] are null :
			// ����������Ԫ��Ϊ��
			Object[] t = (Object[]) value;
			for (int i = 0; i < t.length; i++) {
				if (t[i] != null) {
					if(t[i] instanceof String){
						if(((String) t[i]).trim().length() > 0 || "null".equalsIgnoreCase((String) t[i])){
							return false;
						}
					}else{
						return false;
					}
				}
			}
			return true;
		}
		if ((value instanceof Collection) && ((Collection<?>) value).size() <= 0) {
			return true;
		}
		if ((value instanceof Dictionary) && ((Dictionary<?,?>) value).size() <= 0) {
			return true;
		}
		if ((value instanceof Map) && ((Map<?,?>) value).size() <= 0) {
			return true;
		}
		return false;
	}

    /**
     * list<String>�п�
     * @author littlehui
     * @param list
     * @return
     */
    public static boolean isEmpty(List<String> list) {
        if (list == null) {
            return true;
        }
        if (list.size() == 0) {
            return true;
        }
        List<String> newStrs = new ArrayList<String>();
        for (String str : list) {
            if (!StringUtils.isEmpty(str)) {
                newStrs.add(str);
            }
        }
        if (newStrs.size() > 0) {
            return false;
        }
        return true;
    }


	public static Object copyProperties(Object source, Object target){
		try{
			Map<String,Object> sourceMap = toMap(source);
			target = writeBean(target, sourceMap);
		} catch(Exception e) {
			log.debug("ObjectUtil copyProperties bad for src :" + source.toString() + " dest: " + target.toString());
			throw new RuntimeException("ObjectUtil copyProperties bad for src :"+ source.toString()+" dest: "+target.toString());
		}
		return target;
	}

	public static <M,T> T convertObj(M obj , Class<T> clazz) {
		try {
			T instance = clazz.newInstance();
			ObjectUtil.copyProperties(obj, instance);
			return instance;
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		throw new RuntimeException("convert obj error! source class :"+obj.getClass()+",target :"+clazz);
	}

	public static <M,T> List<T> convertList(List<M> objList , Class<T> clazz) {
		List<T> list = new ArrayList<T>();
		for (M m : objList) {
			list.add(convertObj(m, clazz));
		}
		return list;
	}

    public static <M,T> List<T> convertList(List<M> objList ,Converter<M,T> converter) {
        List<T> list = new ArrayList<T>();
        for (M m : objList) {
            list.add(converter.convert(m));
        }
        return list;
    }

    public static interface  Converter<H,Q>{
        public Q convert(H h);
    }


	/**
	 * �����Ƿ���ֵ����
	 * @param obj
	 * @return
	 * @author yangz
	 * @date 2012-9-26 ����03:01:44
	 */
	public static boolean isValueType(Object obj){
		if (obj instanceof String || obj instanceof Number || obj instanceof Boolean || obj instanceof Character || obj instanceof Date) {
			return true;
		}else{
			return false;
		}
	}
	/**
	 * �Ƿ��Ǽ���
	 * @param obj
	 * @return
	 * @author yangz
	 * @date 2012-9-26 ����03:50:55
	 */
	public static boolean isCollection(Object obj){
		if (obj instanceof Collection<?>) {
			return true;
		}else{
			return false;
		}
	}

    /**
     * ת����ʵ��
     * @param clazz
     * @param mapList
     * @param <M>
     * @return
     */
    public static <M> List<M> toBeanList(Class<M> clazz , List<Map<String,Object>> mapList) {
        List<M> objectList = new ArrayList<M>();
        for (Map<String,Object> map : mapList) {
            objectList.add( toBean(clazz, map) );
        }
        return objectList;
    }

    public static  <M> M toBean(Class<M> type, Map<String, Object> map) {
        M obj = null;
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type); // ��ȡ������
			obj = type.newInstance();
			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
			for (int i = 0; i< propertyDescriptors.length; i++) {
			    PropertyDescriptor descriptor = propertyDescriptors[i];
			    String propertyName = descriptor.getName();
			    if (map.containsKey(propertyName)) {
			        Object value = map.get(propertyName);
			        Object[] args = new Object[1];
			        args[0] = value;
					if (descriptor.getWriteMethod() != null && value != null) {
						if (!descriptor.getWriteMethod().getParameterTypes()[0].equals(value.getClass())) {
							throw new RuntimeException("ӳ����ֶ�["+propertyName+"]���Ͳ�ƥ�䣺 ��Ҫ" + descriptor.getWriteMethod().getParameterTypes()[0]
									+ ",ʵ��" + value.getClass());
						}
					}
			        descriptor.getWriteMethod().invoke(obj, args);
			    }
			}
		} catch (Exception e) {
			RuntimeException ex = new RuntimeException("convent map to object error");
			ex.initCause(e);
			throw ex;
		}
        return obj;
    }

	public static Object writeBean(Object desObject, Map<String, Object> map) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(desObject.getClass()); // ��ȡ������
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				if (map.containsKey(propertyName)) {
					Object value = map.get(propertyName);
					Object[] args = new Object[1];
					args[0] = value;
					if (descriptor.getWriteMethod() != null && value != null) {
						if (!descriptor.getWriteMethod().getParameterTypes()[0].equals(value.getClass())) {
							throw new RuntimeException("ӳ����ֶ�[" + propertyName + "]���Ͳ�ƥ�䣺 ��Ҫ" + descriptor.getWriteMethod().getParameterTypes()[0]
									+ ",ʵ��" + value.getClass());
						}
					}
					descriptor.getWriteMethod().invoke(desObject, args);
				}
			}
		} catch (Exception e) {
			RuntimeException ex = new RuntimeException("convent map to object error");
			ex.initCause(e);
			throw ex;
		}
		return desObject;
	}
		/**
         * ��mapֵת���ɶ���, �޵ݹ�Ƕ��
         * @param type
         * @param map
         * @return
         * @author yangz
         * @date 2012-9-26 ����03:39:54
         */
    public static  <M> M addToBean(M obj ,Class<M> type, Map<String, Object> map) {
        try {
            BeanInfo beanInfo = Introspector.getBeanInfo(type);
            PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
            for (int i = 0; i< propertyDescriptors.length; i++) {
                PropertyDescriptor descriptor = propertyDescriptors[i];
                String propertyName = descriptor.getName();
                if (map.containsKey(propertyName)) {
                    Object value = map.get(propertyName);
                    Object[] args = new Object[1];
                    args[0] = value;
                    descriptor.getWriteMethod().invoke(obj, args);
                }
            }
        } catch (Exception e) {
            RuntimeException ex = new RuntimeException("convent map to object error");
            ex.initCause(e);
            throw ex;
        }
        return obj;
    }

	public static Map<String, Object> toMap(Object bean){
        Map<String, Object> returnMap;
		try {
			Class<?> type = bean.getClass();
			returnMap = new HashMap<String, Object>();
			BeanInfo beanInfo = Introspector.getBeanInfo(type);
			PropertyDescriptor[] propertyDescriptors =  beanInfo.getPropertyDescriptors();
			for (int i = 0; i< propertyDescriptors.length; i++) {
			    PropertyDescriptor descriptor = propertyDescriptors[i];
			    String propertyName = descriptor.getName();
			    if (!propertyName.equals("class")) {
			        Method readMethod = descriptor.getReadMethod();
			        Object result = readMethod.invoke(bean, new Object[0]);
			        if(ObjectUtil.isValueType(result) || result == null){
			        	returnMap.put(propertyName, result);
			        }else if(ObjectUtil.isCollection(result)){
			        	Collection<?> collectionResult = (Collection<?>)result;
						Collection collection = (Collection) result.getClass().newInstance();
			        	for (Object o : collectionResult) {
			        		 if(ObjectUtil.isValueType(o) || o == null){
			        			 collection.add(o);
			        		 }else{
			        			 collection.add(toMap(o));
			        		 }
						}
			        	returnMap.put(propertyName, collection);
			        }else if(result.getClass().isArray()){
			        	throw new RuntimeException("bean property can't be array");
			        }else{ //�Զ������
			        	returnMap.put(propertyName, toMap(result));
			        }
			    }
			}
		} catch (Exception e) {
			RuntimeException ex = new RuntimeException("convent object to map error");
			ex.initCause(e);
			throw ex;
		}
        return returnMap;
    }

	public static <T> List<T> arrayToList(T... objects) {
		List<T> list = new ArrayList<T>();
		for (T obj : objects) {
			if (obj == null) {
				continue;
			}

			list.add(obj);
		}
		return list;
	}

	public static <T> List<T> arrayToListExcludeEmpty(T... objects) {
		List<T> list = new ArrayList<T>();
		for (T obj : objects) {
			if (obj == null) {
				continue;
			}
			if (obj instanceof String) {
				if (StringUtils.isEmpty((String) obj)) {
					continue;
				}
			}

			list.add(obj);
		}
		return list;
	}

	public static <M> List<M> toBeanExtList(Class<M> clazz, List<Map<String, Object>> mapList) {
		List<M> objectList = new ArrayList<M>();
		for (Map<String, Object> map : mapList) {
			objectList.add(toBeanExt(clazz, map));
		}
		return objectList;
	}

	/**
	 * ��mapֵת���ɶ���, �޵ݹ�Ƕ��
	 *
	 * @param type
	 * @param map
	 * @return
	 * @author yangz
	 * @date 2012-9-26 ����03:39:54
	 */
	public static <M> M toBeanExt(Class<M> type, Map<String, Object> map) {
		M obj = null;
		String property = "";
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(type); // ��ȡ������
			obj = type.newInstance();
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (int i = 0; i < propertyDescriptors.length; i++) {
				PropertyDescriptor descriptor = propertyDescriptors[i];
				String propertyName = descriptor.getName();
				property = propertyName;
				if (map.containsKey(propertyName)) {
					Object value = map.get(propertyName);
					Object[] args = new Object[1];
					args[0] = value;
					if (descriptor.getWriteMethod() != null && value != null) {
						if (!descriptor.getWriteMethod().getParameterTypes()[0].equals(value.getClass())) {
							throw new RuntimeException("ӳ����ֶ�["+propertyName+"]���Ͳ�ƥ�䣺 ��Ҫ" + descriptor.getWriteMethod().getParameterTypes()[0]
									+ ",ʵ��" + value.getClass());
						}
					}
					descriptor.getWriteMethod().invoke(obj, args);
					continue;
				}
				String dbField = toDbField(propertyName).toLowerCase();
				if (map.containsKey(dbField)) {
					Object value = map.get(dbField);
					Object[] args = new Object[1];
					args[0] = value;
					if (descriptor.getWriteMethod() != null && value != null) {
						if (!descriptor.getWriteMethod().getParameterTypes()[0].equals(value.getClass())) {
							throw new RuntimeException("ӳ����ֶ�["+propertyName+"]���Ͳ�ƥ�䣺 ��Ҫ" + descriptor.getWriteMethod().getParameterTypes()[0]
									+ ",ʵ��" + value.getClass());
						}
					}
					descriptor.getWriteMethod().invoke(obj, args);
				}
			}
		} catch (Exception e) {
			RuntimeException ex = new RuntimeException("convent map to object error , prop:" + property);
			ex.initCause(e);
			throw ex;
		}
		return obj;
	}

	/**
	 * // Լ����sysId > SYS_ID
	 *
	 * @param propName
	 * @return
	 */
	public static String toDbField(String propName) {
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < propName.length(); i++) {
			char c = propName.charAt(i);
			if (Character.isUpperCase(c)) {
				builder.append('_').append(c);
			} else {
				builder.append(Character.toUpperCase(c));
			}
		}
		return builder.toString();
	}


	public static <T> T setProperty(T t, String propertyName, Object property) {
		try {
			PropertyDescriptor propertyDescriptor = getProperty(Introspector.getBeanInfo(t.getClass()), propertyName);
			Method method = propertyDescriptor.getWriteMethod();
			method.invoke(t, property);
		} catch (IntrospectionException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return t;
	}

	public static PropertyDescriptor getProperty(BeanInfo beanInfo, String property) {
		PropertyDescriptor[] propertys = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor propertyDescriptor : propertys) {
			if (propertyDescriptor.getName().equals(property) && !"class".equals(property)) {
				return propertyDescriptor;
			}
		}
		return null;
	}

	public static String getProperty(Object obj, String property) {
		BeanInfo beanInfo = null;
		try {
			beanInfo = Introspector.getBeanInfo(obj.getClass());
		} catch (IntrospectionException e) {
			e.printStackTrace();
			return null;
		}
		PropertyDescriptor propertyDescriptor = getProperty(beanInfo, property);
		Method readMethod = propertyDescriptor.getReadMethod();
		boolean accesAble = readMethod.isAccessible();
		readMethod.setAccessible(true);
		String value = null;
		try {
			Object oValue = readMethod.invoke(obj);
			if (oValue != null) {
				value = oValue + "";
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		} finally {
			readMethod.setAccessible(accesAble);
		}
		return value;
	}

	public static List<Field> getAnnotaionFields(Class<?> tclass, Class<? extends Annotation> annotationClass) {
		List<Field> needField = new ArrayList<Field>();
		Field[] fields = tclass.getDeclaredFields();
		for (Field field : fields) {
			if (field.isAnnotationPresent(annotationClass)) {
				needField.add(field);
			}
		}
		return needField;
	}
}
