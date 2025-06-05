package com.tecsup.petclinic.services;

import com.tecsup.petclinic.entities.Pet;
import com.tecsup.petclinic.entities.Visit;
import com.tecsup.petclinic.exception.VisitNotFoundException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.time.LocalDate;

/**
 *
 * @author JuanDaniel
 *
 */
public interface VisitService {
    /**
     *
     * @param visit
     * @return
     */
    Visit create(Visit visit);

    /**
     *
     * @param visit
     * @return
     */
    Visit update(Visit visit);

    /**
     *
     * @param id
     * @throws VisitNotFoundException
     */
    void delete(Integer id) throws VisitNotFoundException;

    /**
     *
     * @param id
     * @return
     */
    Visit findById(Integer id) throws VisitNotFoundException;

    /**
     *
     * @param date
     * @return
     */
    List<Visit> findByDate(LocalDate date) throws VisitNotFoundException;

    List<Visit> findAll();
}
