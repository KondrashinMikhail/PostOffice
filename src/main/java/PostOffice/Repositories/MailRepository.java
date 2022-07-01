package PostOffice.Repositories;

import PostOffice.Entities.Mail.Mail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MailRepository extends JpaRepository<Mail, Long> {
}
