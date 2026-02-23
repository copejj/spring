package com.braindribbler.spring.models.logs.status;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="statuses")
public class Status {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="status_id")
	private Long statusId;

	private String status;
	
	private Integer order;

	public Long getStatusId() { return statusId; }
	public void setStatusId(Long statusId) { this.statusId = statusId; }
	public String getStatus() { return status; }
	public void setStatus(String status) { this.status = status; }
	public Integer getOrder() { return order; }
	public void setOrder(Integer order) { this.order = order; }
}
