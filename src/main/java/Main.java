import adres.Adres;
import adres.AdresDAOHibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
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
        System.out.println("\n\nAdresDAO delete test:");
        System.out.print("Eerst " + adao.findAll().size() + " adressen --- na ");
        adao.delete(a);
        System.out.print(adao.findAll().size() + " adressen");

    }
}
