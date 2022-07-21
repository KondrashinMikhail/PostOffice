package PostOffice.entities;

import PostOffice.enums.MailStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;


@Getter
@Setter
@NoArgsConstructor
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private MailStatus status;
    private Date date;
    @ManyToOne
    private Office office;
    @ManyToOne
    private Mail mail;

    public History(MailStatus status, Date date, Office office, Mail mail) {
        this.status = status;
        this.date = date;
        this.office = office;
        this.mail = mail;
    }

    public Office getOffice() {
        return office;
    }

    public Mail getMail() {
        return mail;
    }

    public String getIndex() {
        if (office != null)
            return office.getIndex().toString();
        else
            return "-";
    }
}
