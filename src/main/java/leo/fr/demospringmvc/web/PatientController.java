package leo.fr.demospringmvc.web;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.coyote.Request;
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

import leo.fr.demospringmvc.dao.PatientRepository;
import leo.fr.demospringmvc.entities.Patient;


@Controller
public class PatientController {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@GetMapping(path = "/index")
	public String index(int numero, Model model) {
		System.out.println("je suis dans index ... !");
		return "index";  // C'est le nom de la Vue vers laquelle on retourne
	}
	

	/**
	 * Liste des patients
	 * @param model
	 * @param page
	 * @param size
	 * @param motcle
	 * @return
	 */
	@GetMapping(path = "/patients")
	public String listPatients(Model model , 
				@RequestParam(name="page", defaultValue = "0") int page,
				@RequestParam(name="size", defaultValue = "5") int size,
				@RequestParam(name="keyword", defaultValue = "") String motcle) {
		Page<Patient> pagePatients ;
		System.out.println("page : " + page  + " size : " + size );
		if ( ! motcle.isEmpty()  ) {
			pagePatients =  patientRepository.findByNameContains(motcle, PageRequest.of(page, size).withSort(Direction.ASC,"id"));
		}else {
			pagePatients =  patientRepository.findAll(PageRequest.of(page, size).withSort(Direction.ASC,"id"));
		}
		model.addAttribute("patients", pagePatients.getContent());
		model.addAttribute("pages", new int[pagePatients.getTotalPages()]);
		System.out.println("Nombre de pages : " + pagePatients.getTotalPages());
		model.addAttribute("currentPage", page);
		model.addAttribute("keyword", motcle);
		model.addAttribute("size", size);
		return "patients";  
	}
	
	@GetMapping(path = "/deletePatient")
	public String deletePatient(Model model , 
				@RequestParam(name="page", defaultValue = "0") int page,
				@RequestParam(name="size", defaultValue = "5") int size,
				@RequestParam(name="keyword", defaultValue = "") String motcle,
				@RequestParam(name="id", defaultValue = "") Long id) {
		System.out.println("page : " + page  + " size : " + size );		
		patientRepository.deleteById(id);
		return "redirect:/patients?keyword=" + motcle + "&size=" + size + "&page=" + page ;
	}
	
	// 2eme solution 
	@GetMapping(path = "/Patient/delete")
	public String delete_Patient(Model model , 
				@RequestParam(name="page", defaultValue = "0") int page,
				@RequestParam(name="size", defaultValue = "5") int size,
				@RequestParam(name="keyword", defaultValue = "") String motcle,
				@RequestParam(name="id", defaultValue = "") Long id) {
		patientRepository.deleteById(id);	
		return "redirect:/patients?keyword=" + motcle + "&size=" + size + "&page=" + page ;
	}
	
	// 3 ème solution
	@GetMapping(path = "/deletePatient2")
	public String deletePatient2(Model model , 
				@RequestParam(name="page", defaultValue = "0") int page,
				@RequestParam(name="size", defaultValue = "5") int size,
				@RequestParam(name="keyword", defaultValue = "") String motcle,
				@RequestParam(name="id", defaultValue = "") Long id) {
		System.out.println("je suis dans deletePatient2 ...");
		patientRepository.deleteById(id);
		return listPatients(model, page, size, motcle);
	}
	
	
	@GetMapping(path = "/formPatient" )
	public String formPatient(Model model){
		model.addAttribute("patient", new Patient());
		model.addAttribute("mode", "new");
		return "formPatient";
	}
	
	@PostMapping(path="/savePatient")
	public String savePatient(Model model, @Valid Patient patient , BindingResult bindingResult){	
		System.out.println(bindingResult.hasErrors());
		System.out.println("Voici le patient dans les paramètres : " + patient);
		if (bindingResult.hasErrors()) {
			System.out.println("une erreur ...");
			return "formPatient";
		}
		model.addAttribute("patient", patient);
		patientRepository.save(patient);	
			return "confirmation";	
	}
	
	@GetMapping(path="/editPatient")
	public String editPatient(	Model model,
								@RequestParam(name="id", defaultValue = "") Long id){	
		Patient patient =  patientRepository.findById(id).get();
		model.addAttribute("patient", patient);
		model.addAttribute("mode", "edit");
		return "formPatient"; 
	}
	
	@ResponseBody
	@GetMapping("/listPatients")
	public List<Patient> list(){		
		return patientRepository.findAll();
	}
	

	@ResponseBody
	@GetMapping("/patients/{id}")
	public Patient getOne(@PathVariable Long id ){	
			return patientRepository.findById(id).get();
	}
	
	@GetMapping(path="/editPatient/{id}")
	public String editPatient2(@PathVariable Long id , Model model){
		Patient patient =  patientRepository.findById(id).get();
		model.addAttribute("patient", patient);
		model.addAttribute("mode", "edit");
		System.out.println("Je suis dans editPatient2 ...");
		return "formPatient";
	}

}
