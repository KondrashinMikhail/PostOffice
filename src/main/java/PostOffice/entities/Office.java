package PostOffice.entities;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@AllArgsConstructor
public class Office {
    @Id
    private Long Index;
    private String Name;
    private String Address;
    @ManyToMany
    private List<Mail> Mails = new ArrayList<>();

    public Office(Long index, String name, String address) {
        Index = index;
        Name = name;
        Address = address;
    }

    public void addMail(Mail Mail) {
        Mails.add(Mail);
    }

    public void deleteMail(Mail Mail) {
        Mails.remove(Mail);
    }
}
