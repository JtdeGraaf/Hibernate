package ovchipkaart;

import java.util.ArrayList;

public interface OVChipkaartDAO {
    boolean save(OVChipkaart ov);
    boolean update(OVChipkaart ov);
    boolean delete(OVChipkaart ov);
    ArrayList<OVChipkaart> findAll();
    public OVChipkaart findByKaartnummer(int kaartnummer);
}
