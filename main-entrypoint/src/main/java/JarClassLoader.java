import java.io.*;
import java.util.jar.*;
import java.util.zip.*;

public class JarClassLoader extends ClassLoader {
    private String jarPath;

    public JarClassLoader(String jarPath) {
        this.jarPath = jarPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            byte[] classData = loadClassData(name);
            if (classData != null) {
                return defineClass(name, classData, 0, classData.length);
            } else {
                throw new ClassNotFoundException("Could not find " + name);
            }
        } catch (IOException e) {
            throw new ClassNotFoundException("Could not load " + name, e);
        }
    }

    private byte[] loadClassData(String className) throws IOException {
        String path = className.replace('.', '/') + ".class";
        try (JarFile jar = new JarFile(jarPath)) {
            ZipEntry entry = jar.getEntry(path);
            if (entry == null) {
                return null;
            }
            try (InputStream is = jar.getInputStream(entry)) {
                ByteArrayOutputStream byteStream = new ByteArrayOutputStream();
                int nextValue = is.read();
                while (-1 != nextValue) {
                    byteStream.write(nextValue);
                    nextValue = is.read();
                }
                return byteStream.toByteArray();
            }
        }
    }
}
