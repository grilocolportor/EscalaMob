package org.avs.interfaces;
import org.avs.types.FriendInfo;
import org.avs.types.MessageInfo;


public interface IUpdateData {
	public void updateData(MessageInfo[] messages, FriendInfo[] friends, FriendInfo[] unApprovedFriends, String userKey);

}
