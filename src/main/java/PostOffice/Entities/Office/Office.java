package PostOffice.Entities.Office;

import PostOffice.Entities.Mail.Mail;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Office {
    @Id
    private Long Index;
    private String Name;
    private String Address;
    @ManyToMany
    private List<Mail> Mails = new ArrayList<>();

    public Office() {
    }

    public Office(Long index, String name, String address) {
        Index = index;
        Name = name;
        Address = address;
    }

    public Long getIndex() {
        return Index;
    }

    public String getName() {
        return Name;
    }

    public String getAddress() {
        return Address;
    }

    public List<Mail> getMails() {
        return Mails;
    }

    public void setIndex(Long index) {
        Index = index;
    }

    public void setName(String name) {
        Name = name;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void addMail(Mail Mail) {
        Mails.add(Mail);
    }

    public void deleteMail(Mail Mail) {
        Mails.remove(Mail);
    }
}
