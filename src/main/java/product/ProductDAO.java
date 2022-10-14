package product;

import ovchipkaart.OVChipkaart;

import java.util.List;

public interface ProductDAO {
    boolean save(Product product);
    boolean update(Product product);
    boolean delete(Product product);
    List<Product> findAll();
    List<Product> findByOVChipkaart(OVChipkaart ov);
    Product findById(int id);
}
