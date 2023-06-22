package com.wow.employee.info.api;

import java.util.List;

import javax.validation.Valid;

import org.bson.Document;
import org.springframework.context.MessageSource;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.wow.employee.info.exception.ProcessException;
import com.wow.employee.info.models.Employee;
import com.wow.employee.info.models.Response;
import com.wow.employee.info.models.ValidationErrorResponse;
import com.wow.employee.info.repository.EmployeeRepository;

@RestController
public class EmployeeController {

	private final EmployeeRepository employeeRepository;

	private final MessageSource messageSource;

	public EmployeeController(EmployeeRepository employeeRepository, MessageSource messageSource) {
		this.employeeRepository = employeeRepository;
		this.messageSource = messageSource;
	}

	/**
	 * Add new employee
	 *
	 * @param employee
	 * @param result
	 * @return
	 */
	@PostMapping("/add-employee")
	public ResponseEntity<Object> addEmploye(@RequestBody @Valid Employee employee, BindingResult result) {
		try {
			if (result.hasErrors()) {
				ValidationErrorResponse errorResponse = new ValidationErrorResponse();
				for (ObjectError error : result.getAllErrors()) {
					if (error instanceof FieldError) {
						FieldError fieldError = (FieldError) error;
						String fieldName = fieldError.getField();
						String errorMessage = fieldError.getDefaultMessage();
						errorResponse.addError(fieldName, errorMessage);
					} else {
						errorResponse.addError(error.getDefaultMessage());
					}
				}
				return ResponseEntity.badRequest().body(errorResponse);
			}
			employeeRepository.insert(employee);
			Response successResponse = new Response();
			successResponse.setResponseCode(messageSource.getMessage("response.success.code", null, null));
			successResponse.setResponseMsg(messageSource.getMessage("response.success.message", null, null));
			return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
		} catch (DuplicateKeyException e) {
			throw new ProcessException("response.mongo.duplicate");
		}
	}

	/**
	 * Get all employees from the db
	 *
	 * @return
	 */
	@GetMapping("/get-all-employees")
	public ResponseEntity<Object> getEmployee() {
		List<Employee> employees = employeeRepository.findAll();
		if (!employees.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(employees);
		} else {
			throw new ProcessException("response.no.employee.found");
		}
	}

	/**
	 * Get one employee details using employee id
	 *
	 * @param emp
	 * @return
	 */
	@GetMapping("/{empid}/get-employee")
	public ResponseEntity<Object> getEmployee(@PathVariable("empid") String emp,
			@RequestParam(value = "fields", required = false) List<String> fields) {
		Document projection = getProjection(fields);
		Employee employee = employeeRepository.findByEmployeeId(emp, projection);
		if (employee != null) {
			return ResponseEntity.status(HttpStatus.OK).body(employee);
		} else {
			throw new ProcessException("response.no.employee.found");
		}
	}

	@GetMapping("/{departId}/get-employee-by-department")
	public ResponseEntity<Object> getEmployeeByDeparment(@PathVariable("departId") String departId,
			@RequestParam(value = "fields", required = false) List<String> fields) {
		Document projections = getProjection(fields);
		List<Employee> employee = employeeRepository.findAllByDepartmentId(departId, projections);
		if (!employee.isEmpty()) {
			return ResponseEntity.status(HttpStatus.OK).body(employee);
		} else {
			throw new ProcessException("response.no.employee.found");
		}
	}

	/**
	 * Update employee details
	 *
	 * @param employee
	 * @param result
	 * @return
	 */
	@PostMapping("/update-employee")
	public ResponseEntity<Object> updateEmploye(@RequestBody @Valid Employee employee, BindingResult result) {
		if (result.hasErrors()) {
			ValidationErrorResponse errorResponse = new ValidationErrorResponse();
			for (ObjectError error : result.getAllErrors()) {
				if (error instanceof FieldError) {
					FieldError fieldError = (FieldError) error;
					String fieldName = fieldError.getField();
					String errorMessage = fieldError.getDefaultMessage();
					errorResponse.addError(fieldName, errorMessage);
				} else {
					errorResponse.addError(error.getDefaultMessage());
				}
			}
			return ResponseEntity.badRequest().body(errorResponse);
		}
		if (employeeRepository.findById(employee.getId()).isPresent()) {
			employeeRepository.save(employee);
			Response successResponse = new Response();
			successResponse.setResponseCode(messageSource.getMessage("response.success.code", null, null));
			successResponse.setResponseMsg(messageSource.getMessage("response.success.message", null, null));
			return ResponseEntity.status(HttpStatus.CREATED).body(successResponse);
		} else {
			throw new ProcessException("response.no.employee.found");
		}
	}

	private Document getProjection(List<String> fields) {
		Document projection = new Document();
		if (fields != null) {
			for (String field : fields) {
				projection.append(field, 1);
			}
		}
		return projection;
	}
}
