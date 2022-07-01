package PostOffice.Services;

import PostOffice.Entities.Mail.Mail;
import PostOffice.Entities.Mail.MailStatus;
import PostOffice.Entities.Mail.MailType;
import PostOffice.Exceptions.MailNotFoundException;
import PostOffice.Repositories.MailRepository;
import org.springframework.stereotype.Service;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class MailService {
    private final MailRepository mailRepository;

    public MailService(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    @Transactional
    public Mail addMail(MailType type, Long recipientIndex, String recipientAddress, String recipientName) {
        final Mail mail = new Mail(type, MailStatus.Accepted, recipientIndex, recipientAddress, recipientName);
        return mailRepository.save(mail);
    }

    @Transactional(readOnly = true)
    public Mail findMail(Long id) {
        final Optional<Mail> mail = mailRepository.findById(id);
        return mail.orElseThrow(() -> new MailNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Mail> findAllMails() {
        return mailRepository.findAll();
    }

    @Transactional
    public Mail updateMail(Long id, MailType type, MailStatus status, Long recipientIndex, String recipientAddress, String recipientName) {
        final Mail currentMail = findMail(id);
        currentMail.setType(type);
        currentMail.setStatus(status);
        currentMail.setRecipientIndex(recipientIndex);
        currentMail.setRecipientAddress(recipientAddress);
        currentMail.setRecipientName(recipientName);
        return mailRepository.save(currentMail);
    }

    @Transactional
    public Mail find(MailType type, Long recipientIndex) {
        final List<Mail> mails = findAllMails();
        Mail mail = new Mail();
        for (Mail value : mails) {
            if (value.getStatus() == MailStatus.Accepted &&
                    value.getType() == type &&
                    value.getRecipientIndex() == recipientIndex) {
                mail = value;
                break;
            }
        }
        return mail;
    }

    /*@Transactional
    public Mail changeStatus(Long id, MailStatus status, Office office) {
        final Mail currentMail = findMail(id);
        currentMail.setStatus(status);
        historyService.addHistory(status, new Date(), office, currentMail);
        return mailRepository.save(currentMail);
    }*/

    @Transactional
    public Mail deleteMail(Long id) {
        final Mail currentMail = findMail(id);
        mailRepository.delete(currentMail);
        return currentMail;
    }

    @Transactional
    public void deleteAllMails() {
        mailRepository.deleteAll();
    }
}
