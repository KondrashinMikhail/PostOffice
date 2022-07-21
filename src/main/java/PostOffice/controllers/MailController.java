package PostOffice.controllers;

import PostOffice.dto.HistoryDto;
import PostOffice.entities.Mail;
import PostOffice.dto.MailDto;
import PostOffice.enums.MailStatus;
import PostOffice.entities.Office;
import PostOffice.exceptions.MailAlreadyIssuedException;
import PostOffice.services.*;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/mail")
public class MailController {
    private final MailService mailService;
    private final HistoryService historyService;
    private final OfficeService officeService;

    public MailController(MailService mailService, HistoryService historyService, OfficeService officeService) {
        this.mailService = mailService;
        this.historyService = historyService;
        this.officeService = officeService;
    }

    @PatchMapping("/{id}/nextStatus")
    public void nextStatus(@PathVariable Long id) {
        final Mail currentMail = mailService.findMail(id);
        Office nextOffice = null;
        MailStatus newStatus = null;

        switch (currentMail.getStatus()) {
            case Accepted -> {
                newStatus = MailStatus.SendToWayPoint;
                officeService.removeMailFromOffice(officeService.findOffice(currentMail.getSourceIndex()).getIndex(), id);
            }
            case SendToWayPoint -> {
                newStatus = MailStatus.ArrivedToWayPoint;
                nextOffice = officeService.findNewOffice(currentMail);
                officeService.addMailToOffice(nextOffice.getIndex(), id);
            }
            case ArrivedToWayPoint -> {
                newStatus = MailStatus.SendToDestination;
                officeService.removeMailFromOffice(officeService.findCurrentOffice(currentMail).getIndex(), id);
            }
            case SendToDestination -> {
                newStatus = MailStatus.ArrivedAtDestination;
                nextOffice = officeService.findOffice(currentMail.getRecipientIndex());
                officeService.addMailToOffice(currentMail.getRecipientIndex(), id);
            }
            case ArrivedAtDestination -> {
                newStatus = MailStatus.Issued;
                officeService.removeMailFromOffice(officeService.findCurrentOffice(currentMail).getIndex(), id);
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
        mailService.addMail(mailType,
                recipientIndex,
                recipientAddress,
                recipientName,
                sourceIndex);
        historyService.addHistory(MailStatus.Accepted,
                new Date(),
                officeService.findOffice(sourceIndex),
                mailService.findAccepted(mailType, recipientIndex));
        officeService.addMailToOffice(sourceIndex, mailService.findAccepted(mailType, recipientIndex).getId());
        return new MailDto(mailService.findAccepted(mailType, recipientIndex));
    }

    @GetMapping("/{id}/status")
    public MailStatus getMail(@PathVariable Long id) {
        return mailService.findMail(id).getStatus();
    }

    @GetMapping("/{id}/history")
    public List<HistoryDto> getHistories(@PathVariable Long id) {
        return historyService.findHistoriesByMailId(id).stream().map(HistoryDto::new).toList();
    }

    @GetMapping("/all")
    public List<MailDto> getAllMails() {
        return mailService.findAllMails().stream().map(MailDto::new).toList();
    }
}
