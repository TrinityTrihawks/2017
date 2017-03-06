package prototypes


import java.awt.image.Kernel
import java.nio.charset.StandardCharsets

import com.xlson.groovycsv.CsvParser

import org.junit.Assert
import org.springframework.test.util.ReflectionTestUtils

import gnu.io.SerialPortEvent
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll

class SimpleReadSpockTest extends Specification {
	
	SimpleRead simpleRead
	SerialPortEvent mockEvent
	
	@Shared
	Iterator testData
	
	def 'setup'() {
		simpleRead = new SimpleRead()
		mockEvent = Mock()
	}
	
	def 'setupSpec'() {
		CsvParser parser = new CsvParser()
		testData = parser.parse(
		new InputStreamReader(getClass().classLoader
			.getResourceAsStream("readervals.csv")))
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
		Assert.assertEquals(castAsInt(output), distance);

		where:
		[input, output] << testData
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
