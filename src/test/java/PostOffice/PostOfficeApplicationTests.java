package PostOffice;

import PostOffice.entities.Office;
import PostOffice.services.OfficeService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class PostOfficeApplicationTests {

	@Autowired
	private OfficeService officeService;

	@Test
	void helloWorldTesting() {
		Assertions.assertThat(officeService.findOffice(432010L).getAddress())
				.isEqualTo("ул. Врача Михайлова 64");

	}
}
