package PostOffice.Entities.Mail;

public class MailDto {
    private Long Id;
    private MailType Type;
    private MailStatus Status;
    private Long RecipientIndex;
    private String RecipientAddress;
    private String RecipientName;

    public MailDto() {
    }

    public MailDto(Mail mail) {
        Id = mail.getId();
        Type = mail.getType();
        Status = mail.getStatus();
        RecipientIndex = mail.getRecipientIndex();
        RecipientAddress = mail.getRecipientAddress();
        RecipientName = mail.getRecipientName();
    }

    public Long getId() {
        return Id;
    }
}