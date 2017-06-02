/** 
* Created on 02.06.2017 
* 
* © 2017 Daniel Thommes
*/
package com.propertycross.remoteui;

import java.util.concurrent.CountDownLatch;

import org.junit.Assert;
import org.junit.Test;

/**
 * 
 *
 * @author Daniel Thommes
 */
public class NestoriaApiTest {

	NestoriaApi api = new NestoriaApi();

	@Test
	public void test() throws InterruptedException {
		CountDownLatch latch = new CountDownLatch(1);
		api.searchProperties("aidlingen", 0, r -> {
			System.out.println(r);
			latch.countDown();
		}, e -> {
			Assert.fail(e.getMessage());
			latch.countDown();
		});
		latch.await();
	}

}
