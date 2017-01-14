package de.benboecker.kochbuch;

import android.support.test.runner.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {

	@Test
	public void addition() {
		int result = 2 + 2;
		int expected = 4;
		assertEquals(result, expected);
	}
	@Test
	public void additionAssert() {
		int result = 2 + 2;
		int expected = 128;
		assert result == expected;
		assert result != expected;
	}
}
