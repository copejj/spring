package com.braindribbler.spring.service.admin.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.braindribbler.spring.models.admin.Invite;
import com.braindribbler.spring.repositories.admin.InviteRepository;
import com.braindribbler.spring.service.admin.InviteService;

@Service
public class InviteServiceImpl implements InviteService
{
	private final InviteRepository inviteRepository;

	public InviteServiceImpl(InviteRepository inviteRepository) {
		this.inviteRepository = inviteRepository;
	}
	
	@Override
	public List<Invite> getAllInvites() {
		return inviteRepository.findAll();
	}

	@Override
	public Invite saveInvite(Invite invite) {
        if (invite == null) {
            throw new IllegalArgumentException("Invite cannot be null");
        }
		return inviteRepository.save(invite);
	}

	@Override
	public Invite getValidInvite(String key) {
		Invite invite = inviteRepository.findByKey(key);
        // Only return if it exists AND hasn't been used (userId is null)
		if (invite == null || invite.getUserId() != null) {
			return null;
		}
		return invite;
	}

	@Override
	public void completeInvite(Invite invite, Long newUserId) {
        invite.setUserId(newUserId);
        inviteRepository.save(invite);
    }
}