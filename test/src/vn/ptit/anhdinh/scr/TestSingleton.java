package vn.ptit.anhdinh.scr;

public class TestSingleton {

	private static TestSingleton mSingleton = new TestSingleton();

	private TestSingleton() {
		System.out.println("Khởi tạo Singleton");
	}

	public static TestSingleton getInstance() {
		System.out.println("Get Instance");
		return mSingleton;
	}

	public static void main(String[] args) {
		TestSingleton.getInstance();
		TestSingleton.getInstance();
		TestSingleton.getInstance();
		TestSingleton.getInstance();
	}
}
