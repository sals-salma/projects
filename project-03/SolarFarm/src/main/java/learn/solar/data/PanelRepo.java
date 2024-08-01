package learn.solar.data;

import learn.solar.models.Panel;

import java.util.List;

public interface PanelRepo {
    //CRUD will be implemented in my file repo
    //Create: adding a panel;DONE
    Panel add (Panel toCreate) throws DataAccessException;

    List<Panel> findAll();

    //Read: only need find by section;DONE
    List<Panel> findBySection (String section) throws DataAccessException;

    //Update;DONE
    boolean update(Panel updatedPanel) throws DataAccessException;

    //Delete;DONE
    boolean delete(int panelId) throws DataAccessException;

}
