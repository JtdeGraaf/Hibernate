import adres.Adres;
import adres.AdresDAOHibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import ovchipkaart.OVChipkaart;
import ovchipkaart.OVChipkaartDAOHibernate;
import product.Product;
import product.ProductDAOHibernate;
import reiziger.Reiziger;
import reiziger.ReizigerDAOHibernate;

import javax.persistence.metamodel.EntityType;
import javax.persistence.metamodel.Metamodel;
import java.sql.SQLException;
import java.time.LocalDate;


/**
 * Testklasse - deze klasse test alle andere klassen in deze package.
 *
 * System.out.println() is alleen in deze klasse toegestaan (behalve voor exceptions).
 *
 * @author tijmen.muller@hu.nl
 */
public class Main {
    // Creëer een factory voor Hibernate sessions.
    private static final SessionFactory factory;

    static {
        try {
            // Create a Hibernate session factory
            factory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    /**
     * Retouneer een Hibernate session.
     *
     * @return Hibernate session
     * @throws HibernateException
     */
    private static Session getSession() throws HibernateException {
        return factory.openSession();
    }

    public static void main(String[] args) throws SQLException {
        testFetchAll();
        testReizigerDAO();
        testAdresDAO();
        testOVChipkaartDAO();
        testProductDAO();
    }

    /**
     * P6. Haal alle (geannoteerde) entiteiten uit de database.
     */
    private static void testFetchAll() {
        Session session = getSession();
        try {
            Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                Query query = session.createQuery("from " + entityType.getName());

                System.out.println("[Test] Alle objecten van type " + entityType.getName() + " uit database:");
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
                System.out.println();
            }
        } finally {
            session.close();
        }
    }

    private static void testReizigerDAO(){
        Session session = getSession();
        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(session);
        System.out.println("\nReizigerDAO findall test:");
        System.out.println(rdao.findAll());
        System.out.println("\nReizigerDAO findbyGB test:");
        System.out.println(rdao.findByGbdatum("2000-10-10"));
        System.out.println("\nReizigerDAO findById test:");
        System.out.println(rdao.findById(100));
        System.out.println("\nReizigerDAO save test:");
        System.out.print("Eerst " + rdao.findAll().size() + " reizigers --- na ");
        Reiziger r = new Reiziger("K", "de", "Jong", LocalDate.of(2000, 11, 11));
        rdao.save(r);
        System.out.print(rdao.findAll().size() + " reizigers");
        System.out.println("\n\nReizigerDAO update test: \nvoor update: " + r);
        r.setAchternaam("Test");
        rdao.update(r);
        System.out.println("na update: " + rdao.findById(r.getId()));
        System.out.println("\n\nReizigerDAO delete test:");
        System.out.print("Eerst " + rdao.findAll().size() + " reizigers --- na ");
        rdao.delete(r);
        System.out.print(rdao.findAll().size() + " reizigers");
    }

    private static void testAdresDAO(){
        Session session = getSession();
        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(session);
        AdresDAOHibernate adao = new AdresDAOHibernate(session);
        System.out.println("\n\nAdresDAO findall test:");
        System.out.println(adao.findAll());
        System.out.println("\nAdresDAO findByReiziger test:");
        System.out.println(adao.findByReiziger(rdao.findById(1)));
        System.out.println("\nAdresDAO save test:");
        System.out.print("Eerst " + adao.findAll().size() + " adressem --- na ");
        Adres a = new Adres("8888PP", "33", "Straat weg", "Utrecht", rdao.findById(100));
        adao.save(a);
        System.out.print(adao.findAll().size() + " adressen");
        System.out.println("\n\nAdresDAO update test: \nvoor update: " + a);
        a.setPostcode("0000XX");
        adao.update(a);
        System.out.println("na update: " + adao.findByReiziger(rdao.findById(100)));
        System.out.println("\n\nAdresDAO delete test:");
        System.out.print("Eerst " + adao.findAll().size() + " adressen --- na ");
        adao.delete(a);
        System.out.print(adao.findAll().size() + " adressen");
    }

    private static void testOVChipkaartDAO(){
        Session session = getSession();
        ReizigerDAOHibernate rdao = new ReizigerDAOHibernate(session);
        OVChipkaartDAOHibernate odao = new OVChipkaartDAOHibernate(session);
        System.out.println("\n\nOVChipkaartDAO findall test:");
        System.out.println(odao.findAll());
        System.out.println("\nOVChipkaartDAO findByKaartnummer test:");
        System.out.println(odao.findByKaartnummer(18326));
        System.out.println("\nOVChipkaartDAO save test:");
        System.out.print("Eerst " + odao.findAll().size() + " OV kaarten --- na ");
        OVChipkaart ov = new OVChipkaart(LocalDate.now().plusYears(1), 2, 200.5, rdao.findById(100));
        odao.save(ov);
        System.out.print(odao.findAll().size() + " OV kaarten");
        System.out.println("\n\nOVChipkaartDAO update test: \nvoor update: " + ov);
        ov.setKlasse(1);
        odao.update(ov);
        System.out.println("na update: " + odao.findByKaartnummer(ov.getKaartNummer()));
        System.out.println("\n\nOVChipkaartDAO delete test:");
        System.out.print("Eerst " + odao.findAll().size() + " OV kaarten --- na ");
        odao.delete(ov);
        System.out.print(odao.findAll().size() + " OV kaarten");

    }

    private static void testProductDAO(){
        Session session = getSession();
        ProductDAOHibernate pdao = new ProductDAOHibernate(session);
        OVChipkaartDAOHibernate odao = new OVChipkaartDAOHibernate(session);
        System.out.println("\n\nProductDAO findall test:");
        System.out.println(pdao.findAll());
        System.out.println("\nProductDAO findbyOVChipkaart test:");
        System.out.println(pdao.findByOVChipkaart(odao.findByKaartnummer(79625)));
        System.out.println("\nProductDAO findById test:");
        System.out.println(pdao.findById(1));
        System.out.println("\nProductDAO save test:");
        System.out.print("Eerst " + pdao.findAll().size() + " producten --- na ");
        Product p = new Product("Test", "Test", 50);
        pdao.save(p);
        System.out.print(pdao.findAll().size() + " producten");
        System.out.println("\n\nProductDAO update test: \nvoor update: " + p);
        p.setBeschrijving("TestTestTest");
        pdao.update(p);
        System.out.println("na update: " + pdao.findById(p.getProductNummer()));
        System.out.println("\n\nProductDAO delete test:");
        System.out.print("Eerst " + pdao.findAll().size() + " producten --- na ");
        pdao.delete(p);
        System.out.print(pdao.findAll().size() + " producten");

    }
}
