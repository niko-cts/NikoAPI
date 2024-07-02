package chatzis.nikolas.mc.nikoapi.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This is a utility class.
 * All needed reflection methods are located in here.
 */
public class ReflectionHelper {

    private static final Logger LOG = Logger.getLogger(ReflectionHelper.class.getName());

    /**
     * This class is a util class, so it's not needed to instantiate it.
     */
    private ReflectionHelper() {
        throw new UnsupportedOperationException("This is a util class.");
    }

    /**
     * Create a new instance of a class
     *
     * @param clazz Class<T> - Class to instantiate
     * @return <T> - Instance of Class
     */
    public static <T> T newInstance(Class<T> clazz) {
        try {
            return clazz.newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            LOG.log(Level.WARNING, e.getMessage());
            return null;
        }
    }

    /**
     * Change or set a variable of an instance
     *
     * @param instance Object - Instance to manipulate
     * @param name     String - Name of field (variable)
     * @param value    Object - object to set the variable to
     */
    public static void set(Object instance, String name, Object value) {
        set(instance.getClass(), instance, name, value);
    }

    public static <T> T get(Object instance, String name) {
        return get(instance.getClass(), instance, name);
    }

    /**
     * Change or set a variable of an instance
     *
     * @param clazz    Class<?> - Class of instance
     * @param instance Object - Instance to manipulate
     * @param name     String - Name of field (variable)
     * @param value    Object - object to set the variable to
     */
    public static void set(Class<?> clazz, Object instance, String name, Object value) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            field.set(instance, value);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            LOG.log(Level.WARNING, "Error setting {0} in {1} with message: {2}", new String[]{name, clazz.getName(), e.getMessage()});
        }
    }

    /**
     * Get the field of an instance
     *
     * @param clazz    Class<?> - Class of instance
     * @param instance Object - instance of Class
     * @param name     String - Name of field (variable)
     * @return Object - Field to get
     */
    @SuppressWarnings("unchecked")
    public static <T> T get(Class<?> clazz, Object instance, String name) {
        try {
            Field field = clazz.getDeclaredField(name);
            field.setAccessible(true);
            return (T) field.get(instance);
        } catch (NoSuchFieldException | IllegalAccessException | NullPointerException | ClassCastException e) {
            LOG.log(Level.SEVERE, "Error getting '{0}' in {1} with message: {2}", new String[]{name, clazz.getName(), e.getMessage()});
            LOG.log(Level.SEVERE, "Error is", e);
        }
        return null;
    }

    /**
     * Invoking a method
     *
     * @param clazz      Class<?> - Class of instance
     * @param instance   Object - instance of Class
     * @param methodName String - Name of method
     * @return Object - Object to from method
     */
    public static Object invokeMethod(Class<?> clazz, String methodName, Object instance) {
        try {
            Method method = clazz.getDeclaredMethod(methodName);
            method.setAccessible(true);
            return method.invoke(instance);
        } catch (IllegalAccessException | NullPointerException | InvocationTargetException |
                 NoSuchMethodException e) {
            LOG.log(Level.SEVERE, "Error getting through method '{0}' in {1} with message: {2}", new String[]{methodName, clazz.getName(), e.getMessage()});
            LOG.log(Level.SEVERE, "Error is", e);
        }
        return null;
    }
}
