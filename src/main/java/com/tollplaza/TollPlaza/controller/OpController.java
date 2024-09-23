package com.tollplaza.TollPlaza.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import com.tollplaza.TollPlaza.entity.Operators;
import com.tollplaza.TollPlaza.repository.OpRepository;
import com.tollplaza.TollPlaza.service.OpService;

@Controller
public class OpController {
	
	@Autowired
	private OpService opService;
	
	@Autowired
	private OpRepository opRepository;
	
	@GetMapping("/OpLogin")
	public String operatorLogin() {
		return "opLogin";
	}

	
	@GetMapping("/operatorRegister")
	public String operatorRegister() {
		return "operatorRegister";
	}

	@GetMapping("/operatorList")
	public String operatorList() {
		return "operatorList";
	}
	
	@GetMapping("/demo")
	public String demoPage() {
		return "demo";
	}
	
	@GetMapping("/viewPage/{operatorId}")
    public String showViewPage() {
        return "viewPage"; 
        
    }
	
//	@GetMapping("/viewPage")
//    public String showViewPages() {
//        return "viewPage";    
//    }
	
	@GetMapping("/receipt/{operatorId}")
    public String showReceipt() {
        return "receipt";    
    }
	
	@PostMapping("/save")
	@ResponseBody
	public ResponseEntity<String> addOperator(@ModelAttribute Operators o) {
		opService.save(o);
		return new ResponseEntity<>("Operator added successfully", HttpStatus.OK);
	}

	
	@GetMapping("/existing_operators")
	@ResponseBody
	public List<Operators> getAllOperators() {
		return opService.getAllOperators();
	}
	
	@GetMapping("/get_operator/{id}")
	@ResponseBody
	public ResponseEntity<Operators> getOperatorById(@PathVariable Long id) {
		Optional<Operators> opr = opService.findById(id);
		if (opr.isPresent()) {
			return new ResponseEntity<>(opr.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@PutMapping("/editData")
	@ResponseBody
	public ResponseEntity<String> updateOperatorById(@RequestBody Operators operators) {
		Optional<Operators> op = opRepository.findById(operators.getOpId());
		if (op.isPresent()) {
			Operators existsch = op.get();
			existsch.setName(operators.getName());
			existsch.setBooth(operators.getBooth());
			existsch.setEmail(operators.getEmail());
			existsch.setPassword(operators.getPassword());
			
		    opRepository.save(existsch);

			return ResponseEntity.ok ("Product Details Against id " + operators.getOpId() + " updated");
		} else {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Product Details does not exist " + operators.getOpId());
		}
	}
	
	
	@DeleteMapping("/deleteData/{id}")
	public ResponseEntity<String> deleteOperatorById(@PathVariable Long id) {
	    if (opRepository.existsById(id)) {
	    	opRepository.deleteById(id);
	        return ResponseEntity.ok("operator Details Deleted Successfully");
	    } else {
	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("opeartor Not Found");
	    }
	}
	
}
