package PostOffice.Entities.History;

import PostOffice.Entities.Mail.Mail;
import PostOffice.Entities.Mail.MailStatus;
import PostOffice.Entities.Office.Office;

import javax.persistence.*;
import java.util.Date;

@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long Id;
    private MailStatus Status;
    private Date Date;
    @ManyToOne
    private Office Office;
    @ManyToOne
    private Mail Mail;

    public History() {
    }

    public History(MailStatus status, Date date, Office office, Mail mail) {
        Status = status;
        Date = date;
        Office = office;
        Mail = mail;
    }

    public Long getId() {
        return Id;
    }

    public MailStatus getStatus() {
        return Status;
    }

    public Date getDate() {
        return Date;
    }

    public Office getOffice() {
        return Office;
    }

    public Mail getMail() {
        return Mail;
    }

    public void setStatus(MailStatus status) {
        Status = status;
    }

    public void setDate(Date date) {
        Date = date;
    }

    public void setOffice(Office office) {
        Office = office;
    }

    public void setMail(Mail mail) {
        Mail = mail;
    }
}
