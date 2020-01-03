package application;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;

import java.io.IOException;

import br.com.casadocodigo.livraria.produtos.Produto;
import dao.ProdutoDAO;
import io.Exportador;

@SuppressWarnings({"unchecked", "rawtypes"})
public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		final ObservableList<Produto> produtos = new ProdutoDAO().lista();
		TableView<Produto> tableView = new TableView<>(produtos);
		VBox vbox = new VBox(tableView);
		TableColumn nomeColumn = new TableColumn("Nome");
		TableColumn descColumn = new TableColumn("Descrição");
		TableColumn valorColumn = new TableColumn("Valor");
		TableColumn isbnColumn = new TableColumn("ISBN");
		Button button = new Button("Exportar CSV");
		Group group = new Group();
		Scene scene = new Scene(group, 690, 510);
		Label label = new Label("Listagem de Livros");
		
		button.setLayoutX(575);
		button.setLayoutY(25);
		nomeColumn.setMinWidth(180);
		descColumn.setMinWidth(230);
		valorColumn.setMinWidth(60);
		isbnColumn.setMinWidth(180);
		nomeColumn.setCellValueFactory(new PropertyValueFactory("nome"));
		descColumn.setCellValueFactory(new PropertyValueFactory("descricao"));
		valorColumn.setCellValueFactory(new PropertyValueFactory("valor"));
		isbnColumn.setCellValueFactory(new PropertyValueFactory("isbn"));
		label.setFont(Font.font("Lucida Grande", FontPosture.REGULAR, 30));
		label.setPadding(new Insets(20, 0, 10, 10));
		vbox.setPadding(new Insets(70, 0, 0, 10));
		
		button.setOnAction(event -> {
			new Thread(() -> {
				dormirPorVinteSegundos();
				exportarEmCSV(produtos);
			}).start();
		});
		
		tableView.getColumns().addAll(nomeColumn, descColumn, valorColumn, isbnColumn);
		group.getChildren().addAll(label, vbox, button);
		
		primaryStage.setScene(scene);
		primaryStage.setTitle("Sistema da livraria com JavaFX");	
		primaryStage.show();
	}
	
	private void exportarEmCSV(ObservableList<Produto> produtos) {
		try {
			new Exportador().paraCSV(produtos);
		}catch(IOException e) {
			System.out.println("Erro ao exportar: " + e);
		}
	}
	
	private void dormirPorVinteSegundos() {
		try {
			Thread.sleep(20000);
		}catch(InterruptedException e) {
			System.out.println("Ops, ocorreu um erro: " + e);
		}
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
