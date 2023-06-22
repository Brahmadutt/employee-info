package com.wow.employee.info.models;

import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

public class ValidationErrorResponse {

	private List<ValidationError> validationErrors;

	public ValidationErrorResponse() {
		this.validationErrors = new ArrayList<>();
	}

	public List<ValidationError> getErrors() {
		return validationErrors;
	}

	public void addError(String field, String message) {
		ValidationError validationError = new ValidationError(field, message);
		validationErrors.add(validationError);
	}

	public void addError(String message) {
		ValidationError validationError = new ValidationError(null, message);
		validationErrors.add(validationError);
	}

	@Getter
	@Setter
	private static class ValidationError {
		private String field;
		private String message;

		public ValidationError(String field, String message) {
			this.field = field;
			this.message = message;
		}

	}
}
