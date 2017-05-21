package org.usfirst.frc.team4215.robot.ultrasonic;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

import gnu.io.SerialPortEvent;
import org.usfirst.frc.team4215.robot.ultrasonic.SimpleRead;

public class SimpleReadTest {

	SimpleRead simpleRead;
	SerialPortEvent mockEvent;
	
	@Before
	public void init() {
		simpleRead = new SimpleRead();
		mockEvent = mock(SerialPortEvent.class);
	}
	@Test
	public void testReadEventWithCarraigeReturnAtEnd() {
		String exampleString = "R1424\r";
		InputStream inputStream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));
		ReflectionTestUtils.setField(simpleRead, "inputStream", inputStream);

		//interaction
		when(mockEvent.getEventType()).thenReturn(SerialPortEvent.DATA_AVAILABLE);
		
		//execution
		simpleRead.serialEvent(mockEvent);
		
		//verification
		int distance = simpleRead.getDistance();
		Assert.assertEquals(1424, distance);
	}
	
	@Test
	public void testReadEventWithCarraigeReturnABeginning() {
		//setup data
		String exampleString = "\rR1424";
		InputStream inputStream = new ByteArrayInputStream(exampleString.getBytes(StandardCharsets.UTF_8));
		ReflectionTestUtils.setField(simpleRead, "inputStream", inputStream);

		//interaction
		when(mockEvent.getEventType()).thenReturn(SerialPortEvent.DATA_AVAILABLE);
		
		//execution
		simpleRead.serialEvent(mockEvent);
		
		//verification
		int distance = simpleRead.getDistance();
		Assert.assertEquals(1424, distance);
	}
	
}
