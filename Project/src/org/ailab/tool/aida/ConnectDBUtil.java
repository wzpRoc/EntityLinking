package org.ailab.tool.aida;

public class ConnectDBUtil {
	
	// 连接的URL、用户名和密码，建立数据库连接
	public static String dbUrl = "jdbc:mysql://localhost:3306/wikiif?useUnicode=true&characterEncoding=UTF-8";   
	public static String dbUser = "root";   
	public static String dbPwd = "root";

	public static void RegisterDriver()
	{
		// 1.注册驱动程序类
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
