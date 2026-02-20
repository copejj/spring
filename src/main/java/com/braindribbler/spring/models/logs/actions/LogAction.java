package com.braindribbler.spring.models.logs.actions;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.braindribbler.spring.models.logs.Log;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="job_log_actions")
public class LogAction {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="job_log_action_id")
    private Long id;

    @CreationTimestamp
    @Column(name="created_date", nullable=false, updatable=false)
    private LocalDateTime createdDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="job_log_id")
    @JsonBackReference
    private Log log;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="action_id")
    private Action action;

	public Long getId() { return id; }
	public void setId(Long id) { this.id = id; }
	public LocalDateTime getCreatedDate() { return createdDate; }
	public void setCreatedDate(LocalDateTime createdDate) { this.createdDate = createdDate; }
	public Log getLog() { return log; }
	public void setLog(Log log) { this.log = log; }
	public Action getAction() { return action; }
	public void setAction(Action action) { this.action = action; }
}

