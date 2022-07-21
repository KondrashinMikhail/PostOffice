package PostOffice.mvcs;

import PostOffice.dto.MailDto;
import PostOffice.dto.OfficeDto;
import PostOffice.services.OfficeService;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/office")
public class OfficeMvcController {
    private final OfficeService service;

    public OfficeMvcController(OfficeService service) {
        this.service = service;
    }

    @GetMapping
    public String getOffices(Model model) {
        model.addAttribute("offices", service.findAllOffices().stream().map(OfficeDto::new).toList());
        return "office";
    }

    @GetMapping(value = {"/edit", "/edit/{index}"})
    public String editOffice(@PathVariable(required = false) Long index, Model model) {
        if (index == null || index <= 0)
            model.addAttribute("officeDto", new OfficeDto());
        else {
            model.addAttribute("officeIndex", index);
            model.addAttribute("officeDto", new OfficeDto(service.findOffice(index)));
        }
        return "office-edit";
    }

    @PostMapping(value = {"", "/{index}"})
    public String saveOffice(@PathVariable(required = false) Long index,
                             @ModelAttribute @Valid OfficeDto officeDto,
                             BindingResult bindingResult,
                             Model model) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("errors", bindingResult.getAllErrors());
            return "office-edit";
        }
        if (index == null || index <= 0)
            service.addOffice(officeDto.getIndex(), officeDto.getName(), officeDto.getAddress());
        else
            service.updateOffice(index, officeDto.getName(), officeDto.getAddress());
        return "redirect:/office";
    }

    @PostMapping("/delete/{index}")
    public String deleteOffice(@PathVariable Long index) {
        service.deleteOffice(index);
        return "redirect:/office";
    }

    @GetMapping("/{index}/mails")
    public String getMails(@PathVariable Long index, Model model) {
        model.addAttribute("mails", service.findAllMails(index).stream().map(MailDto::new).toList());
        return "office-mails";
    }
}
