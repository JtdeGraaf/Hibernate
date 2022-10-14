package ovchipkaart;

import adres.Adres;
import org.hibernate.Session;
import reiziger.Reiziger;

import java.util.List;

public class OVChipkaartDAOHibernate implements OVChipkaartDAO{
    private final Session session;

    public OVChipkaartDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(OVChipkaart ov) {
        try {
            session.beginTransaction();
            session.save(ov);
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
    public boolean update(OVChipkaart ov) {
        try {
            session.beginTransaction();
            session.update(ov);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(OVChipkaart ov) {
        try {
            session.beginTransaction();
            session.delete(ov);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
        return true;
    }

    @Override
    public OVChipkaart findByKaartnummer(int kaartNummer) {
        try {
            session.beginTransaction();
            OVChipkaart ov = session.get(OVChipkaart.class, kaartNummer);
            session.getTransaction().commit();
            return ov;
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }


    @Override
    public List<OVChipkaart> findAll() {
        try {
            session.beginTransaction();
            List kaarten = session.createQuery("from OVChipkaart ").list();
            session.getTransaction().commit();
            return kaarten;
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }
}
