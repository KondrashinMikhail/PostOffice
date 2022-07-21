package PostOffice.dto;

import PostOffice.entities.Mail;
import PostOffice.entities.Office;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Data
public class OfficeDto {
    private Long index;
    private String name;
    private String address;
    @ManyToMany
    private List<MailDto> mails = new ArrayList<>();

    public OfficeDto(Office office) {
        index = office.getIndex();
        name = office.getName();
        address = office.getAddress();
    }

    public void addMails(MailDto Mail) {
        mails.add(Mail);
    }

    public void deleteMails(MailDto Mail) {
        mails.remove(Mail);
    }
}
