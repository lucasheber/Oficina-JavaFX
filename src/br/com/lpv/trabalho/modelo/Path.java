package br.com.lpv.trabalho.modelo;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.InputMismatchException;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;

public abstract class Path {

		
	public static final String USER_DATABASE				= "postgres";
	public static final String PASSSWORD_DATABASE			= "root";
	public static final String PATH_DATABASE				= "jdbc:postgresql://localhost/oficina";
	public static final String POSTGRES 					= "postgres";
	
	public static final String ICON 						= "/br/com/lpv/view/img/icon.png";
	public static final String LOGIN 						= "/br/com/lpv/trabalho/view/Login.fxml";
	public static final String RECEPCAO 					= "/br/com/lpv/trabalho/view/Recepcao.fxml";
	public static final String PECAS 						= "/br/com/lpv/trabalho/view/Pecas.fxml";
	public static final String CLIENTES 					= "/br/com/lpv/trabalho/view/Clientes.fxml";
	public static final String USUARIOS 					= "/br/com/lpv/trabalho/view/Usuarios.fxml";
	public static final String VEICULOS 					= "/br/com/lpv/trabalho/view/Veiculos.fxml";
	public static final String ORCAMENTOS 					= "/br/com/lpv/trabalho/view/Orcamentos.fxml";
	public static final String PAGAMENTOS 					= "/br/com/lpv/trabalho/view/Pagamento.fxml";
	public static final String CHART	 					= "/br/com/lpv/trabalho/view/Graficos.fxml";
	public static final String NE	 						= "/br/com/lpv/trabalho/view/NotaFiscal.fxml";
	
	
	/**
	 * Exibe uma alerta de sucesso!
	 * @param titulo - Titulo da barra de menu.
	 * @param cabecalho - cabeçalho da caixa
	 * @param mensagem - mensagem a ser exibida
	 */
	public static void exibirMsgSucesso(String titulo, String cabecalho, String mensagem){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(mensagem);
		
		alert.showAndWait();
	}
	
	/**
	 * Exibe uma alerta de erro!
	 * @param titulo - Titulo da barra de menu.
	 * @param cabecalho - cabeçalho da caixa
	 * @param mensagem - mensagem a ser exibida
	 */
	public static void exibirMsgErro(String titulo, String cabecalho, String mensagem){
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.setContentText(mensagem);
		
		alert.show();
	}
	
	/**
	 * Exibe uma alerta de confirmação!
	 * @param titulo - Titulo da barra de menu.
	 * @param cabecalho - cabeçalho da caixa
	 * @param mensagem - mensagem a ser exibida
	 * @return true se clicou e sim, false se cancelou ou clicou em não.
	 */
	public final static boolean exibirMsgConfirmar(String titulo, String cabecalho, String mensagem){
		Alert alert = new Alert(AlertType.CONFIRMATION, mensagem, ButtonType.YES, ButtonType.NO, ButtonType.CANCEL);
		alert.setTitle(titulo);
		alert.setHeaderText(cabecalho);
		alert.showAndWait();

		if (alert.getResult() == ButtonType.YES) {
		    return true;
		}
		
		return false;
		
	}
	
	/**
	 * Limpa os campos dos <code>TexField</code>.
	 * @param fields - Lista com os objetos
	 */
	public static void limparCampos(TextField ... fields){
		for(TextField f : fields)
			f.clear();
	}
	
	/**
	 * Fas o mascaramento para os telefones
	 * @param textField - campo a ser realizado a máscara
	 */
	public static void foneField(TextField textField) {
        maxField(textField, 14);
        textField.lengthProperty().addListener((observableValue, number, number2) -> {
            try {
                String value = textField.getText();
                value = value.replaceAll("[^0-9]", "");
                int tam = value.length();
                value = value.replaceFirst("(\\d{2})(\\d)", "($1)$2");
                value = value.replaceFirst("(\\d{4})(\\d)", "$1-$2");
                if (tam > 10) {
                    value = value.replaceAll("-", "");
                    value = value.replaceFirst("(\\d{5})(\\d)", "$1-$2");
                }
                textField.setText(value);
                positionCaret(textField);

            } catch (Exception ex) {
            }
        }
        );
    }
	
