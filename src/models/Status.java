package models;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
@Entity
@Table(name="status")
public class Status implements IConvertible, Serializable  {


	/**
	 * 
	 */
	private static final long serialVersionUID = -4833618181415344459L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private Integer diaInicial;
	private Integer hrInicial;
	private Integer diaFinal;
	private Integer hrFinal;
	
	public Status(){
		
	}
	
	@Override
	public Integer getId() {
		// TODO Auto-generated method stub
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

	public Integer getDiaInicial() {
		return diaInicial;
	}

	public void setDiaInicial(Integer diaInicial) {
		this.diaInicial = diaInicial;
	}

	public Integer getHrInicial() {
		return hrInicial;
	}

	public void setHrInicial(Integer hrInicial) {
		this.hrInicial = hrInicial;
	}

	public Integer getDiaFinal() {
		return diaFinal;
	}

	public void setDiaFinal(Integer diaFinal) {
		this.diaFinal = diaFinal;
	}

	public Integer getHrFinal() {
		return hrFinal;
	}

	public void setHrFinal(Integer hrFinal) {
		this.hrFinal = hrFinal;
	}
}
