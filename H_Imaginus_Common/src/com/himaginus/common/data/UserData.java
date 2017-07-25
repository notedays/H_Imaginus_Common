package com.himaginus.common.data;

import java.util.Collection;
import java.util.Set;

public class UserData implements ResponseData {

	private static final long serialVersionUID = 1L;

	// # Data for User
	private int id;
	private String nickname;
	private String birth;
	private String phoneNumber;
	
	// # Data for Player
	private String title;
	private int exp_math;
	private int exp_science;
	private int exp_language;
	private int exp_music;
	private int exp_art;
	private Set<Collection> collectionSet;
	
	private int morality;
	
}
