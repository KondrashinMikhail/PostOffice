package PostOffice.Repositories;

import PostOffice.Entities.History.History;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HistoryRepository extends JpaRepository<History, Long> {
}
