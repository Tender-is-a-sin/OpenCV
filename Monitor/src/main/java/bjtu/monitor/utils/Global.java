package bjtu.monitor.utils;

public class Global {

    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    public static final int right01 = 101; // 对应的管理员只能查用户信息
    public static final int right02 = 102; // 可以修改，查询
    public static final int right03 = 103; // 可以修改，查询，增加，删除用户
    public static final int right04 = 104; // root级用户，可以给别的管理员赋予权限
    public static final String SUCCES = "operation succeed";
    public static final String FAILED = "Operation failed";
}
