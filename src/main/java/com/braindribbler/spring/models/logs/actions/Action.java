package com.braindribbler.spring.models.logs.actions;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="actions")
public class Action {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long actionId;

    private String name;
    
    private Integer sequence;

	public Long getActionId() { return actionId; }
	public void setActionId(Long actionId) { this.actionId = actionId; }
	public String getName() { return name; }
	public void setName(String name) { this.name = name; }
	public Integer getSequence() { return sequence; }
	public void setSequence(Integer sequence) { this.sequence = sequence; }
}
