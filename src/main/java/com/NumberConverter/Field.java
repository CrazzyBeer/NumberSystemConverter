package com.NumberConverter;

import javafx.scene.control.TextField;

public class Field {

	public TextField numberField;
	public TextField systemField;

	public TextField mainNumberField;
	public TextField mainSystemField;

	public String value;

	public Field(TextField numberField, TextField systemField, TextField mainField, TextField mainSystem) {
		this.numberField = numberField;
		this.systemField = systemField;

		this.mainNumberField = mainField;
		this.mainSystemField = mainSystem;

	}

	public Field() {
	}

	/**
	 * If everything is good with the fields, changes the numberField according
	 * to the current mainSystem and mainNumber
	 */
	public void change() {

		if (systemField.getText().length() > 0 && mainSystemField.getText().length() > 0
				&& mainNumberField.getText().length() > 0) {

			int mainSystem = Integer.parseInt(mainSystemField.getText());
			String mainNumber = mainNumberField.getText();

			int system = Integer.parseInt(systemField.getText());

			if (mainSystem >= 2 && mainSystem <= 36 && system >= 2 && system <= 36) {
				numberField.setText(Converter.fromXtoY(mainNumber, mainSystem, system));
			} else
				numberField.setText("");

		} else
			numberField.setText("");

	}

	public void clear() {
		this.getNumberField().setText("");
	}

	public String getData() {
		return "\n" + systemField.getText() + " : " + numberField.getText();
	}

	public TextField getNumberField() {
		return numberField;
	}

	public void setNumberField(TextField numberField) {
		this.numberField = numberField;
	}

	public TextField getSystemField() {
		return systemField;
	}

	public void setSystemField(TextField systemField) {
		this.systemField = systemField;
	}

	public TextField getMainNumber() {
		return mainNumberField;
	}

	public void setMainNumber(TextField mainNumber) {
		this.mainNumberField = mainNumber;
	}

	public TextField getMainSystem() {
		return mainSystemField;
	}

	public void setMainSystem(TextField mainSystem) {
		this.mainSystemField = mainSystem;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public boolean isFilled() {
		return numberField.getText().length() > 0 && systemField.getText().length() > 0;
	}

}
