package com.NumberConverter;

import java.util.Vector;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class MainController {
	public Button add;
	public VBox mainBox;

	public TextField mainSystem;
	public TextField mainNumber;

	public Vector<Field> fields;

	public int error;

	public void initialize() {
		fields = new Vector<Field>();
		mainSystem.setText("10");
		mainNumber.setText("0");
		error = 0;

		mainSystem.textProperty().addListener(new ChangeListener<String>() {
			/*
			 * If the value is a number, its length is greater than 0
			 * and it is contained in [2, 36]
			 */
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*") && newValue.length() > 0) {
					int val = Integer.parseInt(newValue);
					if (val >= 2 && val <= 36) {
						removeError(mainSystem);
						if (fitsSystem(mainNumber.getText()))
							removeError(mainNumber);
						else
							addError(mainNumber);

						changeAll();
					} else {
						addError(mainSystem);

						changeAll();
					}

				} else {
					addError(mainSystem);

					changeAll();
				}
			}
		});

		mainNumber.textProperty().addListener(new ChangeListener<String>() {
			/* 
			 * If the value is a number, its length is greater than 0
			 */
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.length() > 0 && fitsSystem(newValue)) {
					removeError(mainNumber);
					changeAll();
				} else {
					addError(mainNumber);
					changeAll();
				}

			}
		});
	}

	/**
	 * Checks if the new String in the mainNumber has it's elements
	 * in the bounds of the given system. They should be [0, system)
	 * @param newValue
	 * @return true if fits, false if doesn't fit
	 */
	public boolean fitsSystem(String newValue) {

		int system = Integer.parseInt(mainSystem.getText());
		String num = newValue;

		for (int i = 0; i < num.length(); i++) {
			char c = num.charAt(i);
			int val = Converter.getOrd(c);
			if (val >= system)
				return false;
		}
		return true;
	}

	/**
	 * Adds the error class to the field containing an error
	 * Checks if it already contains such a class
	 * @param field
	 */
	public void addError(TextField field) {
		if (!field.getStyleClass().contains("error")) {
			field.getStyleClass().add("error");
		}
		error = 1;
	}
	
	/**
	 * Removes the error class to the field no more containing an error
	 * Checks if it already contains such a class
	 * @param field
	 */
	public void removeError(TextField field) {
		if (field.getStyleClass().contains("error")) {
			field.getStyleClass().remove(field.getStyleClass().size() - 1);
		}
		error = 0;
	}

	/**
	 * Propagates the change to all the fields
	 * If the is an error then it clears them
	 * 
	 */
	public void changeAll() {
		if (error == 1) {
			for (int i = 0; i < fields.size(); i++) {
				fields.elementAt(i).clear();
			}
			return;
		}

		for (int i = 0; i < fields.size(); i++) {
			fields.elementAt(i).change();
		}
	}

	/**
	 * Adds a new field
	 */
	public void addPressed() {
		if (error == 1)
			return;

		final Field newField = new Field();

		HBox newBox = new HBox();
		newBox.setSpacing(10);
		newBox.getStyleClass().add("newField");

		TextField system = new TextField();
		system.textProperty().addListener(new ChangeListener<String>() {
			/* If it's a number and its length is greater than 0 
			 * then change it according to the mainSystem and mainNumber
			 * */
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (newValue.matches("\\d*")) {
					if (newValue.length() > 0) {
						if (error == 0)
							newField.change();
					}
				} else {
					system.setText(oldValue);
				}
			}
		});
		system.setPromptText("[2, 36]");
		system.setTooltip(new Tooltip("Enter the needed system. "));
		system.setPrefWidth(50);
		system.setMinWidth(50);
		system.setAlignment(Pos.CENTER);

		TextField resultNumber = new TextField();
		resultNumber.setEditable(false);
		resultNumber.setTooltip(new Tooltip("Output for the number. Can't be changed. "));
		resultNumber.prefWidthProperty().bind(mainBox.widthProperty().subtract(20));
		resultNumber.getStyleClass().add("resultNumber");

		Button delete = new Button("Delete");
		delete.setMinWidth(60);
		delete.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				mainBox.getChildren().remove(newBox);
				for (int i = 0; i < fields.size(); i++) {
					if (fields.elementAt(i).equals(newField))
						fields.remove(i);
				}
			}

		});

		newBox.getChildren().addAll(system, resultNumber, delete);
		newBox.setPrefHeight(-1);

		mainBox.getChildren().add(newBox);

		/* Adds the references to all the needed textFields to the object */
		newField.setNumberField(resultNumber);
		newField.setSystemField(system);
		newField.setMainSystem(mainSystem);
		newField.setMainNumber(mainNumber);
		newField.change();

		fields.add(newField);
	}
}
