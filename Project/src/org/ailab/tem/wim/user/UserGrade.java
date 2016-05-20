package org.ailab.tem.wim.user;

/**
 * Created with IntelliJ IDEA.
 * User: Jazz
 * Date: 13-12-10
 * Time: 下午8:51
 * To change this template use File | Settings | File Templates.
 */
public enum UserGrade {
    UserGrade0(0, "草民"),
    UserGrade1(1, "捕快"),
    UserGrade2(2, "亭长"),
    UserGrade3(3, "县令"),
    UserGrade4(4, "巡抚"),
    UserGrade5(5, "总督"),
    UserGrade6(6, "封疆大臣"),
    UserGrade7(7, "军机大臣"),
    UserGrade8(8, "御前")
    ;

    protected int id;
    protected String name;

    UserGrade(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }


    public static UserGrade getByValue(int id) {
        for(UserGrade obj : UserGrade.values()) {
            if(obj.getId() == id) {
                return obj;
            }
        }

        return null;
    }
}
