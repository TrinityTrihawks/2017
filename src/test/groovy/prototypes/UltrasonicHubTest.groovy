package prototypes

import spock.lang.Specification
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Unroll

import java.util.ArrayList
import java.util.Iterator

import org.junit.Assert
import com.xlson.groovycsv.CsvParser
import spock.lang.Shared

class UltrasonicHubTest extends Specification{
	ArrayList<SimpleRead> mockList
	SimpleRead mockSimpleRead1
	SimpleRead mockSimpleRead2
	UltrasonicHub mockHub
	
	@Shared
	Iterator testData
	
	def 'setup'(){
		mockHub = new UltrasonicHub()
		mockList = new ArrayList<>()
		
		mockSimpleRead1 = Mock()
		mockSimpleRead2 = Mock()
		mockHub = Mock()
		
		
		mockList.add(mockSimpleRead1)
		mockList.add(mockSimpleRead2)
		mockList.add(mockHub)
	}
	
	def 'setupSpec'() {
		// Sets up the data for testing
		CsvParser parser = new CsvParser() // We use the parser from groovycsv
		testData = parser.parse(
		new InputStreamReader(getClass().classLoader
			.getResourceAsStream("hubvals.csv")))
	}

	
	@Unroll("#angle should return #expectedAngle")
	def 'Returns proper angles'(){
		given:
			ReflectionTestUtils.setField(mockHub,"readerlist",mockList)
			
		when:
			double angle = mockHub.getCorrectionAngle()
			
		then:
			ArrayList portReadings = mockHub.getDistancefromallPorts()
			Assert.assertEquals(angle,expectedAngle.toDouble(),0.0001)
			
		where:
		[input1, input2, expectedAngle] << testData
	}
}
