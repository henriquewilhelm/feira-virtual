package models;

import java.io.Serializable;
 

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Enumerated;

@Entity
@Table(name = "usuarios")
public class Usuario implements IConvertible, Serializable {
    
    /**
	 * 
	 */
	private static final long serialVersionUID = -6601780594381439370L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(unique=true)
    private String email;
    private String password;
    private String nome;
    private String endereco;
    private String cidade;
    private String bairro;
    private Long telefone;
    @Enumerated(EnumType.STRING)
    private UserTipo tipo;
    
    public Usuario(){
    	tipo = UserTipo.USER;
    }
    
    public Usuario(String email, String password, String nome, String endereco,
			String cidade, String bairro, Long telefone, UserTipo tipo) {
		super();
		this.email = email;
		this.password = password;
		this.nome = nome;
		this.endereco = endereco;
		this.cidade = cidade;
		this.bairro = bairro;
		this.telefone = telefone;
		this.tipo = tipo;
	}

	// get and set 
    public boolean isAdmin() {
        return UserTipo.ADMIN.equals(tipo);
    }
 
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Long getTelefone() {
		return telefone;
	}

	public void setTelefone(Long telefone) {
		this.telefone = telefone;
	}

	public UserTipo getTipo() {
		return tipo;
	}

	public void setTipo(UserTipo tipo) {
		this.tipo = tipo;
	}
	
	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public boolean isUser() {
        return UserTipo.USER.equals(tipo);
    }
}
