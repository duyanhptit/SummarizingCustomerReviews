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
		List<String> listActual = preprocessorData.getComments();

		Assert.assertTrue(listActual.contains(expect));
	}
}
