package PostOffice.Controllers;

import PostOffice.Entities.History.History;
import PostOffice.Entities.History.HistoryDto;
import PostOffice.Entities.Mail.Mail;
import PostOffice.Entities.Mail.MailDto;
import PostOffice.Entities.Mail.MailStatus;
import PostOffice.Entities.Mail.MailType;
import PostOffice.Entities.Office.Office;
import PostOffice.Exceptions.MailAlreadyIssuedException;
import PostOffice.Services.*;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/mail")
public class MailController {
    private final MailService mailService;
    private final HistoryService historyService;
    private final OfficeService officeService;

    public MailController(MailService mailService, HistoryService historyService, OfficeService officeService) {
        this.mailService = mailService;
        this.historyService = historyService;
        this.officeService = officeService;
    }

    public Office findCurrentOffice(Mail mail) {
        List<Office> offices = officeService.findAllOffices();
        Office currentOffice = new Office();
        for (Office office : offices)
            if (office.getMails().contains(mail)) {
                currentOffice = office;
                break;
            }
        return currentOffice;
    }

    public Office findNewOffice(Mail mail) {
        List<Office> offices = officeService.findAllOffices();
        List<History> histories = historyService.findHistoriesByMailId(mail.getId());
        Office firstOffice = new Office();
        for(History history : histories)
            if (history.getStatus() == MailStatus.Accepted)
                firstOffice = history.getOffice();
        offices.remove(firstOffice);
        offices.remove(officeService.findOffice(mail.getRecipientIndex()));
        Long nextIndex = offices.get(new Random().nextInt(offices.size())).getIndex();
        return officeService.findOffice(nextIndex);
    }

    @PatchMapping ("/{id}/changeStatus")
    public void changeStatus(@PathVariable Long id) {
        final Mail currentMail = mailService.findMail(id);
        Office nextOffice = null;
        MailStatus newStatus = null;

        switch (currentMail.getStatus()) {
            case Accepted -> {
                newStatus = MailStatus.SendToWayPoint;
                officeService.removeMailFromOffice(findCurrentOffice(currentMail).getIndex(), id);
            }
            case SendToWayPoint -> {
                newStatus = MailStatus.ArrivedToWayPoint;
                nextOffice = findNewOffice(currentMail);
                officeService.addMailToOffice(nextOffice.getIndex(), id);
            }
            case ArrivedToWayPoint -> {
                newStatus = MailStatus.SendToDestination;
                officeService.removeMailFromOffice(findCurrentOffice(currentMail).getIndex(), id);
            }
            case SendToDestination -> {
                newStatus = MailStatus.ArrivedAtDestination;
                nextOffice = officeService.findOffice(currentMail.getRecipientIndex());
                officeService.addMailToOffice(currentMail.getRecipientIndex(), id);
            }
            case ArrivedAtDestination -> {
                newStatus = MailStatus.Issued;
                officeService.removeMailFromOffice(findCurrentOffice(currentMail).getIndex(), id);
            }
            case Issued -> throw new MailAlreadyIssuedException(id);
        }
        mailService.updateMail(id,
                currentMail.getType(),
                newStatus,
                currentMail.getRecipientIndex(),
                currentMail.getRecipientAddress(),
                currentMail.getRecipientName());
        historyService.addHistory(newStatus, new Date(), nextOffice, currentMail);
    }

    @PostMapping
    public MailDto createMail(@RequestParam("mailType") String mailType,
                              @RequestParam("recipientIndex") Long recipientIndex,
                              @RequestParam("recipientAddress") String recipientAddress,
                              @RequestParam("recipientName") String recipientName,
                              @RequestParam("sourceIndex") Long sourceIndex) {
        mailService.addMail(MailType.valueOf(mailType), recipientIndex, recipientAddress, recipientName);
        historyService.addHistory(MailStatus.Accepted,
                new Date(),
                officeService.findOffice(sourceIndex),
                mailService.find(MailType.valueOf(mailType), recipientIndex));
        officeService.addMailToOffice(sourceIndex, mailService.find(MailType.valueOf(mailType), recipientIndex).getId());
        return new MailDto(mailService.find(MailType.valueOf(mailType), recipientIndex));
    }

    @GetMapping("/{id}/status")
    public MailStatus getMail(@PathVariable Long id) {
        return mailService.findMail(id).getStatus();
    }

    @GetMapping("/{id}/history")
    public List<HistoryDto> getHistories(@PathVariable Long id) {
        return historyService.findHistoriesByMailId(id).stream().map(HistoryDto::new).toList();
    }
}
