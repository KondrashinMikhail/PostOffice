package PostOffice.Services;

import PostOffice.Entities.History.History;
import PostOffice.Entities.Mail.Mail;
import PostOffice.Entities.Mail.MailStatus;
import PostOffice.Entities.Office.Office;
import PostOffice.Repositories.HistoryRepository;
import PostOffice.Repositories.MailRepository;
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
        final List<History> histories = historyRepository.findAll();
        histories.removeIf(history -> !Objects.equals(history.getMail().getId(), id));
        return histories;
    }
}
