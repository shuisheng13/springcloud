package com.pactera.vo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class LaunWidgetTypeVo  implements Serializable {

	private static final long serialVersionUID = 7746644332391516103L;
	
	private Long id;
	private Date createDate;
	private Date updateDate;
	private String typeName;
}
