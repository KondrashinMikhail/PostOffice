package PostOffice.mvcs;

import PostOffice.dto.HistoryDto;
import PostOffice.dto.MailDto;
import PostOffice.entities.History;
import PostOffice.entities.Mail;
import PostOffice.entities.Office;
import PostOffice.enums.MailStatus;
import PostOffice.exceptions.MailAlreadyIssuedException;
import PostOffice.services.HistoryService;
import PostOffice.services.MailService;
import PostOffice.services.OfficeService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/mail")
public class MailMvcController {
    private final MailService mailService;
    private final OfficeService officeService;
    private final HistoryService historyService;
    private final List<Long> officeIndexes;

    public MailMvcController(MailService mailService, OfficeService officeService, HistoryService historyService) {
        this.mailService = mailService;
        this.officeService = officeService;
        this.historyService = historyService;
        //this.types = List.of(MailType.Package.toString(), MailType.Card.toString(), MailType.Letter.toString(), MailType.ParcelPost.toString());
        this.officeIndexes = officeService.findAllOffices().stream().map(Office::getIndex).collect(Collectors.toList());
    }

    @GetMapping
    public String getMails(Model model) {
        model.addAttribute("mails", mailService.findAllMails().stream().map(MailDto::new).toList());
        return "mail";
    }

    @GetMapping("/create")
    public String createMail(Model model) {
        model.addAttribute("mailDto", new MailDto());
        return "mail-create";
    }

    @GetMapping("/{id}/nextStatus")
    public String nextStatus(@PathVariable Long id) {
        final Mail currentMail = mailService.findMail(id);
        Office nextOffice = null;
        MailStatus newStatus = null;

        switch (currentMail.getStatus()) {
            case Accepted -> {
                newStatus = MailStatus.SendToWayPoint;
                officeService.removeMailFromOffice(officeService.findCurrentOffice(currentMail).getIndex(), id);
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
        return "redirect:/mail";
    }

    @PostMapping("/creating")
    public String saveMail(@ModelAttribute @Valid MailDto mailDto,
                           BindingResult bindingResult,
                           Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
        }
        mailService.addMail(mailDto.getType(),
                mailDto.getRecipientIndex(),
                mailDto.getRecipientAddress(),
                mailDto.getRecipientName(),
                mailDto.getSourceIndex());
        historyService.addHistory(MailStatus.Accepted,
                new Date(),
                officeService.findOffice(mailDto.getSourceIndex()),
                mailService.findAccepted(mailDto.getType(),
                        mailDto.getRecipientIndex()));
        officeService.addMailToOffice(mailDto.getSourceIndex(), mailService.findAccepted(mailDto.getType(), mailDto.getRecipientIndex()).getId());
        return "redirect:/mail";
    }

    @GetMapping("/{id}/history")
    public String getHistories(@PathVariable Long id, Model model) {
        model.addAttribute("histories", historyService.findHistoriesByMailId(id).stream().map(HistoryDto::new).toList());
        return "mail-history";
    }
}
