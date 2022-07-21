package PostOffice.entities;

import PostOffice.enums.MailStatus;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String type;
    private MailStatus status;
    private Long recipientIndex;
    private String recipientAddress;
    private String recipientName;
    private Long sourceIndex;

    public Mail(String type, MailStatus status, Long recipientIndex, String recipientAddress, String recipientName, Long sourceIndex) {
        this.type = type;
        this.status = status;
        this.recipientIndex = recipientIndex;
        this.recipientAddress = recipientAddress;
        this.recipientName = recipientName;
        this.sourceIndex = sourceIndex;
    }
}
