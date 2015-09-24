package models;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
@Entity
@Table(name="produtos")
public class Produto implements IConvertible, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5343542158290506407L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;

	@Temporal(TemporalType.DATE)
	private Date data;
	private Tipo tipo;
	private Double valor;
	private Categoria categoria;
	private String foto = "/resources/image/produtos/produto.png";
	@Enumerated(EnumType.STRING)
	private StatusProduto status;
	
	public Produto() {
		setData(Calendar.getInstance().getTime());
	};
	
	public Produto(String nome, Date data, Tipo tipo, Double valor, Categoria categoria) {
		super();
		this.nome = nome;
		this.data = data;
		this.tipo = tipo;
		this.valor = valor;
		this.categoria = categoria;
	}
	
	public Produto(Produto produto) {
		setNome(produto.getNome());
		setData(produto.getData());
		setTipo(produto.getTipo());
		setValor(produto.getValor());
		setCategoria(produto.getCategoria());
		setData(Calendar.getInstance().getTime());
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Tipo getTipo() {
		return tipo;
	}

	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
	}

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	public String getFoto() {
		return foto;
	}

	public void setFoto(String foto) {
		this.foto = foto;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public StatusProduto getStatusProduto() {
		return status;
	}

	public void setStatusProduto(StatusProduto statusProduto) {
		this.status = statusProduto;
	}
}
