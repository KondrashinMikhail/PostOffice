package PostOffice.Repositories;

import PostOffice.Entities.Office.Office;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfficeRepository extends JpaRepository<Office, Long> {
}
