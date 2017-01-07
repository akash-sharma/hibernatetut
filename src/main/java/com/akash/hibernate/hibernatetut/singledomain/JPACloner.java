package com.akash.hibernate.hibernatetut.singledomain;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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

@SuppressWarnings({ "rawtypes", "unchecked" })
public class JPACloner {

	private static final String JPA_ID_ANNOTATION = "javax.persistence.Id";

	public static void getJPAClone(Object object, List<Class<?>> classesToIgnore) throws Exception {
		Set<EntityIdInfo> exploredObjects = new HashSet<EntityIdInfo>();
		exploreObject(object, exploredObjects, classesToIgnore);
	}

	private static void exploreObject(Object object, Set<EntityIdInfo> exploredObjects, List<Class<?>> classesToIgnore)
			throws Exception {
		Class clazz = getClass(object);
		Object primaryKey = getPrimaryKey(object);
		if (primaryKey == null) {
			return;
		}
		EntityIdInfo entityIdInfo = new EntityIdInfo(object.getClass().getName(), primaryKey);
		boolean newObjectAdded = exploredObjects.add(entityIdInfo);
		if (isEntity(clazz) && newObjectAdded) {
			if (clazz.getAnnotation(PrimaryKeyJoinColumn.class) != null) {
				for (Field field : clazz.getSuperclass().getDeclaredFields()) {
					if (field.getAnnotation(Id.class) != null || field.getAnnotation(Version.class) != null) {
						field.setAccessible(true);
						field.set(object, null);
					} else if (!isBaseProperty(field) && field.getAnnotation(Transient.class) == null) {
						String propertyName = Character.toUpperCase(field.getName().charAt(0))
								+ field.getName().substring(1);
						Object objectToExplore = (Object) clazz.getMethod("get" + propertyName).invoke(object);
						objectToExplore = HibernateUtils.initialize(objectToExplore);

						if (objectToExplore != null && objectToExplore instanceof Collection) {
							Collection objectToExploreColl = (Collection) objectToExplore;
							for (Object objToExplore : objectToExploreColl) {
								if (!exploredObjects.contains(objToExplore)
										&& !classesToIgnore.contains(getClass(objToExplore))) {
									exploreObject(objToExplore, exploredObjects, classesToIgnore);
								}
							}
						} else {
							if (objectToExplore != null && !exploredObjects.contains(objectToExplore)
									&& !classesToIgnore.contains(getClass(objectToExplore))) {
								exploreObject(objectToExplore, exploredObjects, classesToIgnore);
							}
						}
					}
				}
			}
			for (Field field : clazz.getDeclaredFields()) {
				if (field.getAnnotation(Id.class) != null || field.getAnnotation(Version.class) != null) {
					field.setAccessible(true);
					field.set(object, null);
				} else if (!isBaseProperty(field) && field.getAnnotation(Transient.class) == null) {
					String propertyName = Character.toUpperCase(field.getName().charAt(0))
							+ field.getName().substring(1);
					Object objectToExplore = (Object) clazz.getMethod("get" + propertyName).invoke(object);
					objectToExplore = HibernateUtils.initialize(objectToExplore);

					if (objectToExplore != null && objectToExplore instanceof Collection) {
						Collection objectToExploreColl = (Collection) objectToExplore;
						for (Object objToExplore : objectToExploreColl) {
							if (!exploredObjects.contains(objToExplore)
									&& !classesToIgnore.contains(getClass(objToExplore))) {
								exploreObject(objToExplore, exploredObjects, classesToIgnore);
							}
						}
					} else {
						if (objectToExplore != null && !exploredObjects.contains(objectToExplore)
								&& !classesToIgnore.contains(getClass(objectToExplore))) {
							exploreObject(objectToExplore, exploredObjects, classesToIgnore);
						}
					}
				}
			}
		}
	}

	private static Object getPrimaryKey(Object obj) {
		if (obj == null) {
			return null;
		}
		List<Field> fields = getAllFields(obj.getClass());
		for (Field field : fields) {
			field.setAccessible(true);
			for (Annotation annotation : field.getAnnotations()) {
				if (annotation.annotationType().getName().equals(JPA_ID_ANNOTATION)) {
					field.setAccessible(true);
					try {
						return field.get(obj);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		return null;
	}

	private static List<Field> getAllFields(Class<?> clazz) {
		List<Field> fields = new ArrayList<Field>(Arrays.asList(clazz.getDeclaredFields()));
		Class<?> currentClass = clazz;
		while (true) {
			currentClass = currentClass.getSuperclass();
			if (currentClass.getSuperclass() == null) {
				break;
			}
			fields.addAll(new ArrayList<Field>(Arrays.asList(currentClass.getDeclaredFields())));
		}
		return fields;
	}

	private static Class<? extends Object> getClass(Object entity) {
		if (entity instanceof HibernateProxy) {
			return ((HibernateProxy) entity).getHibernateLazyInitializer().getPersistentClass();
		}
		return entity.getClass();
	}

	private static boolean isEntity(Class clazz) throws Exception {
		if (clazz.getAnnotation(Entity.class) == null && clazz.getAnnotation(Embeddable.class) == null) {
			return false;
		}
		return true;
	}

	private static boolean isBaseProperty(Field f) {
		return f.getAnnotation(ManyToOne.class) == null && f.getAnnotation(OneToOne.class) == null
				&& f.getAnnotation(OneToMany.class) == null && f.getAnnotation(ManyToMany.class) == null
				&& f.getAnnotation(Embedded.class) == null && f.getAnnotation(ElementCollection.class) == null;
	}

	private static class EntityIdInfo {
		private final String className;
		private final Object id;

		public EntityIdInfo(String className, Object id) {
			this.className = className;
			this.id = id;
		}

		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((className == null) ? 0 : className.hashCode());
			result = prime * result + ((id == null) ? 0 : id.hashCode());
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			EntityIdInfo other = (EntityIdInfo) obj;
			if (className == null) {
				if (other.className != null)
					return false;
			} else if (!className.equals(other.className))
				return false;
			if (id == null) {
				if (other.id != null)
					return false;
			} else if (!id.equals(other.id))
				return false;
			return true;
		}
	}
}

class HibernateUtils {

	public static <T> T initialize(T entity) {
		if (entity == null) {
			return null;
		}
		if (entity instanceof HibernateProxy) {
			Hibernate.initialize(entity);
			entity = (T) ((HibernateProxy) entity).getHibernateLazyInitializer().getImplementation();

		} else if (entity instanceof PersistentCollection) {
			if (!((PersistentCollection) entity).wasInitialized()) {
				((PersistentCollection) entity).forceInitialization();
			}
		}
		return entity;
	}
}
