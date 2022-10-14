package adres;

import reiziger.Reiziger;

import javax.persistence.*;

@Entity
@Table(name = "adres")
public class Adres {
    @Id
    @GeneratedValue
    @Column(name = "adres_id")
    private int id;
    private String postcode;
    private String huisnummer;
    private String straat;
    private String woonplaats;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;

    public Adres(){}

    public Adres(String postcode, String huisnummer, String straat, String woonplaats, Reiziger reiziger){
        this.id = id;
        this.postcode = postcode;
        this.huisnummer = huisnummer;
        this.straat = straat;
        this.woonplaats = woonplaats;
        this.reiziger = reiziger;
        reiziger.setAdres(this);
    }

    public String getPostcode() {
        return postcode;
    }

    public void setPostcode(String postcode) {
        this.postcode = postcode;
    }

    @Override
    public String toString() {
        return "#" + getId() + " " + postcode;
    }

    public int getId() {
        return id;
    }

    public String getHuisnummer() {
        return huisnummer;
    }

    public String getStraat() {
        return straat;
    }

    public String getWoonplaats() {
        return woonplaats;
    }
}
