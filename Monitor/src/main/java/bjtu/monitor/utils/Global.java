package bjtu.monitor.utils;

public class Global {

    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public static final int right = 100;   // 普通用户，只能修改自己，查询所有用户
    public static final int right01 = 101; // 对应的管理员只能查询，修改别的用户信息
    public static final int right02 = 102; // 可以修改，查询，增加，删除用户
    public static final int right03 = 103; // root级用户，可以给别的管理员赋予权限
    public static final String SUCCES = "Operation succeed";
    public static final String FAILED = "Operation failed";
    public static final int REGISTER = 1; //注册
    public static final int FIND = 0; //找回

}
