package org.ailab.nlp.parsing.chinese;

import gate.Factory;
import gate.FeatureMap;
import gate.Gate;
import gate.ProcessingResource;
import gate.creole.SerialAnalyserController;
import gate.util.GateException;

import java.io.File;
import java.net.MalformedURLException;

/**
 * User: Lu Tingming
 * Date: 2011-8-26
 * Time: 16:45:23
 * Desc:
 */
public class GateUtilForChinese {
	// GATE文件目录和插件目录
	public static final File gateHome = new File("D:\\GATE");
	public static File pluginsHome;

	// 所用到的基本资源
	public static final String prName_chineseParser = "org.ailab.irica.parsing.chinese.ChineseParserForGATE";

	/**
	 * GATE初始化函数
	 * @throws gate.util.GateException
	 */
	public static void init() throws GateException {
	    System.out.println("GateUtil.init()");
	    pluginsHome = new File(gateHome, "plugins");
	    System.setProperty("gate.home", gateHome.getAbsolutePath());
	    Gate.init();
	}

	/**
	 * 注册插件目录
	 * @throws GateException
	 */
	public static void registerPlugins() throws GateException {
		try {
			Gate.getCreoleRegister().registerDirectories(new File(pluginsHome, "ANNIE").toURL());
			Gate.getCreoleRegister().registerDirectories(new File(pluginsHome, "IRICA").toURL());
		} catch (MalformedURLException e) {
			//e.printStackTrace();
			System.err.println("Can not find the Gate plugins home!");
		}
	}

	/**
	 * 工厂方法获取一个新的annieController
	 * 得到的controller已经被添加了一些基本的处理资源
	 * @return
	 */
	public static SerialAnalyserController newController(){
		SerialAnalyserController annieController = null;
		try {
			// 如果没有初始化，则进行初始化
			if(!Gate.isInitialised()){
				init();
				registerPlugins();
			}

			// 构造新的控制器
			annieController = (SerialAnalyserController) Factory.createResource(
	                "gate.creole.SerialAnalyserController",
                    Factory.newFeatureMap(),
	                Factory.newFeatureMap(),
                    "test_" + Gate.genSym()
	        );

			// 在控制器中添加基本的处理资源 ProcessResouce
            FeatureMap params = Factory.newFeatureMap(); // use default parameters
            ProcessingResource pr = (ProcessingResource)Factory.createResource(prName_chineseParser, params);
            pr.setParameterValue("homeOfIctclas", "D:\\ICTCLAS\\ICTCLAS50_Windows_32_JNI\\API");
            // add the PR to the pipeline controller
            annieController.add(pr);
		} catch (GateException e) {
			//e.printStackTrace();
			System.err.println("Failed to generate annie controller!");
		}

		return annieController;
	}
}
