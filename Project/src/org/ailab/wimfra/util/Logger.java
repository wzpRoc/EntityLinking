package org.ailab.wimfra.util;

import org.apache.log4j.xml.DOMConfigurator;

import java.net.URL;

/**
 * User: Lu Tingming Time: 2009-12-24 Desc: 提供记录日志的方法
 */
public class Logger {
	/** log4j的logger */
	public org.apache.log4j.Logger mLogger = null;

	/** 是否已经初始化 */
	protected static boolean isInitialized = false;

	/** 配置文件 */
	private static String resource = "/log4j.xml";

	/**
	 * 得到logger
	 * 
	 * @param callerClass
	 *            调用该方法的类
	 * @return logger
	 */
	public static Logger getLogger(Class callerClass) {
		if (!isInitialized) {
			URL configFileResource = Logger.class.getClassLoader().getResource("log4j.xml");
			DOMConfigurator.configure(configFileResource);
			isInitialized = true;
		}
		org.apache.log4j.Logger logger = org.apache.log4j.Logger.getLogger(callerClass.getName());

		return new Logger(logger);
	}

	/**
	 * 设置状态为“未初始化”，可以使程序重新读取配置文件
	 */
	public static void setUninitialized() {
		Logger.isInitialized = false;
	}

	/**
	 * 构造方法
	 * 
	 * @param pLogger
	 *            log4j的logger
	 */
	public Logger(org.apache.log4j.Logger pLogger) {
		mLogger = pLogger;
	}

	/**
	 * 记录debug级别的日志
	 * 
	 * @param e
	 *            日志信息
	 */
	public void debug(Exception e) {
		StringBuffer sb = new StringBuffer(e.toString());
		StackTraceElement[] trace = e.getStackTrace();
		for (int i = 0; i < trace.length; i++)
			sb.append("\n\tat ").append(trace[i]);
		debug(sb);
	}

	/**
	 * 记录error级别的日志
	 * 
	 * @param e
	 *            日志信息
	 */
	public void error(Exception e) {
		StringBuffer sb = new StringBuffer(e.toString());
		StackTraceElement[] trace = e.getStackTrace();
		for (int i = 0; i < trace.length; i++)
			sb.append("\n\tat ").append(trace[i]);
		error(sb);
	}

	/**
	 * 记录error级别的日志
	 * 
	 * @param message
	 *            日志信息
	 */
	public void error(Object message) {
		log(org.apache.log4j.Level.ERROR, message);
	}

	/**
	 * 记录warn级别的日志
	 * 
	 * @param message
	 *            日志信息
	 */
	public void warn(Object message) {
		log(org.apache.log4j.Level.WARN, message);
	}

	/**
	 * 记录debug级别的日志
	 * 
	 * @param message
	 *            日志信息
	 */
	public void debug(Object message) {
		log(org.apache.log4j.Level.DEBUG, message);
	}

	/**
	 * 记录info级别的日志
	 * 
	 * @param message
	 */
	public void info(Object message) {
		log(org.apache.log4j.Level.INFO, message);
	}

	/**
	 * 按照指定的日志级别和信息记录日志
	 * 
	 * @param level
	 *            日志级别
	 * @param message
	 *            日志信息
	 */
	public void log(org.apache.log4j.Level level, Object message) {
		mLogger.log(level, message);
	}

}
