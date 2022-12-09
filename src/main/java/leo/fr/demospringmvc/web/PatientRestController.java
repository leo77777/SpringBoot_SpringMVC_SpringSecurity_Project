package leo.fr.demospringmvc.web;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import leo.fr.demospringmvc.dao.PatientRepository;
import leo.fr.demospringmvc.entities.Patient;


@RestController
public class PatientRestController {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@GetMapping("/listPatientsRestControleur")
	public List<Patient> list(){		
		return patientRepository.findAll();
	}

	@GetMapping("/patientsRestControleur/{id}")
	public Patient getOne(@PathVariable Long id ){		
		return patientRepository.findById(id).get();
	}

}
