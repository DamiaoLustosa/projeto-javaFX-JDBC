package gui;

import java.net.URL;
import java.util.ResourceBundle;

import gui.util.Constraints;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class DepartmentFormController implements Initializable {

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

	@FXML
	public void onBtSaveAction() {
		System.out.println("Bot�o Salvar");

	}

	@FXML
	public void onBtCancelAction() {
		System.out.println("Bot�o Salvar");

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}
	
	private void initializeNodes() {
		Constraints.setTextFieldInteger(txtId); //Comando para aceitar somente inteiros
		Constraints.setTextFieldMaxLength(txtName, 30); //M�todo para aceitar o m�ximo de 30 caracteres
	}

}
