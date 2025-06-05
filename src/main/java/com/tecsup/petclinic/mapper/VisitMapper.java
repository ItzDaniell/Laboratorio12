package com.tecsup.petclinic.mapper;

import com.tecsup.petclinic.dtos.PetDTO;
import com.tecsup.petclinic.dtos.VisitDTO;
import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Visit;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValueMappingStrategy;
import org.mapstruct.factory.Mappers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Mapper(componentModel = "spring", nullValueMappingStrategy =  NullValueMappingStrategy.RETURN_DEFAULT)
public interface VisitMapper {
    VisitMapper INSTANCE = Mappers.getMapper(VisitMapper.class);

    //@Mapping(target = "name", source = "name")
    @Mapping(source = "date", target = "date")
    Visit toVisit(VisitDTO visitDTO);

    default LocalDate stringToDate(String dateStr) {

        LocalDate date = null;
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            return LocalDate.parse(dateStr, dateFormat);
        } catch (DateTimeException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Mapping(source = "date", target = "date")
    VisitDTO toVisitTO(Visit visit);

    default String dateToString(LocalDate date) {

        if (date != null ) {
            DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            return date.format(dateFormat);
        } else {
            return "";
        }
    }
    List<VisitDTO> toVisitDTOList(List<Visit> visitList);
    List<Visit> toVisitList(List<VisitDTO> visitDTOList);
}
