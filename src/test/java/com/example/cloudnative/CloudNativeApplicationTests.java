package com.example.cloudnative;

import com.example.cloudnative.Controller.DemoController;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class CloudNativeApplicationTests {

	private DemoController demoController = new DemoController();

	private static final String EXPECTED_TEXT = "{\"name\":\"云原生斗地主\",\"number\":\"nju17\"}";
	@BeforeEach
	void initAll() {
		demoController = new DemoController();
	}
	@Test
	void testGetText() {
		String result = demoController.getText();
		assert(result.equals(EXPECTED_TEXT));
	}


	@Test
	void test429(){
		try {
			for(int i = 0; i < 100; i++) {
				Thread.sleep(5);
				demoController.getText();
			}
			assert false;
		} catch (Exception e) {
			assert(e.getMessage().equals("429 TOO_MANY_REQUESTS"));
		}
	}

	@Test
	void test429Two(){
		try {
			for(int i = 0; i < 100; i++) {
				demoController.getText();
			}
			assert false;
		} catch (Exception e) {
			assert(e.getMessage().equals("429 TOO_MANY_REQUESTS"));
		}
	}

	@Test
	void testEdge(){
		try {
			for(int i = 0; i < 100; i++) {
				Thread.sleep(11);
				demoController.getText();
			}
			assert true;
		} catch (Exception e) {
			if(e.getMessage().equals("429 TOO_MANY_REQUESTS")){
				assert false;
			};
			assert true;
		}
	}

}
