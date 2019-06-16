package com.boot.kafka;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import sun.misc.Unsafe;

import java.lang.reflect.Field;

@RunWith(SpringRunner.class)
@SpringBootTest
public class KafkaApplicationTests {

	private static final int _1MB = 1024*1024;

	@Test
	public void contextLoads() {
	}


	@Test
	public void testString() {
		String str1 = new StringBuilder("¼ÆËã»ú").append("Èí¼þ").toString();
		System.out.println(str1.intern() == str1);

		String str2 = new StringBuilder("ja").append("va").toString();
		System.out.println(str2.intern() == str2);
	}


	public void testDirectMemoryOOM() throws IllegalAccessException {
		Field unsafeField = Unsafe.class.getDeclaredFields()[0];
		unsafeField.setAccessible(true);
		Unsafe unsafe = (Unsafe) unsafeField.get(null);
		while (true) {
			unsafe.allocateMemory(_1MB);
		}
	}
}
