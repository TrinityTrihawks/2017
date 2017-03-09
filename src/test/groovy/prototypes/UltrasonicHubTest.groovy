package prototypes

import spock.lang.Specification
import org.springframework.test.util.ReflectionTestUtils
import spock.lang.Unroll
import org.junit.Assert

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
	
	@Unroll("#angle should return #expectedAngle")
	def 'Returns proper angles'(){
		given:
			ReflectionTestUtils.setField(hub,"readerlist",mockList)
			
		when:
			double angle = hub.getCorrectionAngle()
			
		then:
			1* mockSimpleRead1.getDistance() >> dist1
			1* mockSimpleRead2.getDistance() >> dist2
			Assert.assertEquals(angle,expectedAngle.toDouble(),0)
			
		where:
		expectedAngle  | dist1  | dist2
		0              | 300    | 309
	}
}
