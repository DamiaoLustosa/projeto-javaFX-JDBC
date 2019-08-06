package gui;

import java.net.URL;
import java.util.ResourceBundle;

import db.DbException;
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
import model.services.DepartmentService;

public class DepartmentFormController implements Initializable {

	private Department entity;
	private DepartmentService service;

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

	@FXML
	public void onBtSaveAction(ActionEvent event) {
		if(entity == null) {
			throw new IllegalStateException("Entity está vazio!");
		}else (service == null){
			throw new IllegalStateException("service está vazio!");
			
		}
		
		try {
			entity = getFormData();
			service.saveOrUpdate(entity);
			Utils.currentStage(event).close();
		}
		catch (DbException e) {
			Alerts.showAlert("Error ao salvar objeto", null, e.getMessage(), AlertType.ERROR);
		}
		

	}

	private Department getFormData() {

		Department obj = new Department();
		obj.setId(Utils.tryParseToInt(txtId.getText())); // Convertendo para inteiro por meio do metodo da classe utils
		obj.setNome(txtName.getText());

		return obj;
	}

	@FXML
	public void onBtCancelAction(ActionEvent event) {
		Utils.currentStage(event).close();	//Fecha a janela como chamada do evento

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

}
