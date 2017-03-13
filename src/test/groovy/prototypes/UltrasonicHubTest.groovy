package prototypes

import spock.lang.Specification
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Unroll
import org.junit.Assert
import com.xlson.groovycsv.CsvParser

class UltrasonicHubTest extends Specification{
	ArrayList<SimpleRead> mockList
	SimpleRead mockSimpleRead1
	SimpleRead mockSimpleRead2
	UltrasonicHub hub
	
	def 'setup'(){
		hub = new UltrasonicHub()
		mockList = new ArrayList<>()
		
		mockSimpleRead1 = Mock()
		mockSimpleRead2 = Mock()
		
		
		mockList.add(mockSimpleRead1)
		mockList.add(mockSimpleRead2)
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
			ReflectionTestUtils.setField(hub,"readerlist",mockList)
			
		when:
			double angle = hub.getCorrectionAngle()
			
		then:
			1* mockSimpleRead1.getDistance() >> dist1
			1* mockSimpleRead2.getDistance() >> dist2
			Assert.assertEquals(angle,expectedAngle.toDouble(),0.0001)
			
		where:
		[input1, input2, output] << testData
	}
}
