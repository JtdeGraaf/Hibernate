package reiziger;

import org.hibernate.Session;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

public class ReizigerDAOHibernate implements ReizigerDAO {
    private final Session session;

    public ReizigerDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Reiziger reiziger) {
        try {
            session.beginTransaction();
            session.save(reiziger);
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
    public boolean update(Reiziger reiziger) {
        try {
            session.beginTransaction();
            session.update(reiziger);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Reiziger reiziger) {
        try {
            session.beginTransaction();
            session.delete(reiziger);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
        return true;
    }

    @Override
    public Reiziger findById(int id) {
        try {
            session.beginTransaction();
            Reiziger r = session.get(Reiziger.class, id);
            session.getTransaction().commit();
            return r;
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public List<Reiziger> findByGbdatum(String geboortedatum) {
        try {
            session.beginTransaction();
            List reizigers = session.createQuery("from Reiziger where geboortedatum = ?1").setParameter(1, LocalDate.parse(geboortedatum)).list();
            session.getTransaction().commit();
            return reizigers;
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public List<Reiziger> findAll() {
        try {
            session.beginTransaction();
            List reizigers = session.createQuery("from Reiziger").list();
            session.getTransaction().commit();
            return reizigers;
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }
}
