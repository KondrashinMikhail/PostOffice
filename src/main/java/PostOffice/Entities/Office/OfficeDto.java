package PostOffice.Entities.Office;

import PostOffice.Entities.Mail.Mail;
import PostOffice.Entities.Mail.MailDto;

import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

public class OfficeDto {
    private Long Index;
    private String Name;
    private String Address;
    @ManyToMany
    private List<MailDto> Mails = new ArrayList<>();

    public OfficeDto() {
    }

    public OfficeDto(Office office) {
        Index = office.getIndex();
        Name = office.getName();
        Address = office.getAddress();
    }

    public Long getIndex() {
        return Index;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public List<MailDto> getMails() {
        return Mails;
    }

    public void setIndex(Long index) {
        Index = index;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void addMails(MailDto Mail) {
        Mails.add(Mail);
    }

    public void deleteMails(Mail Mail) {
        Mails.remove(Mail);
    }
}
