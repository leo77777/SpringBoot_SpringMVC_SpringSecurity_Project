package leo.fr.demospringmvc;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import leo.fr.demospringmvc.dao.PatientRepository;
import leo.fr.demospringmvc.entities.Patient;

@SpringBootApplication
public class DemospringmvcApplication implements CommandLineRunner {
	
	@Autowired
	private PatientRepository patientRepository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;


	public static void main(String[] args) {
		SpringApplication.run(DemospringmvcApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		patientRepository.save(new Patient(null, "Rere", new Date(), false, 3));
		patientRepository.save(new Patient(null, "Vyvy", new Date(), false, 7));
		patientRepository.save(new Patient(null, "Lala", new Date(), false, 4)); 		

		System.out.println("Voici le password encoder : " + passwordEncoder.encode("1234"));
		
		patientRepository.findAll().forEach(p->{
				System.out.println(p.getName());
		});
	}
}
