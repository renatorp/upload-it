package com.example.uploadit.vo;

import java.io.Serializable;

import org.springframework.web.multipart.MultipartFile;

public class FileRequestBody implements Serializable {

	private static final long serialVersionUID = 1L;

	private MultipartFile file;
	private String dzuuid;
	private Integer dzchunkindex;
	private Long dztotalfilesize;
	private Long dzchunksize;
	private Integer dztotalchunkcount;
	private Integer dzchunkbyteoffset;

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

	public Integer getDzchunkbyteoffset() {
		return dzchunkbyteoffset;
	}

	public void setDzchunkbyteoffset(Integer dzchunkbyteoffset) {
		this.dzchunkbyteoffset = dzchunkbyteoffset;
	}

}
