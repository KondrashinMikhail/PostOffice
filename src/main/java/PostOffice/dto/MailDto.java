package PostOffice.dto;

import PostOffice.entities.Mail;
import PostOffice.enums.MailStatus;
import PostOffice.enums.MailType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private Long id;
    private MailType type;
    private MailStatus status;
    private Long recipientIndex;
    private String recipientAddress;
    private String recipientName;

    public MailDto(Mail mail) {
        id = mail.getId();
        type = mail.getType();
        status = mail.getStatus();
        recipientIndex = mail.getRecipientIndex();
        recipientAddress = mail.getRecipientAddress();
        recipientName = mail.getRecipientName();
    }
}