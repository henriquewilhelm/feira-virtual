package models;

import java.io.Serializable;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
@Entity
@Table(name="bairros")
public class Bairro implements IConvertible, Serializable  {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1493845613954043924L;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	@OneToOne(cascade=CascadeType.ALL)
	private Cidade cidade;
	
	public Bairro(){
		cidade = new Cidade();
	};
	
	public Bairro(String nome, Cidade cidade) {
		super();
		this.nome = nome;
		this.cidade = cidade;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}
}
