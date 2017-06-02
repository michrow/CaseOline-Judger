package caseonline.judger.engine.application;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 评判引擎加载器
 * 
 * @author 王肖辉 2017年3月19日
 */
public class ApplicationBootstrap {

	/**
	 * 主程序入口
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		LOGGER.info("Starting Testcase Online Judger ...");
		ApplicationBootstrap app = new ApplicationBootstrap();
		app.getApplicationContext();
		app.setHeartBeat();
		app.setShutdownHook();
		LOGGER.info("Testcase Online Judge Judger started...");
	}

	/**
	 * 加载应用程序配置文件
	 */
	private void getApplicationContext() {
		applicationContext = new ClassPathXmlApplicationContext("application-context.xml");
	}

	/**
	 * 引擎心跳
	 */
	private void setHeartBeat() {
		final int DELAY = 10;
		final int PERIOD = 20;
		ApplicationHeartbeat heartbeat = applicationContext.getBean(ApplicationHeartbeat.class);
		ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
		scheduledExecutorService.scheduleAtFixedRate(heartbeat, DELAY, PERIOD, TimeUnit.SECONDS);
	}

	/**
	 * 停止前的准备工作
	 */
	private void setShutdownHook() {
		final Thread mainThread = Thread.currentThread();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			public void run() {
				try {
					LOGGER.info("Testcase Online Judger is shutting down...");
					mainThread.join();
				} catch (InterruptedException e) {
					LOGGER.catching(e);
				}
			}
		});
	}

	private ApplicationContext applicationContext;
	private static final Logger LOGGER = LogManager.getLogger(ApplicationBootstrap.class);
}
