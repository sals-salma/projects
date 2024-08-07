package learn.foraging.data;

import learn.foraging.models.Forager;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ForagerRepositoryDouble implements ForagerRepository {

    public final static Forager FORAGER = makeForager();

    private final ArrayList<Forager> foragers = new ArrayList<>();

    public ForagerRepositoryDouble() {
        foragers.add(FORAGER);
    }

    @Override
    public Forager findById(String id) {
        return foragers.stream()
                .filter(i -> i.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<Forager> findAll() {
        List <Forager> toReturn = new ArrayList<>();
        for (Forager toCopy : foragers ){
            toReturn.add(new Forager(toCopy));
        }
        return toReturn;
    }

    @Override
    public List<Forager> findByState(String stateAbbr) {
        return foragers.stream()
                .filter(i -> i.getState().equalsIgnoreCase(stateAbbr))
                .collect(Collectors.toList());
    }

    private static Forager makeForager() {
        Forager forager = new Forager();
        forager.setId("0e4707f4-407e-4ec9-9665-baca0aabe88c");
        forager.setFirstName("Jilly");
        forager.setLastName("Sisse");
        forager.setState("GA");
        return forager;
    }
    @Override
    public Forager add(Forager toAdd) throws DataException {
        //make test for null input
        if (toAdd == null) {
            throw new DataException("Trying to add null Forager");
        }
        //make test for good input
        List<Forager> allForagers = findAll();

        toAdd.setId(java.util.UUID.randomUUID().toString());
        allForagers.add(new Forager((toAdd)));
        return toAdd;
    }

}
