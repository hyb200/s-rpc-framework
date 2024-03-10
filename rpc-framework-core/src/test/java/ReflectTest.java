import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class ReflectTest {
    public static void main(String[] args) throws Exception{
        Class<?> clazz = Class.forName("Test");
        Test obj = (Test) clazz.newInstance();
        Test obj2 = (Test) clazz.newInstance();

        Method[] declaredMethods = clazz.getDeclaredMethods();
        for (Method method : declaredMethods) {
            System.out.println(method.getName());
        }

        Method publicMethod = clazz.getDeclaredMethod("publicMethod");
        publicMethod.invoke(obj);

        Field value = clazz.getDeclaredField("value");
        value.setAccessible(true);
        value.set(obj2, "abaaba");

        Method privateMethod = clazz.getDeclaredMethod("privateMethod");
        privateMethod.setAccessible(true);
        privateMethod.invoke(obj);
        privateMethod.invoke(obj2);
    }
}


class Test {

    private String value;

    public Test() {
        this.value = "ikun";
    }

    public void publicMethod() {
        System.out.println("Public Method: " + value);
    }

    private void privateMethod() {
        System.out.println("Private Method: " + value);
    }
}