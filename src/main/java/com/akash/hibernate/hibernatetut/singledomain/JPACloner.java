package com.akash.hibernate.hibernatetut.singledomain;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.Hibernate;
import org.hibernate.collection.spi.PersistentCollection;
import org.hibernate.proxy.HibernateProxy;
//import org.springframework.beans.BeanUtils;

/**
 * 
 * @author Prafulla Gupta
 *
 */
@SuppressWarnings({"rawtypes","unchecked"})
public class JPACloner {

	/**
	 * This method traverse the complete object graph and set the @Id and @Version fields with null. 
	 * This api modifies the object passed as an argument, so it is not doing the job of deep cloning.  
	 * @param object - Object you want to get cloned
	 * @param classesToIgnore - Classes whose id you don't want to set to null 
	 * @throws Exception
	 */
	public static void getJPAClone(Object object,  List<Class<?>> classesToIgnore) throws Exception{
		List<Object> exploredObjects = new ArrayList<Object>();
		exploreObject(object, exploredObjects, classesToIgnore);		
	}
	
	/**
	 * This method traverse the complete object graph and deep clones it to create a new object graph.	 
	 * This method is not thread safe and doesn't expect any change in the passed object during the processing   
	 * @param object - Object you want to get cloned. If the object is lazily loaded,
	 *                 then loading of object and calling this method should be in same transaction
	 * @param classesToIgnore - Classes whose references you don't want to clone 
	 * @throws Exception
	 */
	/*public static Object deepClone(Object object,  List<Class> classesToIgnore) throws Exception{
		Map<Object, Object> clonedObjects = new HashMap<Object, Object>();
		if(classesToIgnore == null){
			classesToIgnore = new ArrayList<Class>();
		}
		Object clonedObject = cloneObject(object, clonedObjects, classesToIgnore);
		return clonedObject;
	}*/
	
//	private static Object cloneObject(Object source, Map<Object, Object> clonedObjects, List<Class> classesToIgnore) throws Exception{
//		Object target = null;
//		try{
//		    target =getClass(source).getConstructor().newInstance();
//		}catch(NoSuchMethodException ex){
//			//when default constructor dosent exist,below line is mainly added for BigDecimal which has a string constructor
//			 target = getClass(source).getConstructor(String.class).newInstance(source.toString());
//		}
//		clonedObjects.put(source, target);
//		
//		PropertyDescriptor[] propDescriptors = BeanUtils.getPropertyDescriptors(getClass(source));
//		for(PropertyDescriptor propDescriptor : propDescriptors){
//			if (propDescriptor.getWriteMethod() != null && propDescriptor.getReadMethod() != null) {
//				Method readMethod = propDescriptor.getReadMethod();
//				if (!Modifier.isPublic(readMethod.getDeclaringClass().getModifiers())) {
//					readMethod.setAccessible(true);
//				}
//				Object sourceValue = readMethod.invoke(source);
//				if(sourceValue !=  null){
//					if(sourceValue instanceof Collection){
//						Collection colSrcVals = (Collection) sourceValue;
//						
//						for(Object colSrcVal : colSrcVals){
//							Object colTargetVal = getClonedValue(clonedObjects, classesToIgnore, colSrcVal);
//							//if target collection is null initialize it first
//							initializeCollection(target, propDescriptor, colSrcVals);														
//							
//							addObjectToCollection(target, propDescriptor, colTargetVal);
//						}
//						
//					}else if(sourceValue instanceof Map){
//						Map mapSrcVals = (Map) sourceValue;
//						Set<Map.Entry> entrySet = mapSrcVals.entrySet();
//						for(Map.Entry mapEntry : entrySet){
//							Object key = mapEntry.getKey();
//							Object value = mapEntry.getValue();
//							Object keyClone = getClonedValue(clonedObjects, classesToIgnore, key);
//							Object valueClone = getClonedValue(clonedObjects, classesToIgnore, value);
//							
//							initializeCollection(target, propDescriptor, mapSrcVals);
//							
//							addObjectToMap(target, propDescriptor, keyClone, valueClone);
//						}						
//						
//					}else if(!isJavaDefined(sourceValue)){
//						Object valTarget = null;
//						if(!classesToIgnore.contains(getClass(sourceValue)) && !clonedObjects.containsKey(sourceValue)){
//							valTarget = cloneObject(sourceValue, clonedObjects, classesToIgnore);
//							clonedObjects.put(sourceValue, valTarget);							
//						}else{
//							valTarget = clonedObjects.get(sourceValue);
//						}
//						populateValueInTarget(target, propDescriptor, valTarget);
//						
//					}else{
//						populateValueInTarget(target, propDescriptor, sourceValue);						
//					}
//				}				
//			}
//		}
//		return target;
//	}

//	private static void addObjectToMap(Object target, PropertyDescriptor propDescriptor, Object keyClone,
//			Object valueClone) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
//		PropertyDescriptor propCol = BeanUtils.getPropertyDescriptor(getClass(target),propDescriptor.getName());
//		Method readMethodCol = propCol.getReadMethod();
//		if (!Modifier.isPublic(readMethodCol.getDeclaringClass().getModifiers())) {
//			readMethodCol.setAccessible(true);
//		}
//		Map.class.getDeclaredMethod("put",Object.class,Object.class).invoke(readMethodCol.invoke(target), keyClone, valueClone);
//	}

//	private static Object getClonedValue(Map<Object, Object> clonedObjects, List<Class> classesToIgnore, Object colSrcVal) throws Exception {
//		Object colTargetVal = null;
//		if(!isJavaDefined(colSrcVal) && !classesToIgnore.contains(getClass(colSrcVal))){
//			if(!clonedObjects.containsKey(colSrcVal)){
//				colTargetVal = cloneObject(colSrcVal, clonedObjects, classesToIgnore);
//				clonedObjects.put(colSrcVal, colTargetVal);
//			}else{
//				colTargetVal = clonedObjects.get(colSrcVal);
//			}
//			
//		}else{
//			colTargetVal = colSrcVal;
//		}
//		return colTargetVal;
//	}

//	private static void initializeCollection(Object target, PropertyDescriptor propDescriptor, Object colSrcVals)
//			throws IllegalAccessException, InvocationTargetException, InstantiationException, NoSuchMethodException {
//		PropertyDescriptor propCol = BeanUtils.getPropertyDescriptor(getClass(target),propDescriptor.getName());
//		Method readMethodCol = propCol.getReadMethod();
//		if (!Modifier.isPublic(readMethodCol.getDeclaringClass().getModifiers())) {
//			readMethodCol.setAccessible(true);
//		}
//		if(readMethodCol.invoke(target) == null){
//			Object targetCol = getClass(colSrcVals).getConstructor().newInstance();
//			populateValueInTarget(target, propDescriptor, targetCol);	
//		}
//	}

