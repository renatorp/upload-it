package com.example.uploadit.vo;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.ApiModelProperty;

public class FileRequestBody implements Serializable {

	private static final long serialVersionUID = 7604744323529383474L;

	@ApiModelProperty(hidden = true)
	private MultipartFile file;

	@ApiModelProperty("Indentificado do arquivo.")
	private String dzuuid;

	@ApiModelProperty("Se o arquivo estiver divido em blocos, indica qual índice esta requisição representa.")
	private Integer dzchunkindex;

	@ApiModelProperty("Tamanho total do arquivo em bytes.")
	private Long dztotalfilesize;

	@ApiModelProperty("Tamanho total do bloco em bytes.")
	private Long dzchunksize;

	@ApiModelProperty("Se o arquivo estiver divido em blocos, indica a quantidade total de blocos.")
	private Integer dztotalchunkcount;

	public String getDzuuid() {
		return dzuuid;
	}

	public void setDzuuid(String dzuuid) {
		this.dzuuid = dzuuid;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}

	public Integer getDzchunkindex() {
		return dzchunkindex;
	}

	public void setDzchunkindex(Integer dzchunkindex) {
		this.dzchunkindex = dzchunkindex;
	}

	public Long getDztotalfilesize() {
		return dztotalfilesize;
	}

	public void setDztotalfilesize(Long dztotalfilesize) {
		this.dztotalfilesize = dztotalfilesize;
	}

	public Long getDzchunksize() {
		return dzchunksize;
	}

	public void setDzchunksize(Long dzchunksize) {
		this.dzchunksize = dzchunksize;
	}

	public Integer getDztotalchunkcount() {
		return dztotalchunkcount;
	}

	public void setDztotalchunkcount(Integer dztotalchunkcount) {
		this.dztotalchunkcount = dztotalchunkcount;
	}
}
