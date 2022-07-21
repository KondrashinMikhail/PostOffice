package PostOffice.dto;

import PostOffice.entities.History;
import PostOffice.enums.MailStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class HistoryDto {
    private Long id;
    private MailStatus status;
    private Date date;
    private OfficeDto office;
    private MailDto mail;

    private String index;

    public HistoryDto(History history) {
        id = history.getId();
        status = history.getStatus();
        date = history.getDate();
        if (history.getOffice() != null) {
            office = new OfficeDto(history.getOffice());
            index = office.getIndex().toString();
        } else index = "-";
        if (history.getMail() != null) mail = new MailDto(history.getMail());
    }
}