	private static void populateValueInTarget(Object target, PropertyDescriptor propDescriptor, Object valTarget)
			throws IllegalAccessException, InvocationTargetException {
		Method writeMethod = propDescriptor.getWriteMethod();
		if (!Modifier.isPublic(writeMethod.getDeclaringClass().getModifiers())) {
			writeMethod.setAccessible(true);
		}
		writeMethod.invoke(target, valTarget);
	}
	
	private static boolean isJavaDefined(Object object){
		//	If you have methods in your enum, individual values of the enum can override them. If you override any of these methods it 
		//creates an anonymous class which is a subclass of the enum. isEnum() for that class returns false.
		//Thus, even though EnumClass.class.isEnum() returns true, 
		return getCanonicalName(object).startsWith("java.lang") 
				||getClass(object).isEnum() || object instanceof Enum<?>
				|| object instanceof Date 
				|| object instanceof java.sql.Date;
	}

//	private static void addObjectToCollection(Object target, PropertyDescriptor propDescriptor, Object colTargetVal) throws Exception {
//		PropertyDescriptor propCol = BeanUtils.getPropertyDescriptor(getClass(target),propDescriptor.getName());
//		Method readMethodCol = propCol.getReadMethod();
//		if (!Modifier.isPublic(readMethodCol.getDeclaringClass().getModifiers())) {
//			readMethodCol.setAccessible(true);
//		}
//		Collection.class.getDeclaredMethod("add",Object.class).invoke(readMethodCol.invoke(target), colTargetVal);
//	}
	
