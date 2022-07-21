package PostOffice.dto;

import PostOffice.entities.Mail;
import PostOffice.enums.MailStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class MailDto {
    private Long id;
    private String type;
    private MailStatus status;
    private Long recipientIndex;
    private String recipientAddress;
    private String recipientName;

    private Long sourceIndex;

    public MailDto(Mail mail) {
        id = mail.getId();
        type = mail.getType();
        status = mail.getStatus();
        recipientIndex = mail.getRecipientIndex();
        recipientAddress = mail.getRecipientAddress();
        recipientName = mail.getRecipientName();
        sourceIndex = mail.getSourceIndex();
    }
}