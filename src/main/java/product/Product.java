package product;

import ovchipkaart.OVChipkaart;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
public class Product {
    @Id
    @GeneratedValue
    @Column(name = "product_nummer")
    private int productNummer;
    private String naam;
    private String beschrijving;
    private double prijs;

    @ManyToMany(mappedBy = "producten")
    private List<OVChipkaart> ovChipkaarten;

    public Product(){}

    public Product(String naam, String beschrijving, double prijs){
        this.setProductNummer(productNummer);
        this.setNaam(naam);
        this.setBeschrijving(beschrijving);
        this.setPrijs(prijs);
        this.ovChipkaarten = new ArrayList<OVChipkaart>();
    }


    public int getProductNummer() {
        return productNummer;
    }

    public void setProductNummer(int productNummer) {
        this.productNummer = productNummer;
    }

    public String getNaam() {
        return naam;
    }

    public void setNaam(String naam) {
        this.naam = naam;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }

    public void voegOVKaartToe(OVChipkaart ov){
        this.ovChipkaarten.add(ov);
    }

    public void verwijderOVKaart(OVChipkaart ov){
        this.ovChipkaarten.remove(ov);
    }

    public List<OVChipkaart> getOVKaarten(){
        return ovChipkaarten;
    }

    @Override
    public String toString() {
        return "#" + productNummer +" " + beschrijving + " - " + prijs;
    }
}
