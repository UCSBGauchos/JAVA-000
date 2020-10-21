import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
public class ClassloaderTest extends ClassLoader {
    @Override
    protected Class<?> findClass(String name) {
        String classPath= this.getClass().getResource("/"+name+".xlass").getPath();
        File file =new File(classPath);
        int length = (int) file.length();
        byte[] bytes = new byte[length];
        try {
            new FileInputStream(file).read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (int i=0;i<bytes.length;i++){
            bytes[i]= (byte) (255-bytes[i]);
        }
        return defineClass(name,bytes,0,bytes.length);
    }
    public static void main(String[] args) throws Exception {
        ClassloaderTest classLoader = new ClassloaderTest();
        Class clazz = classLoader.findClass("Hello");
        Object obj = clazz.newInstance();
        Method method = clazz.getMethod("hello");
        method.invoke(obj);
    }
}