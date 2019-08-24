package JUC;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
/**
 * CyclicBarrier作用，拦截一定的线程数量，保证这批数据同时完成，然后在执行下一步，如果拦截所有的，初始化时，指定的值一定要等于拦截线程总数
 * await() 阻塞等待一定数量的线程执行完毕
 * 如果设置的数值比总线程数量大，则会一直阻塞
 * @author bao
 *
 */
public class CyclicBarrierTest {

	public static void main(String[] args) {
		int[] count = new int[]{0};
		int thread_size = 4;
		CyclicBarrier barrier = new CyclicBarrier(thread_size);
		for (int i = 0; i < thread_size; i++)
			new Thread() {
				@Override
				public void run() {
					System.out.println("线程" + Thread.currentThread().getName() + "正在写入数据...");
					try {
						count[0]++;
						Thread.sleep(5000+count[0]*100);
						System.out.println("线程" + Thread.currentThread().getName() + "写入数据完毕，等待其他线程写入完毕");
						barrier.await();
					} catch (InterruptedException e) {
						e.printStackTrace();
					} catch (BrokenBarrierException e) {
						e.printStackTrace();
					}
					System.err.println("。。。。线程" + Thread.currentThread().getName() +"线程写入完毕，开始处理其他任务...");
				}
			
		}.start();
		System.out.println("结束。。。。。。。。。");
	}
}
