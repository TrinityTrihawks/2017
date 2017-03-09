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
		mockSimpleRead1 = Mock()
		mockSimpleRead2 = Mock()
		mockList = new ArrayList<>()
		mockList << mockSimpleRead1
		mockList << mockSimpleRead2
	}
	
	@Unroll("#angle should return #expectedAngle")
	def 'Returns proper angles'(){
		given:
			ReflectionTestUtils.setField(hub,"readerlist",mockList)
		when:
			double angle = hub.getCorrectionAngle()
		then:
			(1.._)* mockSimpleRead1.getDistance() >> dist1
			(1.._)* mockSimpleRead2.getDistance() >> dist2
			Assert.assertEquals(angle,expectedAngle.toDouble(),0)
		where:
		expectedAngle  | dist1  | dist2
		0      | 300    | 309
	}
}
