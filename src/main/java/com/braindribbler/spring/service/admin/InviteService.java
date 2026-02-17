package com.braindribbler.spring.service.admin;

import java.util.List;

import com.braindribbler.spring.models.admin.Invite;

public interface InviteService {
	List<Invite> getAllInvites();
	Invite saveInvite(Invite invite);
	Invite getValidInvite(String key);
	void completeInvite(Invite invite, Long newUserId);
}
