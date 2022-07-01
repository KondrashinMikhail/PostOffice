package PostOffice.Entities.Mail;

import javax.persistence.*;

@Entity
public class Mail {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long Id;
    private MailType Type;
    private MailStatus Status;
    private Long RecipientIndex;
    private String RecipientAddress;
    private String RecipientName;

    public Mail() {
    }
    public Mail(MailType type, MailStatus status, Long recipientIndex, String recipientAddress, String recipientName) {
        Type = type;
        Status = status;
        RecipientIndex = recipientIndex;
        RecipientAddress = recipientAddress;
        RecipientName = recipientName;
    }

    public Long getId() {
        return Id;
    }

    public MailType getType() {
        return Type;
    }

    public MailStatus getStatus() {
        return Status;
    }

    public Long getRecipientIndex() {
        return RecipientIndex;
    }

    public String getRecipientAddress() {
        return RecipientAddress;
    }

    public String getRecipientName() {
        return RecipientName;
    }

    public void setType(MailType type) {
        Type = type;
    }

    public void setStatus(MailStatus status) {
        Status = status;
    }
    public void setRecipientIndex(Long recipientIndex) {
        RecipientIndex = recipientIndex;
    }

    public void setRecipientAddress(String recipientAddress) {
        RecipientAddress = recipientAddress;
    }

    public void setRecipientName(String recipientName) {
        RecipientName = recipientName;
    }
}
