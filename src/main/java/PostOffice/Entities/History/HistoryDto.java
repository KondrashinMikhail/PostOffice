package PostOffice.Entities.History;

import PostOffice.Entities.Mail.Mail;
import PostOffice.Entities.Mail.MailDto;
import PostOffice.Entities.Mail.MailStatus;
import PostOffice.Entities.Office.Office;
import PostOffice.Entities.Office.OfficeDto;

import java.util.Date;

public class HistoryDto {
    private Long Id;
    private MailStatus Status;
    private Date Date;
    private OfficeDto Office;
    private MailDto Mail;

    public HistoryDto() {
    }

    public HistoryDto(History history) {
        Id = history.getId();
        Status = history.getStatus();
        Date = history.getDate();
        if (history.getOffice() != null) Office = new OfficeDto(history.getOffice());
        if (history.getMail() != null) Mail = new MailDto(history.getMail());
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

    public OfficeDto getOffice() {
        return Office;
    }

    public MailDto getMail() {
        return Mail;
    }

    public void setOffice(OfficeDto office) {
        Office = office;
    }

    public void setMail(MailDto mail) {
        Mail = mail;
    }
}
