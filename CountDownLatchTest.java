package JUC;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch 类似计数器的功能，当值不为0时，wait()方法阻塞，调用一次countDown（）时，值会减一。当值为0时，阻塞结束
 * 
 * addShutdownHook，添加钩子，有信号量输入时（通过 kill -15或者在unix 系统中使用ctrl+c），调用钩子中的方法
 * 
 * @author bao
 *
 */
public class CountDownLatchTest {

	private final CountDownLatch keepAliveLatch = new CountDownLatch(1);
	//keepAliveThread线程挂起，保证程序一直运行，
	private  Thread keepAliveThread;

	public static void main(String[] args) throws InterruptedException {
		new CountDownLatchTest().test();
	}

	/** creates a new instance 
	 * @throws InterruptedException */
	public void test() throws InterruptedException {
		keepAliveThread = new Thread(new Runnable() {
			@Override
			public void run() {
				try {
					System.err.println(4);
					keepAliveLatch.await();
					System.err.println(1);
				} catch (InterruptedException e) {
					// bail out
				}
			}
		}, "elasticsearch[keepAlive/" + 1 + "]");
		keepAliveThread.setDaemon(false);
		// keep this thread alive (non daemon thread) until we shutdown
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.err.println(0);
				keepAliveLatch.countDown();
				System.err.println(2);
			}
		});
		System.err.println(3);
		keepAliveThread.start();
		Thread.sleep(10000);
		System.out.println("over");
		
	}
}