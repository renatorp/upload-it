package com.example.uploadit.entity;

import java.io.Serializable;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("Usuário")
public class User implements Serializable {
	private static final long serialVersionUID = 1709465934323243079L;

	@ApiModelProperty("Identificador do usuário")
	private Integer id;
	
	@ApiModelProperty("Nome do usuário")
	private String name;
	
	@ApiModelProperty("Senha do usuário")
	private String password;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
