import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "ov_chipkaart")
public class OVChipkaart {
    @Id
    @GeneratedValue
    @Column(name = "kaart_nummer")
    private int kaartNummer;
    @Transient
    private LocalDate geldigTot;
    private int klasse;
    private double saldo;

    @ManyToOne
    @JoinColumn(name = "reiziger_id")
    private Reiziger reiziger;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<Product> producten;

    public OVChipkaart(){}

    public OVChipkaart(LocalDate geldigTot, int klasse, double saldo, Reiziger reiziger){
        this.kaartNummer = kaartNummer;
        this.geldigTot = geldigTot;
        this.klasse = klasse;
        this.saldo = saldo;
        this.reiziger = reiziger;
        this.reiziger.voegOVKaartToe(this);
        this.producten = new ArrayList<>();
    }

    public int getKaartNummer() {
        return kaartNummer;
    }

    public LocalDate getGeldigTot() {
        return geldigTot;
    }

    public int getKlasse() {
        return klasse;
    }

    public double getSaldo() {
        return saldo;
    }

    public Reiziger getReiziger() {
        return reiziger;
    }

    @Override
    public String toString() {
        return "Kaartnummer: " + kaartNummer + " - " + klasse + "e klasse, saldo: " + saldo + "\n deze kaart is van: " + reiziger;
    }

    public List<Product> getProducten(){
        return this.producten;
    }

    public void voegProductToe(Product product){
        this.producten.add(product);
    }

    public void verwijderProduct(Product product){
        this.producten.remove(product);
    }


    public int getReizigerId(){
        return reiziger.getId();
    }


}
