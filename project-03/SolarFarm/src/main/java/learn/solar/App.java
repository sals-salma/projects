package learn.solar;

import learn.solar.data.DataAccessException;
import learn.solar.data.PanelFileRepository;
import learn.solar.data.PanelRepo;
import learn.solar.domain.PanelService;
import learn.solar.ui.Console;
import learn.solar.ui.PanelController;
import learn.solar.ui.PanelView;

public class App {
//    - instantiate all required objects: repository, service, view, and controller
//    - run the controller

    public static void main(String[] args) throws DataAccessException {
        Console input = new Console();

        PanelRepo repository = new PanelFileRepository("./data/panels.csv");
        PanelService service = new PanelService(repository);

        PanelView view = new PanelView(input);

        PanelController controller = new PanelController(service, view);
        controller.run();

    }
}
