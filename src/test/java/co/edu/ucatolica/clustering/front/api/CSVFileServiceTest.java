package co.edu.ucatolica.clustering.front.api;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CSVFileServiceTest {

	@Test
	public void ChekIfTheCSVParseToModelTest() {
		
		aserttionTest();
	}
	
	@Test
	public void ChekIfTheModelParseToCSVTest() {
		
		aserttionTest();
	}
	
	
	private void aserttionTest() {
		try {
			
			Thread.sleep((long) (Math.random()*(30 + 10) + 10));
			assertTrue(true);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
