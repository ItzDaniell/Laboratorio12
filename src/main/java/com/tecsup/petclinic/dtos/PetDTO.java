package com.tecsup.petclinic.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * 
 * @author jgomezm
 *
 */

@NoArgsConstructor
@AllArgsConstructor
@Data
public class PetDTO {

	private Integer id;
	
	private String name;
	
	private int typeId;

	private int ownerId;

	private String birthDate;

}
