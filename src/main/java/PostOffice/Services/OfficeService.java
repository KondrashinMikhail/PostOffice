package PostOffice.Services;

import PostOffice.Entities.Mail.Mail;
import PostOffice.Entities.Office.Office;
import PostOffice.Exceptions.MailNotFoundException;
import PostOffice.Exceptions.OfficeNotFoundException;
import PostOffice.Repositories.MailRepository;
import PostOffice.Repositories.OfficeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class OfficeService {
    private final OfficeRepository officeRepository;
    private final MailRepository mailRepository;

    public OfficeService(OfficeRepository officeRepository, MailRepository mailRepository) {
        this.officeRepository = officeRepository;
        this.mailRepository = mailRepository;
    }

    @Transactional
    public Office addOffice(Long Index, String Name, String Address) {
        final Office office = new Office(Index, Name, Address);
        return officeRepository.save(office);
    }

    @Transactional(readOnly = true)
    public Office findOffice(Long index) {
        final Optional<Office> office = officeRepository.findById(index);
        return office.orElseThrow(() -> new OfficeNotFoundException(index));
    }

    @Transactional(readOnly = true)
    public List<Office> findAllOffices() {
        return officeRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Mail> findAllMails(Long index) {
        final Optional<Office> office = officeRepository.findById(index);
        return office.orElseThrow(() -> new OfficeNotFoundException(index)).getMails();
    }

    @Transactional
    public Office updateOffice(Long index, String name, String address) {
        final Office currentOffice = findOffice(index);
        currentOffice.setName(name);
        currentOffice.setAddress(address);
        return officeRepository.save(currentOffice);
    }

    @Transactional
    public Office deleteOffice(Long id) {
        final Office currentOffice = findOffice(id);
        officeRepository.delete(currentOffice);
        return currentOffice;
    }

    @Transactional
    public void deleteAllOffices() {
        officeRepository.deleteAll();
    }

    @Transactional
    public Office addMailToOffice(Long index, Long id) {
        final Optional<Office> office = officeRepository.findById(index);
        final Optional<Mail> mail = mailRepository.findById(id);
        office.orElseThrow(() -> new OfficeNotFoundException(index))
                .addMail(mail.orElseThrow(() -> new MailNotFoundException(id)));
        return officeRepository.save( office.orElseThrow(() ->
                new OfficeNotFoundException(index)));
    }

    @Transactional
    public Office removeMailFromOffice(Long index, Long id){
        final Optional<Office> office = officeRepository.findById(index);
        final Optional<Mail> mail = mailRepository.findById(id);
        office.orElseThrow(() -> new OfficeNotFoundException(index))
                .deleteMail(mail.orElseThrow(() -> new MailNotFoundException(id)));
        return officeRepository.save( office.orElseThrow(() ->
                new OfficeNotFoundException(index)));
    }
}
