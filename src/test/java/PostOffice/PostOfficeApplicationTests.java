package PostOffice;

import PostOffice.services.OfficeService;
import lombok.NoArgsConstructor;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
//@RunWith(SpringJUnit4ClassRunner.class)
@RunWith(SpringRunner.class)
@NoArgsConstructor
//@ContextConfiguration(classes = {AppConfig.class})
public class PostOfficeApplicationTests {

	@Autowired
	private OfficeService officeService;

	@Test
	public void helloWorldTesting() {
		Assertions.assertThat(officeService.findOffice(432010L).getAddress())
				.isEqualTo("ул. Врача Михайлова 64");

	}
}
