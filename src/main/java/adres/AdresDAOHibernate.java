package adres;

import org.hibernate.Session;
import reiziger.Reiziger;

import java.time.LocalDate;
import java.util.List;

public class AdresDAOHibernate implements AdresDAO {
    private final Session session;

    public AdresDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Adres adres) {
        try {
            session.beginTransaction();
            session.save(adres);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }
        return true;
    }

    @Override
    public boolean update(Adres adres) {
        try {
            session.beginTransaction();
            session.update(adres);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Adres adres) {
        try {
            session.beginTransaction();
            session.delete(adres);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
        return true;
    }

    @Override
    public Adres findByReiziger(Reiziger reiziger) {
        try {
            session.beginTransaction();
            Reiziger a = session.get(Reiziger.class, reiziger.getId());
            session.getTransaction().commit();
            return a.getAdres();
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }


    @Override
    public List<Adres> findAll() {
        try {
            session.beginTransaction();
            List adressen = session.createQuery("from Adres").list();
            session.getTransaction().commit();
            return adressen;
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }
}
