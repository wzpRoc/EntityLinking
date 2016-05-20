package wzp.test;

/**
 * Created by WZP on 2016/3/14.
 */
public class UserService {

    private String name = null;

    public UserService() {

        System.out.println("UserService Init...");
    }

    public void sayHello() {
        System.out.println("hello world");
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
