package tomskasu.sancor.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tomskasu.sancor.repo.UserEntityRepo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAmount;
import java.time.temporal.TemporalField;
import java.util.Calendar;
import java.util.Date;

@Service
public class UserEntityService {

    @Autowired
    UserEntityRepo entityRepo;

    public int calculateAgeFromDate(String birthDateStr) {




            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");


            LocalDate birthDate = LocalDate.parse(birthDateStr, formatter);

            // Calculate the age
            Period age = Period.between(birthDate, LocalDate.now());


            return age.getYears();
    }



}
