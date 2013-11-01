package vn.ptit.anhdinh.scr;

import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;

public class PreprocessorDataTest {

	@Test
	public void TestDeleteSpecialCharater() {
		String test = "Được \"lắm\" đó ><. Hehe @@. Android & ios";
		List<String> listTest = new LinkedList<String>();
		listTest.add(test);
		String expect = "Được lắm đó . Hehe . Android  ios";

		PreprocessorData preprocessorData = new PreprocessorData(listTest);
		preprocessorData.deleteSpecialCharacter();
		List<String> listActual = preprocessorData.getReviews();

		Assert.assertTrue(listActual.contains(expect));
	}

	@Test
	public void TestRemoveReviewsNotVietnamese() {
		String test1 = "Ứng dụng này chạy rất là tuyệt.";
		String test2 = "Ung dung nay chay rat la tuyet.";
		List<String> listTest = new LinkedList<String>();
		listTest.add(test1);
		listTest.add(test2);

		PreprocessorData preprocessorData = new PreprocessorData(listTest);
		preprocessorData.removeReviewsNotVietnamese();
		List<String> listActual = preprocessorData.getReviews();

		Assert.assertTrue(listActual.contains(test1));
		Assert.assertFalse(listActual.contains(test2));
	}
}
