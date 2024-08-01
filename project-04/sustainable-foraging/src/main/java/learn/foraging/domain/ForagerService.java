package learn.foraging.domain;

import learn.foraging.data.DataException;
import learn.foraging.data.ForagerRepository;
import learn.foraging.models.Forage;
import learn.foraging.models.Forager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ForagerService {

    private final ForagerRepository repository;

    public ForagerService(ForagerRepository repository) {
        this.repository = repository;
    }

    public List<Forager> findByState(String stateAbbr) {
        return repository.findByState(stateAbbr);
    }

    public List<Forager> findByLastName(String prefix) {
        return repository.findAll().stream()
                .filter(i -> i.getLastName().startsWith(prefix))
                .collect(Collectors.toList());
    }

    public Result<Forager> add(Forager forager) throws DataException {
        Result<Forager> result = validate(forager);
        if (!result.isSuccess()) {
            return result;
        }
        //FIXED!
        List <Forager> f = repository.findAll();
        for(Forager toCheck : f){
            if(toCheck.getFirstName().equalsIgnoreCase(forager.getFirstName()) && toCheck.getLastName()
                    .equalsIgnoreCase(forager.getLastName())
                    && toCheck.getState().equalsIgnoreCase(forager.getState())){
                result.addErrorMessage("Can't add duplicate Forager.");
                return result;
            }
        }

        result.setPayload(repository.add(forager));
        return result;
    }

    private Result<Forager> validate(Forager forager) {
        Result<Forager> result = new Result<>();

        if (forager == null) {
            result.addErrorMessage("Forager is required");
            return result;
        }
        if (forager.getFirstName() == null && forager.getFirstName().isBlank()){
            result.addErrorMessage("First name is required");
        }
        if (forager.getLastName() == null && forager.getLastName().isBlank()){
            result.addErrorMessage("Last name is required");
        }
        if (forager.getState() == null && forager.getState().isBlank()){
            result.addErrorMessage("State is required");
        }
        return result;
    }
}
