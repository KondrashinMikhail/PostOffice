package PostOffice.services;

import PostOffice.entities.History;
import PostOffice.entities.Mail;
import PostOffice.enums.MailStatus;
import PostOffice.entities.Office;
import PostOffice.repositories.HistoryRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Service
public class HistoryService {
    private final HistoryRepository historyRepository;

    public HistoryService(HistoryRepository historyRepository) {
        this.historyRepository = historyRepository;
    }

    @Transactional
    public History addHistory(MailStatus status, Date date, Office office, Mail mail) {
        final History history = new History(status, date, office, mail);
        return historyRepository.save(history);
    }

    @Transactional
    public List<History> findHistoriesByMailId(Long id) {
        return historyRepository.findAllByMail_id(id);
    }
}
