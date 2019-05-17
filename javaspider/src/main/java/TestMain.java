import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Exchanger;

/**
 * @ author ezra
 * @ date 2019/5/8 14:19
 */
public class TestMain {
	public static void main(String[] args) {
		final Exchanger<List<Integer>> exchanger = new Exchanger<>();
		new Thread() {
			@Override
			public void run() {
				List<Integer> l = new ArrayList<>(2);
				l.add(1);
				l.add(2);
				try {
					l = exchanger.exchange(l);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Thread1" + l.toString());
			}
		}.start();
		new Thread() {
			@Override
			public void run() {
				List<Integer> l = new ArrayList<>(2);
				l.add(4);
				l.add(5);
				try {
					l = exchanger.exchange(l);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("Thread2" + l.toString());
			}
		}.start();

	}
}
