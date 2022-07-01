package PostOffice.Controllers;

import PostOffice.Entities.Mail.MailDto;
import PostOffice.Services.OfficeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping ("/office")
public class OfficeController {
    private final OfficeService officeService;

    public OfficeController(OfficeService officeService) {
        this.officeService = officeService;
    }

    @PostMapping
    public void createOffice(@RequestParam("index") Long index,
                           @RequestParam("name") String name,
                           @RequestParam("address") String address) {
        officeService.addOffice(index, name, address);
    }

    @GetMapping("/{index}/mails")
    public List<MailDto> getMails(@PathVariable Long index){
        return officeService.findAllMails(index).stream().map(MailDto::new).toList();
    }



}
