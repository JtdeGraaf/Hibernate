package product;

import adres.Adres;
import org.hibernate.Session;
import ovchipkaart.OVChipkaart;
import reiziger.Reiziger;

import java.util.List;

public class ProductDAOHibernate implements ProductDAO{
    private final Session session;

    public ProductDAOHibernate(Session session) {
        this.session = session;
    }

    @Override
    public boolean save(Product product) {
        try {
            session.beginTransaction();
            session.save(product);
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
    public boolean update(Product product) {
        try {
            session.beginTransaction();
            session.update(product);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
        return true;
    }

    @Override
    public boolean delete(Product product) {
        try {
            session.beginTransaction();
            session.delete(product);
            session.getTransaction().commit();
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return false;
        }
        return true;
    }

    @Override
    public List<Product> findByOVChipkaart(OVChipkaart ov) {
        try {
            session.beginTransaction();
            OVChipkaart ovchipkaart = session.get(OVChipkaart.class, ov.getKaartNummer());
            session.getTransaction().commit();
            return ovchipkaart.getProducten();
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }

    @Override
    public Product findById(int id) {
        try {
            session.beginTransaction();
            Product p = session.get(Product.class, id);
            session.getTransaction().commit();
            return p;
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }


    @Override
    public List<Product> findAll() {
        try {
            session.beginTransaction();
            List producten = session.createQuery("from Product").list();
            session.getTransaction().commit();
            return producten;
        }
        catch (Exception e) {
            session.getTransaction().rollback();
            return null;
        }
    }
}
