package PostOffice.entities;

import PostOffice.enums.MailStatus;
import PostOffice.enums.MailType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Mail {
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id;
    private MailType type;
    private MailStatus status;
    private Long recipientIndex;
    private String recipientAddress;
    private String recipientName;

    public Mail(MailType type, MailStatus status, Long recipientIndex, String recipientAddress, String recipientName) {
        this.type = type;
        this.status = status;
        this.recipientIndex = recipientIndex;
        this.recipientAddress = recipientAddress;
        this.recipientName = recipientName;
    }
}
