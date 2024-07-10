package security.loginsecurity.memo.service;

import security.loginsecurity.memo.domain.entity.Meal;
import security.loginsecurity.memo.domain.repository.MealRepository;
import security.loginsecurity.memo.dto.MealDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealService {
    private final MealRepository mealRepository;

    @Autowired
    public MealService(MealRepository mealRepository) {
        this.mealRepository = mealRepository;
    }

    public List<MealDto> getMealsByTypeAndDate(String mealType, LocalDate date) {
        List<Meal> meals = mealRepository.findByMealTypeAndDate(mealType, date);
        return meals.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public void saveMeal(MealDto mealDto) {
        Meal meal = convertToEntity(mealDto);
        mealRepository.save(meal);
    }

    public void updateMeal(MealDto mealDto) {
        Meal existingMeal = mealRepository.findById(mealDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("Meal not found with id: " + mealDto.getId()));
        existingMeal.setMealType(mealDto.getMealType());
        existingMeal.setDate(mealDto.getDate());
        existingMeal.setTime(mealDto.getTime());
        existingMeal.setMenus(String.join(", ", mealDto.getMenus()));
        existingMeal.setRating(mealDto.getRating());
        mealRepository.save(existingMeal);
    }

    private MealDto convertToDto(Meal meal) {
        MealDto dto = new MealDto();
        dto.setId(meal.getId());
        dto.setMealType(meal.getMealType());
        dto.setDate(meal.getDate());
        dto.setTime(meal.getTime());
        dto.setMenus(Arrays.asList(meal.getMenus().split(", ")));
        dto.setRating(meal.getRating());
        return dto;
    }

    private Meal convertToEntity(MealDto mealDto) {
        Meal meal = new Meal();
        meal.setId(mealDto.getId());
        meal.setMealType(mealDto.getMealType());
        meal.setDate(mealDto.getDate());
        meal.setTime(mealDto.getTime());
        meal.setMenus(String.join(", ", mealDto.getMenus()));
        meal.setRating(mealDto.getRating());
        return meal;
    }
}
