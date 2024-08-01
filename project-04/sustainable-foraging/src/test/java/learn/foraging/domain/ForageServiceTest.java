package learn.foraging.domain;

import learn.foraging.data.DataException;
import learn.foraging.data.ForageRepositoryDouble;
import learn.foraging.data.ForagerRepositoryDouble;
import learn.foraging.data.ItemRepositoryDouble;
import learn.foraging.models.Category;
import learn.foraging.models.Forage;
import learn.foraging.models.Forager;
import learn.foraging.models.Item;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ForageServiceTest {

    // write tests for reports and add (duplicate)
    ForageService service = new ForageService(
            new ForageRepositoryDouble(),
            new ForagerRepositoryDouble(),
            new ItemRepositoryDouble());

    @Test
    void shouldAdd() throws DataException {
        Forage forage = new Forage();
        forage.setDate(LocalDate.now());
        forage.setForager(ForagerRepositoryDouble.FORAGER);
        forage.setItem(ItemRepositoryDouble.ITEM);
        forage.setKilograms(0.5);

        Result<Forage> result = service.add(forage);
        assertTrue(result.isSuccess());
        assertNotNull(result.getPayload());
        assertEquals(36, result.getPayload().getId().length());
    }

    @Test
    void shouldNotAddWhenForagerNotFound() throws DataException {

        Forager forager = new Forager();
        forager.setId("30816379-188d-4552-913f-9a48405e8c08");
        forager.setFirstName("Ermengarde");
        forager.setLastName("Sansom");
        forager.setState("NM");

        Forage forage = new Forage();
        forage.setDate(LocalDate.now());
        forage.setForager(forager);
        forage.setItem(ItemRepositoryDouble.ITEM);
        forage.setKilograms(0.5);

        Result<Forage> result = service.add(forage);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotAddWhenItemNotFound() throws DataException {

        Item item = new Item(11, "Dandelion", Category.EDIBLE, new BigDecimal("0.05"));

        Forage forage = new Forage();
        forage.setDate(LocalDate.now());
        forage.setForager(ForagerRepositoryDouble.FORAGER);
        forage.setItem(item);
        forage.setKilograms(0.5);

        Result<Forage> result = service.add(forage);
        assertFalse(result.isSuccess());
    }

    @Test
    void shouldNotForageSameItemOnSameDay() throws DataException {
        Forager forager = new Forager();
        forager.setId("30816379-188d-4552-913f-9a48405e8c08");
        forager.setFirstName("Ermengarde");
        forager.setLastName("Sansom");
        forager.setState("NM");
        //same day as the one in my double
        LocalDate date = LocalDate.of(2020, 6, 26);

        Forage forage = new Forage();
        forage.setDate(date);
        forage.setForager(forager);
        forage.setItem(ItemRepositoryDouble.ITEM);
        forage.setKilograms(0.5);

        Result<Forage> result = service.add(forage);
        assertFalse(result.isSuccess());

    }
    @Test
    void shouldShowKgOfEachItemCollectedOnOneDay() throws DataException {
        LocalDate date = LocalDate.of(2020, 6, 26);

        Map<String, Double> actual = new HashMap<>();
        actual.put("Chanterelle", 1.25);

        Map<String, Double> expected = service.reportKgPerItems(date);

        assertEquals(actual, expected);

    }
    @Test
    void shouldNotShowKgOfEachItemCollectedOnNullDate() throws DataException {
        LocalDate date = null;

        Map<String, Double> actual = new HashMap<>();
        actual.put("Chanterelle", 1.25);

        Map<String, Double> expected = service.reportKgPerItems(date);

        assertNotEquals(actual, expected);

    }

    @Test
    void shouldShowTotalValueOfEachCategoryCollectedOnOneDay() throws DataException {
        LocalDate date = LocalDate.of(2020, 6, 26);

        Map<Category, BigDecimal> actual = new HashMap<>();
        actual.put(Category.EDIBLE, BigDecimal.valueOf(12.4875));
        actual.put(Category.POISONOUS, BigDecimal.ZERO);
        actual.put(Category.MEDICINAL, BigDecimal.ZERO);
        actual.put(Category.INEDIBLE, BigDecimal.ZERO);

        Map<Category, BigDecimal>  expected = service.reportTotalPerCategory(date);

        assertEquals(actual, expected);

    }
    @Test
    void shouldNotShowTotalValueOfEachCategoryCollectedOnNullDay() throws DataException {
        LocalDate date = null;

        Map<Category, BigDecimal> actual = new HashMap<>();
        actual.put(Category.EDIBLE, BigDecimal.valueOf(12.4875));
        actual.put(Category.POISONOUS, BigDecimal.ZERO);
        actual.put(Category.MEDICINAL, BigDecimal.ZERO);
        actual.put(Category.INEDIBLE, BigDecimal.ZERO);

        Map<Category, BigDecimal>  expected = service.reportTotalPerCategory(date);

        assertNotEquals(actual, expected);

    }


}