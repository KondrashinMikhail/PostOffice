package PostOffice.services;

import PostOffice.entities.Mail;
import PostOffice.entities.Office;
import PostOffice.exceptions.MailNotFoundException;
import PostOffice.exceptions.OfficeNotFoundException;
import PostOffice.repositories.MailRepository;
import PostOffice.repositories.OfficeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Random;

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
        return officeRepository.save(office.orElseThrow(() ->
                new OfficeNotFoundException(index)));
    }

    @Transactional
    public Office removeMailFromOffice(Long index, Long id) {
        final Optional<Office> office = officeRepository.findById(index);
        final Optional<Mail> mail = mailRepository.findById(id);
        office.orElseThrow(() -> new OfficeNotFoundException(index))
                .deleteMail(mail.orElseThrow(() -> new MailNotFoundException(id)));
        return officeRepository.save(office.orElseThrow(() ->
                new OfficeNotFoundException(index)));
    }

    public Office findCurrentOffice(Mail mail) {
        List<Office> offices = findAllOffices();
        Office currentOffice = new Office();
        for (Office office : offices)
            if (office.getMails().contains(mail)) {
                currentOffice = office;
                break;
            }
        return currentOffice;
    }

    public Office findNewOffice(Mail mail) {
        List<Office> offices = findAllOffices();
        offices.remove(findOffice(mail.getSourceIndex()));
        offices.remove(findOffice(mail.getRecipientIndex()));
        Long nextIndex = offices.get(new Random().nextInt(offices.size())).getIndex();
        return findOffice(nextIndex);
    }
}