	private static void exploreObject(Object object, List<Object> exploredObjects, List<Class<?>> classesToIgnore) throws Exception{
		Class clazz = getClass(object);
		exploredObjects.add(object);
		if(isEntity(clazz)){
			if(clazz.getAnnotation(PrimaryKeyJoinColumn.class)!=null){
				for(Field field : clazz.getSuperclass().getDeclaredFields()){	
					if(field.getAnnotation(Id.class) != null || field.getAnnotation(Version.class) != null){
						field.setAccessible(true);
						field.set(object, null);										
					}
					else if(!isBaseProperty(field) && field.getAnnotation(Transient.class) == null){
						String propertyName = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
						Object objectToExplore = (Object)clazz.getMethod("get" + propertyName).invoke(object);
						objectToExplore = HibernateUtils.initialize(objectToExplore);
						
						if(objectToExplore != null && objectToExplore instanceof Collection){
							Collection objectToExploreColl = (Collection) objectToExplore;
							for(Object objToExplore : objectToExploreColl){
								if(!exploredObjects.contains(objToExplore) && !classesToIgnore.contains(getClass(objToExplore))){
									exploreObject(objToExplore, exploredObjects, classesToIgnore);
								}								
							}					
						}else{						
							if(objectToExplore != null && !exploredObjects.contains(objectToExplore) && !classesToIgnore.contains(getClass(objectToExplore))){
								exploreObject(objectToExplore, exploredObjects, classesToIgnore);
							}
						}
					}
				}
			}
			for(Field field : clazz.getDeclaredFields()){				
				if(field.getAnnotation(Id.class) != null || field.getAnnotation(Version.class) != null){
					field.setAccessible(true);
					field.set(object, null);										
				}
				else if(!isBaseProperty(field) && field.getAnnotation(Transient.class) == null){
					String propertyName = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
					Object objectToExplore = (Object)clazz.getMethod("get" + propertyName).invoke(object);
					objectToExplore = HibernateUtils.initialize(objectToExplore);
					
					if(objectToExplore != null && objectToExplore instanceof Collection){
						Collection objectToExploreColl = (Collection) objectToExplore;
						for(Object objToExplore : objectToExploreColl){
							if(!exploredObjects.contains(objToExplore) && !classesToIgnore.contains(getClass(objToExplore))){
								exploreObject(objToExplore, exploredObjects, classesToIgnore);
							}								
						}					
					}else{						
						if(objectToExplore != null && !exploredObjects.contains(objectToExplore) && !classesToIgnore.contains(getClass(objectToExplore))){
							exploreObject(objectToExplore, exploredObjects, classesToIgnore);
						}
					}
				}
			}
		}
	}

	private static String getCanonicalName(final Object object){
		String canonicalName = getClass(object).getCanonicalName();
		return canonicalName != null?canonicalName:"";
	}

	private static Class<? extends Object> getClass(Object entity) {
		   if (entity instanceof HibernateProxy) {
		       return ((HibernateProxy)entity).getHibernateLazyInitializer().getPersistentClass();
		   }
		   return entity.getClass();
	}	
	
