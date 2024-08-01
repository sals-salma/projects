package learn.foraging.domain;

import learn.foraging.data.DataException;
import learn.foraging.data.ForageRepository;
import learn.foraging.data.ForagerRepository;
import learn.foraging.data.ItemRepository;
import learn.foraging.models.Category;
import learn.foraging.models.Forage;
import learn.foraging.models.Forager;
import learn.foraging.models.Item;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ForageService {

    private final ForageRepository forageRepository;
    private final ForagerRepository foragerRepository;
    private final ItemRepository itemRepository;

    public ForageService(ForageRepository forageRepository, ForagerRepository foragerRepository, ItemRepository itemRepository) {
        this.forageRepository = forageRepository;
        this.foragerRepository = foragerRepository;
        this.itemRepository = itemRepository;
    }

    public Map<String, Double> reportKgPerItems(LocalDate date){

        // Forage on that date
        List <Forage> dailyForages = this.findByDate(date);
        // Variable that holds map
        Map<String, Double> report = dailyForages.stream()
                // can't map element to null key
                .collect(Collectors.groupingBy(
                        // argument : this is how you build the key
                        f ->  f.getItem().getName(),
                        // argument : how to build a single double from the list that is produced;
                        // convert value to a single value
                        Collectors.summingDouble(f-> f.getKilograms())));
        return report;
    }




    public List<Forage> findByDate(LocalDate date) {

        Map<String, Forager> foragerMap = foragerRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getId(), i -> i));
        Map<Integer, Item> itemMap = itemRepository.findAll().stream()
                .collect(Collectors.toMap(i -> i.getId(), i -> i));

        List<Forage> result = forageRepository.findByDate(date);
        for (Forage forage : result) {
            forage.setForager(foragerMap.get(forage.getForager().getId()));
            forage.setItem(itemMap.get(forage.getItem().getId()));
        }

        return result;
    }

    public Result<Forage> add(Forage forage) throws DataException {
        Result<Forage> result = validate(forage);
        if (!result.isSuccess()) {
            return result;
        }
        
        List <Forage> f = forageRepository.findByDate(forage.getDate());
        for(Forage toCheck : f){
            if( toCheck.getItem().getId()== forage.getItem().getId()){
                result.addErrorMessage("Can't forage the same item on the same day.");
                return result;
            }
        }

        result.setPayload(forageRepository.add(forage));
        return result;
    }

    public int generate(LocalDate start, LocalDate end, int count) throws DataException {

        if (start == null || end == null || start.isAfter(end) || count <= 0) {
            return 0;
        }

        count = Math.min(count, 500);

        ArrayList<LocalDate> dates = new ArrayList<>();
        while (!start.isAfter(end)) {
            dates.add(start);
            start = start.plusDays(1);
        }

        List<Item> items = itemRepository.findAll();
        List<Forager> foragers = foragerRepository.findAll();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            Forage forage = new Forage();
            forage.setDate(dates.get(random.nextInt(dates.size())));
            forage.setForager(foragers.get(random.nextInt(foragers.size())));
            forage.setItem(items.get(random.nextInt(items.size())));
            forage.setKilograms(random.nextDouble() * 5.0 + 0.1);
            forageRepository.add(forage);
        }

        return count;
    }

    private Result<Forage> validate(Forage forage) {

        Result<Forage> result = validateNulls(forage);
        if (!result.isSuccess()) {
            return result;
        }

        validateFields(forage, result);
        if (!result.isSuccess()) {
            return result;
        }

        validateChildrenExist(forage, result);

        return result;
    }

    private Result<Forage> validateNulls(Forage forage) {
        Result<Forage> result = new Result<>();

        if (forage == null) {
            result.addErrorMessage("Nothing to save.");
            return result;
        }

        if (forage.getDate() == null) {
            result.addErrorMessage("Forage date is required.");
        }

        if (forage.getForager() == null) {
            result.addErrorMessage("Forager is required.");
        }

        if (forage.getItem() == null) {
            result.addErrorMessage("Item is required.");
        }
        return result;
    }

    private void validateFields(Forage forage, Result<Forage> result) {
        // No future dates.
        if (forage.getDate().isAfter(LocalDate.now())) {
            result.addErrorMessage("Forage date cannot be in the future.");
        }

        if (forage.getKilograms() <= 0 || forage.getKilograms() > 250.0) {
            result.addErrorMessage("Kilograms must be a positive number less than 250.0");
        }

    }

    private void validateChildrenExist(Forage forage, Result<Forage> result) {

        if (forage.getForager().getId() == null
                || foragerRepository.findById(forage.getForager().getId()) == null) {
            result.addErrorMessage("Forager does not exist.");
        }

        if (itemRepository.findById(forage.getItem().getId()) == null) {
            result.addErrorMessage("Item does not exist.");
        }
    }

    public Map<Category, BigDecimal> reportTotalPerCategory(LocalDate date) {
        List<Forage> dailyForages = findByDate(date);
        BigDecimal categoryOne = BigDecimal.ZERO;
        BigDecimal categoryTwo = BigDecimal.ZERO;
        BigDecimal categoryThree = BigDecimal.ZERO;
        BigDecimal categoryFour = BigDecimal.ZERO;

        //add to each of those big decimals
        for(Forage forage : dailyForages){
            BigDecimal value = calculatedValue(forage);
            switch (forage.getItem().getCategory()){
                case EDIBLE:
                    categoryOne = categoryOne.add(value);
                    break;
                case INEDIBLE:
                    categoryTwo = categoryTwo.add(value);
                    break;
                case MEDICINAL:
                    categoryThree = categoryThree.add(value);
                    break;
                case POISONOUS:
                    categoryFour = categoryFour.add(value);
                    break;
            }
        }
        //actual report
        Map<Category, BigDecimal> report = new HashMap<>();
        report.put(Category.EDIBLE, categoryOne);
        report.put(Category.INEDIBLE, categoryTwo);
        report.put(Category.MEDICINAL, categoryThree);
        report.put(Category.POISONOUS, categoryFour);
        return report;
    }

    private BigDecimal calculatedValue(Forage forage) {
        BigDecimal pricePer = forage.getItem().getDollarPerKilogram();
        Double numberOfKg = forage.getKilograms();
        BigDecimal convertNum = BigDecimal.valueOf(numberOfKg);
        BigDecimal totalPrice = pricePer.multiply(convertNum);

        return totalPrice;
    }


}
