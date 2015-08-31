package models;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="pedidos")
public class Pedido implements IConvertible, Serializable  {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5250713405988574156L;
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	
	@Temporal(TemporalType.DATE)
	private Date data;
	@OneToOne(cascade=CascadeType.ALL)
	private Usuario usuario;
	
	private String nome;
    private String endereco;
    private String cidade;
    private String bairro;
    private Long telefone;
    
    private String obs;
	@OneToMany(cascade=CascadeType.ALL, orphanRemoval=true)
	@JoinTable(name = "pedido_itens",  inverseJoinColumns = { 
			@JoinColumn(name = "item_id", 
						table="itens", 
						referencedColumnName = "id",
						unique=false) } )
	private List<Item> listItens;
	
	public Pedido() {
		listItens = new ArrayList<Item>();	
		setData(Calendar.getInstance().getTime());
	};
	
	public Pedido(Date data, Usuario usuario, String obs, List<Item> listItens) {
		super();
		setUsuario(usuario);
		this.data = data;
		this.obs = obs;
		this.listItens = listItens;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
		if (this.usuario!=null){
			this.nome = usuario.getNome();
			this.endereco = usuario.getEndereco();
			this.cidade = usuario.getCidade();
			this.bairro = usuario.getBairro();
			this.telefone = usuario.getTelefone();
		}
		
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

	public String getEndereco() {
		return this.endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	
	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public void setListItens(List<Item> listItens) {
		this.listItens = listItens;
	}

	public List<Item> getListItens() {
		return listItens;
	}
}