	/**
	 * This method clone deeply with not treat ignored classes .
	 * @param object
	 * @param exploredObjects
	 * @param classesToIgnore
	 * @throws Exception
	 */
	@SuppressWarnings("unused")
	public static void deepCloneWithClassesToIgnore(Object object,List<Object> exploredObjects, List<Class> classesToIgnore) throws Exception{
		Class clazz = object.getClass();
		if(isEntity(clazz)){
			if(clazz.getAnnotation(PrimaryKeyJoinColumn.class)!=null){
				for(Field field : clazz.getSuperclass().getDeclaredFields()){	
					if(field.getAnnotation(Id.class) != null || field.getAnnotation(Version.class) != null){
						field.setAccessible(true);
						field.set(object, null);										
					}
					else if(!isBaseProperty(field) && field.getAnnotation(Transient.class) == null){
						String propertyName = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
						Object objectToExplore = (Object)clazz.getMethod("get" + propertyName).invoke(object);
						objectToExplore = HibernateUtils.initialize(objectToExplore);
						
						if(objectToExplore != null && objectToExplore instanceof Collection){
							Collection objectToExploreColl = (Collection) objectToExplore;
							for(Object objToExplore : objectToExploreColl){
								if(!exploredObjects.contains(objToExplore) && !classesToIgnore.contains(objToExplore.getClass())){
									deepCloneWithClassesToIgnore(objToExplore, exploredObjects, classesToIgnore);
								}								
							}					
						}else{						
							if(objectToExplore != null && !exploredObjects.contains(objectToExplore) && !classesToIgnore.contains(objectToExplore.getClass())){
								deepCloneWithClassesToIgnore(objectToExplore, exploredObjects, classesToIgnore);
							}
						}
					}
				}
			}
			for(Field field : clazz.getDeclaredFields()){	
				boolean needToIgnore =false;
				if(field.getAnnotation(Id.class) != null || field.getAnnotation(Version.class) != null){
					field.setAccessible(true);
					field.set(object, null);										
				}
				else if(!isBaseProperty(field) && field.getAnnotation(Transient.class) == null){
					String propertyName = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
					Object objectToExplore=null;
					if(isNeedToIgnore(field,classesToIgnore)){
					 objectToExplore = (Object)clazz.getMethod("get" + propertyName).invoke(object);
					}
					objectToExplore = HibernateUtils.initialize(objectToExplore);
					
					if(objectToExplore != null && objectToExplore instanceof Collection){
						Collection objectToExploreColl = (Collection) objectToExplore;
						for(Object objToExplore : objectToExploreColl){
							if(!exploredObjects.contains(objToExplore) && !classesToIgnore.contains(objToExplore.getClass())){
								deepCloneWithClassesToIgnore(objToExplore, exploredObjects, classesToIgnore);
							}								
						}					
					}else{						
						if(objectToExplore != null && !exploredObjects.contains(objectToExplore) && !classesToIgnore.contains(objectToExplore.getClass())){
							deepCloneWithClassesToIgnore(objectToExplore, exploredObjects, classesToIgnore);
						}
					}
				}
			}
		}
	}	

	private static boolean isNeedToIgnore(Field field, List<Class> classesToIgnore){
		boolean isIgnored =false;
		String propertyName = Character.toUpperCase(field.getName().charAt(0)) + field.getName().substring(1);
		for(Class claz : classesToIgnore){
			if(claz.getSimpleName().equals(propertyName)){
				isIgnored =true;
				break;
			}
		}
		return isIgnored;
	}
	
	private static boolean isEntity(Class clazz) throws Exception {		
		if(clazz.getAnnotation(Entity.class) == null && clazz.getAnnotation(Embeddable.class) == null){
			return false;
		}
		return true;
	}
	
	private static boolean isBaseProperty(Field f) {
		return f.getAnnotation(ManyToOne.class) == null &&
				f.getAnnotation(OneToOne.class) == null &&
				f.getAnnotation(OneToMany.class) == null &&
				f.getAnnotation(ManyToMany.class) == null &&
				f.getAnnotation(Embedded.class) == null &&
				f.getAnnotation(ElementCollection.class) == null;
	}	
}

class HibernateUtils {

	public static <T> T initialize(T entity) {
		if (entity == null) {
			return null;
		}
		if (entity instanceof HibernateProxy) {
			Hibernate.initialize(entity);
			entity = (T)((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();
			
		} else if (entity instanceof PersistentCollection) {
			if (!((PersistentCollection) entity).wasInitialized()) {
				((PersistentCollection) entity).forceInitialization();
			}
		}
		return entity;
	}
}
