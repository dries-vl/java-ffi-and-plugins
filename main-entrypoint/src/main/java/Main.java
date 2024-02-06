import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) throws Exception {
        String jarPath = "plugins/welcome-plugin-1.0-SNAPSHOT.jar"; // Adjust this path
        String className = "plugin4j.WelcomeGreeting";
        String methodName = "getGreeting";

        // Load and invoke method for the first time
        Class<?> myClass = loadClass(jarPath, className);
        String str = (String) invokeStaticMethod(myClass, methodName);
        System.out.println(str);

        Thread.sleep(10000);

        // If you need to reload the class (e.g., after updating the JAR), create a new JarClassLoader instance
        myClass = loadClass(jarPath, className);
        String str2 = (String) invokeStaticMethod(myClass, methodName);
        System.out.println(str2);
    }

    private static Class<?> loadClass(String jarPath, String className) throws ClassNotFoundException {
        JarClassLoader loader = new JarClassLoader(jarPath);
        return loader.loadClass(className);
    }

    private static Object invokeStaticMethod(Class<?> clazz, String methodName) throws Exception {
        Method method = clazz.getDeclaredMethod(methodName);
        method.setAccessible(true); // If the method is not public
        return method.invoke(null); // null for static methods
    }
}
