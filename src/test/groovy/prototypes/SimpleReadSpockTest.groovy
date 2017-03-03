package prototypes


import java.nio.charset.StandardCharsets

import org.junit.Assert
import org.springframework.test.util.ReflectionTestUtils

import gnu.io.SerialPortEvent
import spock.lang.Specification
import spock.lang.Unroll

class SimpleReadSpockTest extends Specification {

	SimpleRead simpleRead
	SerialPortEvent mockEvent
	
	def 'setup'() {
		simpleRead = new SimpleRead()
		mockEvent = Mock()
	}

	@Unroll("#input should return #output")
	def "input returns output"() {
		given:
		InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
		ReflectionTestUtils.setField(simpleRead, "inputStream", inputStream)
		
		when:
		simpleRead.serialEvent(mockEvent);
		
		then:
		1* mockEvent.getEventType() >> SerialPortEvent.DATA_AVAILABLE
		int distance = simpleRead.getDistance();
		Assert.assertEquals(output, distance);

		where:
		input     | output
		'\rR1424' | 1424
		'R1424\r' | 1424
		'R0023\r' | 23
	}
	
	def "no data available does not modify default distance"() {
		given:
		InputStream inputStream = new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8))
		ReflectionTestUtils.setField(simpleRead, "inputStream", inputStream)
		
		when:
		simpleRead.serialEvent(mockEvent);
		
		then:
		1* mockEvent.getEventType() >> event
		int distance = simpleRead.getDistance();
		Assert.assertEquals(output, distance);

		where:
		input  | output  | event
		'\rR1424'| 0 | SerialPortEvent.OUTPUT_BUFFER_EMPTY
		'R1424\r'| 0 | SerialPortEvent.DSR
		'R0023\r'| 0 | SerialPortEvent.PE
	}
}
