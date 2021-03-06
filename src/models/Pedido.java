package models;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
    private Cidade cidade;
    private Bairro bairro;
    private Long telefone;
    
    private String obs;
	@OneToMany(cascade=CascadeType.ALL)
	@JoinTable(name = "pedido_itens",  inverseJoinColumns = { 
			@JoinColumn(name = "item_id", 
						table="itens", 
						referencedColumnName = "id",
						unique=false) } )
	private List<Item> listItens;
	private Double total = 0d;
	@Enumerated(EnumType.STRING)
	private StatusPedido status;
	
	public Pedido() {
		listItens = new ArrayList<Item>();	
		cidade = new Cidade();
		setData(Calendar.getInstance().getTime());
	};
	
	public Pedido(Date data, Usuario usuario, String obs, List<Item> listItens, Double total, StatusPedido statusPedido) {
		super();
		setUsuario(usuario);
		this.data = data;
		this.obs = obs;
		this.listItens = listItens;
		this.total = total;
		this.status = statusPedido;
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
			this.endereco = usuario.getEndereco() + " Nº "+usuario.getNumero() +". "+ usuario.getComplemento();
			this.telefone = usuario.getTelefone();
			this.cidade = usuario.getCidade();
			this.bairro = usuario.getBairro();
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
	
	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
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
	
	public List<Item> getLastItens() {
		if (listItens.size()>5){
			ArrayList<Item> itens = new ArrayList<Item>();
			Integer aux = listItens.size()-5; 
			for (int i=aux; i<listItens.size(); i++)
				itens.add(listItens.get(i));
			
			return itens;
		}
		else
			return listItens;
	}


	public Double getTotal() {
		return total;
	}
	public void setTotal(Double total) {
		this.total = total;
	}

	public StatusPedido getStatus() {
		return status;
	}

	public void setStatus(StatusPedido status) {
		this.status = status;
	}	
}
