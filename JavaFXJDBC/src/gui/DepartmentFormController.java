package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;

import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Department;
import model.exception.ValidationException;
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private Department entity;
	private DepartmentService service;

	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private Label labelerrorName;

	@FXML
	private Button btSave;

	@FXML
	private Button btCancel;

	public void setDepartement(Department entity) {
		this.entity = entity;

	}

	public void setDepartmentService(DepartmentService service) {
		this.service = service;

	}

	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);

	}

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entity está vazio!");
		}
		else(service == null){	
			throw new IllegalStateException("service está vazio!");
		}
		
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			notifyDataChangeListeners();
			Utils.currentStage(event).close(); //Fecha a janela como chamada do evento
		}
		catch (ValidationException e) {
			setErrorMessages(e.getMessage());
		}
		catch (DbException e) {
			Alerts.showAlert("Error ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
		

	}

	private void notifyDataChangeListeners() {
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();

		}

	}

	private Department getFormData() {

		Department obj = new Department();

		ValidationException exception = new ValidationException("Validation Error!");

		obj.setId(Utils.tryParseToInt(txtId.getText())); // Convertendo para inteiro por meio do metodo da classe utils

		if (txtName.getText() == null || txtName.getText().trim().equals("")) {
			exception.addError("Nome", "O Campo não pode ser vazio");
		}
		obj.setNome(txtName.getText());

		if (exception.getErrors().size() > 0) { // Verifica se a quantidade de erros é maior que zero
			throw exception;
		}

		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close(); // Fecha a janela como chamada do evento

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId); // Comando para aceitar somente inteiros
		Constraints.setTextFieldMaxLength(txtName, 30); // Método para aceitar o máximo de 30 caracteres
	}

	public void updateFormData() {
		if (entity == null) {
			throw new IllegalStateException("entity está nulo!");
		}
		txtId.setText(String.valueOf(entity.getId())); // Convertendo para String
		txtName.setText(String.valueOf(entity.getNome()));
	}

	private void setErrorMessages(Map<String, String> errors) {
		Set<String> fields = errors.keySet();

		if (fields.contains("Name")) {
			labelerrorName.setText(errors.get("name"));

		}

	}

}