	/**
	 * Verifica se o campo corresponde só numeros automaticamente.
	 * @param textField - campo a ser verificado
	 */
	public static void numericField(final TextField textField) {
	    textField.lengthProperty().addListener(new ChangeListener<Number>() {
	        @Override
	        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
	            if (newValue.intValue() > oldValue.intValue()) {
	                char ch = textField.getText().charAt(oldValue.intValue());
	                if (!(ch >= '0' && ch <= '9')) {
	                    textField.setText(textField.getText().substring(0, textField.getText().length() - 1));
	                }
	            }
	        }
	    });
	}
	
	/**
	  * Faz o mascaramentos do cpf.
	  * @param textField - campo a ser realizado o efeito da máscara.
	  */
	 public static void cpfField(TextField textField) {

       textField.focusedProperty().addListener(new ChangeListener<Boolean>() {

           @Override
           public void changed(ObservableValue<? extends Boolean> observableValue, Boolean Boolean, Boolean fieldChange) {
               String value = textField.getText();
               if (!fieldChange) {
                   if (textField.getText().length() == 11) {
                       value = value.replaceAll("[^0-9]", "");
                       value = value.replaceFirst("([0-9]{3})([0-9]{3})([0-9]{3})([0-9]{2})$", "$1.$2.$3-$4");
                   }
               }
               textField.setText(value);
               if (textField.getText() != value) {
                   textField.setText("");
                   textField.insertText(0, value);
               }

           }
       });

       maxField(textField, 14);
	 }
	 

	 /**
		 * Verifica se é um cpf válido
		 * @param CPF - String cpf a ser validado
		 * @return <code>true</code> se for válido, <code>false</code> caso contrário.
		 */
	 public static boolean isCPF(String CPF) {
		// considera-se erro CPF's formados por uma sequencia de numeros iguais
		    if (CPF.equals("00000000000") || CPF.equals("11111111111") ||
		        CPF.equals("22222222222") || CPF.equals("33333333333") ||
		        CPF.equals("44444444444") || CPF.equals("55555555555") ||
		        CPF.equals("66666666666") || CPF.equals("77777777777") ||
		        CPF.equals("88888888888") || CPF.equals("99999999999") ||
		       (CPF.length() != 11))
		       return(false);

		    char dig10, dig11;
		    int sm, i, r, num, peso;

		// "try" - protege o codigo para eventuais erros de conversao de tipo (int)
		    try {
		// Calculo do 1o. Digito Verificador
		      sm = 0;
		      peso = 10;
		      for (i=0; i<9; i++) {              
		// converte o i-esimo caractere do CPF em um numero:
		// por exemplo, transforma o caractere '0' no inteiro 0         
		// (48 eh a posicao de '0' na tabela ASCII)         
		        num = (int)(CPF.charAt(i) - 48); 
		        sm = sm + (num * peso);
		        peso = peso - 1;
		      }

		      r = 11 - (sm % 11);
		      if ((r == 10) || (r == 11))
		         dig10 = '0';
		      else dig10 = (char)(r + 48); // converte no respectivo caractere numerico

		// Calculo do 2o. Digito Verificador
		      sm = 0;
		      peso = 11;
		      for(i=0; i<10; i++) {
		        num = (int)(CPF.charAt(i) - 48);
		        sm = sm + (num * peso);
		        peso = peso - 1;
		      }

		      r = 11 - (sm % 11);
		      if ((r == 10) || (r == 11))
		         dig11 = '0';
		      else dig11 = (char)(r + 48);

		// Verifica se os digitos calculados conferem com os digitos informados.
		      if ((dig10 == CPF.charAt(9)) && (dig11 == CPF.charAt(10)))
		         return(true);
		      else return(false);
		    } catch (InputMismatchException erro) {
		        return(false);
		    }
	}
		 
	
	 public static void maxField(final TextField textField, final Integer length) {
	    textField.textProperty().addListener(new ChangeListener<String>() {
	        @Override
	        public void changed(ObservableValue<? extends String> observableValue, String oldValue, String newValue) {
	            if (newValue.length() > length)
	                textField.setText(oldValue);
	        }
	    });
	}// maxField
	 
	 
	 /**
		 * Transforma uma data para o formato dd/MM/yyyy
		 * @param data - LocalDate - data a ser transformada
		 * @return retorna a data no formato dd/MM/yyyy
		 */
		public static String Dataformatada(LocalDate data){
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			return sdf.format(Date.valueOf(data).getTime());
		}
	 private static void positionCaret(TextField textField) {
        Platform.runLater(() -> {
            if (textField.getText().length() != 0) {
                textField.positionCaret(textField.getText().length());
            }
        }
        );
	 }
	 
	 public static void placa(final TextField textField) {
	    	textField.textProperty().addListener(new ChangeListener<String>() {
	    		@Override
	    		public void changed(ObservableValue<? extends String> ov, String antigo, String novo) {
	    			if (!novo.isEmpty()) {
	    				if (!letrasDaPlacaValida(novo, antigo, textField) || !numerosDaPlacaValida(novo, antigo, textField)) {
	    					desfazAlteracao(antigo, textField);
	    				}
	    			}
	    		}
	    	});

	    }

	    private static boolean letrasDaPlacaValida(String novo, String antigo, TextField textField) {
	    	for (int i = 0; i < novo.length() && i < 3; i++) {
	    		try {
	    			Integer.parseInt(novo.substring(i, i + 1));
	    			return false;
	    		} catch (NumberFormatException e) {
	    		}
	    	}
	    	return true;
	    }

	    private static boolean numerosDaPlacaValida(String novo, String antigo, TextField textField) {
	    	if (novo.length() >= 4 && novo.length() <= 7) {
	    		try {
	    			Integer.parseInt(novo.substring(3));
	    			return true;
	    		} catch (NumberFormatException e) {
	    			return false;
	    		}
	    	} else if (novo.length() < 4) {
	    		return true;
	    	}
	    	return false;
	    }

	    private static void desfazAlteracao(String antigo, TextField textField) {
	    	if (antigo != null && !antigo.isEmpty()) {
	    		textField.setText(antigo);
	    	} else {
	    		textField.clear();
	    	}
	    }
	 
}
